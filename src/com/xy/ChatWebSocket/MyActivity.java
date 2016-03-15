package com.xy.ChatWebSocket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.xy.ChatWebSocket.model.LoginOutModel;
import com.xy.ChatWebSocket.model.SayModel;
import com.xy.ChatWebSocket.socket.WebSocketClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class MyActivity extends Activity {
    private String TAG = "xiaoyu";
    private Gson gson = new Gson();
    private WebSocketClient client;
    private String pongString = "{\"type\":\"pong\"}";
    private String loginString = "{\"type\":\"login\",\"client_name\":\"AndroidXY\",\"room_id\":\"1\"}";

    private String urlString = "ws://182.92.218.87:7272";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        doConnect();
    }

    private void doConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<BasicNameValuePair> extraHeaders = Arrays.asList(new BasicNameValuePair("Cookie", "session=xiaoyu"));

                client = new WebSocketClient(URI.create(urlString), new WebSocketClient.Listener() {
                    @Override
                    public void onConnect() {
                        Log.d(TAG, "Connected!");
                        client.send(loginString);
                    }

                    @Override
                    public void onMessage(String message) {
                        String type = "";
                        try {
                            JSONObject result = new JSONObject(message);
                            type = result.getString("type");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (type.equals("ping")) {
                            client.send(pongString);
                            Log.d(TAG,"ping");
                        } else if (type.equals("say")) {
                            SayModel sayModel = gson.fromJson(message, SayModel.class);
                            Log.d(TAG, sayModel.from_client_name + ":" + sayModel.content + " time:" + sayModel.time);
                        } else if (type.equals("login")) {
                            LoginOutModel loginOutModel = gson.fromJson(message, LoginOutModel.class);
                            Log.d(TAG, loginOutModel.client_name + "login time:" + loginOutModel.time);
                        } else if (type.equals("logout")) {

                        }
                    }

                    @Override
                    public void onMessage(byte[] data) {
                    }

                    @Override
                    public void onDisconnect(int code, String reason) {
                        Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
                    }

                    @Override
                    public void onError(Exception error) {
                    }
                }, extraHeaders);

                client.connect();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        client.disconnect();
        super.onDestroy();
    }
}
