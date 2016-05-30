/*
*   Example of an Okhttp Get Request and Post request using it's built in async callback
*   Dean Brown 30/3/2016
 */
package com.grapevine.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkActivity extends AppCompatActivity {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    Button getButton = (Button) findViewById(R.id.button1);
    Button postButton = (Button) findViewById(R.id.button2);
    EditText textBox = (EditText) findViewById(R.id.textBox);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok);

        /*
        *   Sets a click listener on the get button
        */
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://raw.github.com/square/okhttp/master/README.md";
                try {
                    doGetRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        /*
        *   Sets a click listener on the post button
        */
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO -> set up a php server on eu5.org just to test if we can create successful post requests
                String url = "http://grapevinetest.eu5.org/messages.php";
                String message = textBox.getText().toString();
                try {
                    doPostRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    *   Returns the text boxes content
     */
    String getTextBoxContent() {
        String textBoxContent = textBox.getText().toString();
        return textBoxContent;
    }

    /*
    *   Performs Get Request
     */
    void doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Error!
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = res = response.body().string();
                handleGetResponse(res);
            }
        });
    }

    /*
    *   Handles the Get Response
     */
    void handleGetResponse(String response) {
        Log.i("OkHttpGet", response);
    }

    /*
    *   Creates a formBody and builds a request
    *   Performs Post Request
     */
    void doPostRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String text = getTextBoxContent();

        RequestBody formBody = new FormBody.Builder()
                .add("message", text)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                handlePostResponse(res);
            }
        });
    }

    /*
    *   Handles the Post Response
     */
    void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }

}