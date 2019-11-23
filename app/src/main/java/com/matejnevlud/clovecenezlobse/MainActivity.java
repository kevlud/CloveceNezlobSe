package com.matejnevlud.clovecenezlobse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

    MediaPlayer player;
    ProgressDialog progressBar;
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


        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Please wait");
        progressBar.setMessage("Waiting for other players...");
        progressBar.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progressBar.show();

    }

    public void onRestartButtonClicked(View view) {
        this.mSocket.emit("initialize game");
        boardView.restartBoard();
    }


    private void onMyTurn() {
        myNameTextView.setText("Your turn, " + playerName);
        isMyTurn = true;
        canTossDice = true;
        diceNumbers = new ArrayList<>();
        boardView.startDrawingAnimatedDice();
        myNameTextView.setText("You can throw the dice.");
    }

    private void onOtherMoved(String jsonResponse) {
        myNameTextView.setText("Other player moved.");
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
        Log.d("GAME", "Did throw number " + lastDiceNumber);
        myNameTextView.setText("You threw " + lastDiceNumber);

        canTossDice = false;
        boardView.stopDrawingAnimatedDice(lastDiceNumber);

        if (boardView.isMyPieceOutOfHolder()) {
            this.canMoveFigure = true;
            boardView.canMoveFigure();

            // If I have figures on board and i threw number 6
        } else if (!boardView.isMyPieceOutOfHolder() && lastDiceNumber == 6) {
            this.canMoveFigure = true;
            boardView.canMoveFigure();

            // If all my figures are in holders and I didnt throw dice 3 times yet
        } else if (!boardView.isMyPieceOutOfHolder() && lastDiceNumber != 6 && diceNumbers.size() < 3) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    canTossDice = true;
                    boardView.startDrawingAnimatedDice();
                    myNameTextView.setText("You can throw the dice.");
                }
            }, 2000);
        } else {
            emitMyTurnEnded();
        }
    }


    // BOARD VIEW LISTENER
    @Override
    public void onBoardFigureMoved() {
        this.emitMyFigureMoved();
    }

    @Override
    public void onDiceClicked() {
        if (!canTossDice) return;
        this.generateRandomDiceNumber();
    }

    private void emitMyFigureMoved() {
        Log.d("SOCKET EMIT", "FIGURE MOVED");
        mSocket.emit("figure moved", boardView.exportToJSONMovedFigures());


        if (lastDiceNumber == 6) {
            canTossDice = true;
            boardView.startDrawingAnimatedDice();
            myNameTextView.setText("You can throw the dice.");
        } else {
            this.emitMyTurnEnded();
        }
    }

    private void emitMyTurnEnded() {
        myNameTextView.setText("Your move ended.");
        Log.d("SOCKET EMIT", "MOVE ENDED");
        mSocket.emit("move ended");
        isMyTurn = false;
    }


    // SOCKET IO LISTENER
    private Emitter.Listener onStartGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //JSONObject data = (JSONObject) args[0];

            final String myColor = (String) args[0];

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) findViewById(R.id.myColor)).setText(myColor);
                }
            });


            Log.d("SOCKET START GAME", myColor);
            boardView.setMyPlayingColor(myColor);
            boardView.restartBoard();

            progressBar.dismiss();
        }
    };

    private Emitter.Listener onMyName = new Emitter.Listener() {
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    playerName = (String) args[0];
                    myNameTextView.setText("Starting new game");

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
                    onOtherMoved((String) args[0]);
                }
            });
        }
    };


}
