package com.matejnevlud.clovecenezlobse;

import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Game {

    BP myFigures;
    String playerName;

    private Socket mSocket;

    {
        try {
            this.mSocket = IO.socket("https://clovece-nezlob-se-server.herokuapp.com");
        } catch (URISyntaxException e) {
        }
    }

    public Game() {


    }

    private Emitter.Listener onStartGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //JSONObject data = (JSONObject) args[0];

            Log.d("SOCKET START GAME", (String) args[0]);
        }
    };

    private Emitter.Listener onMyName = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //JSONObject data = (JSONObject) args[0];

            Log.d("SOCKET MY NAME", (String) args[0]);

        }
    };

}
