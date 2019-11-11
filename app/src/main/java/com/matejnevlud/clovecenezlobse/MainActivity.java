package com.matejnevlud.clovecenezlobse;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.squareup.seismic.ShakeDetector;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener, OnBoardChangedListener {

    BoardView boardView;

    TextView myNameTextView;


    BP myFigures;
    String playerName;

    private Socket mSocket;

    private boolean isMyTurn = false;
    private boolean canMoveFigure = false;
    private boolean didMoveFigure = false;

    private boolean canTossDice = false;
    private int lastDiceNumber;
    ArrayList<Integer> diceNumbers = new ArrayList<>();

    {
        try {
            this.mSocket = IO.socket("https://clovece-nezlob-se-server.herokuapp.com");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
        sd.start(sensorManager);

        this.boardView = findViewById(R.id.canvasView);
        boardView.addListenerForBoardEvents(this);
        this.myNameTextView = findViewById(R.id.myName);

        this.mSocket.on("start game", onStartGame);
        this.mSocket.on("your name", onMyName);
        this.mSocket.on("your move", onMyTurn);
        this.mSocket.on("other moved", onOtherMoved);
        this.mSocket.connect();

    }

    private void onMyTurn() {
        myNameTextView.setText("Your turn, " + playerName);
        isMyTurn = true;
        canTossDice = true;
        boardView.startDrawingAnimatedDice();


        ///// SMAZAT V OSTRE
        this.generateRandomDiceNumber();
    }

    private void OnOtherMoved(String jsonResponse) {
        boardView.parseJSONFromServer(jsonResponse);
    }

    // STOPPED TOSSING DICE
    @Override
    public void hearShake() {
        if (!canTossDice) return;
        this.generateRandomDiceNumber();
    }

    public void generateRandomDiceNumber() {
        Random rn = new Random();
        lastDiceNumber = rn.nextInt(6) + 1;
        diceNumbers.add(lastDiceNumber);
        Log.d("GAME", "Did toss number " + lastDiceNumber);
        canTossDice = false;
        boardView.stopDrawingAnimatedDice(lastDiceNumber);

        if (boardView.isMyPieceOutOfHolder(boardView.myColoredFigure)) {
            this.canMoveFigure = true;
            boardView.canMoveFigure();
        } else if (!boardView.isMyPieceOutOfHolder(boardView.myColoredFigure) && lastDiceNumber == 6) {
            this.canMoveFigure = true;
            boardView.canMoveFigure();

        } else if (diceNumbers.size() >= 3) {
            this.emitMyTurnEnded();
            this.diceNumbers = new ArrayList<>();
        } else {
            canTossDice = true;
            boardView.startDrawingAnimatedDice();

            ///// SMAZAT V OSTRE
            this.generateRandomDiceNumber();
        }


    }

    @Override
    public void onBoardFigureMoved() {
        this.emitMyFigureMoved();
    }


    private void emitMyFigureMoved() {
        Log.d("SOCKET EMIT", "FIGURE MOVED");
        mSocket.emit("figure moved", boardView.exportToJSONMovedFigures());
        this.emitMyTurnEnded();
    }

    private void emitMyTurnEnded() {
        Log.d("SOCKET EMIT", "MOVE ENDED");
        mSocket.emit("move ended");

    }


    private Emitter.Listener onStartGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //JSONObject data = (JSONObject) args[0];

            Log.d("SOCKET START GAME", (String) args[0]);
            boardView.setMyPlayingColor((String) args[0]);
        }
    };

    private Emitter.Listener onMyName = new Emitter.Listener() {
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    playerName = (String) args[0];
                    myNameTextView.setText(playerName);

                    Log.d("SOCKET MY NAME", (String) args[0]);
                }
            });
        }
    };

    private Emitter.Listener onMyTurn = new Emitter.Listener() {
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    Log.d("SOCKET MY TURN", "MY TURN");
                    onMyTurn();
                }
            });
        }
    };

    private Emitter.Listener onOtherMoved = new Emitter.Listener() {
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("SOCKET", "OTHER MOVED");
                    //onOtherMoved();
                }
            });
        }
    };


}
