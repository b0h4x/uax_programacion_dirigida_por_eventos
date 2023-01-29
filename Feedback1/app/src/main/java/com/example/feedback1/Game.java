package com.example.feedback1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
    ImageView mFlagImage;
    TextView mScore;
    ProgressBar progressBar;
    int score = 0;
    int progressBarCount = 60;
    JSONArray countries;
    JSONObject country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        progressBar = findViewById(R.id.progressBar2);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progressBarCount == 0) finishGame();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                      progressBarCount--;
                      progressBar.setProgress(progressBarCount);
                   }
               });
            }
        }, 0,1000);
        mFlagImage = findViewById(R.id.flagImage);
        mScore = findViewById(R.id.score);
        mScore.setText(R.string.score_text);
        mScore.append(" ");
        mScore.append(String.valueOf(score));

        // Retrieve the API
        try {
            fetchApi();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            startGame();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startGame() throws JSONException {
            country = nextCountry();
            List<Integer> myList =
                new ArrayList<>(Arrays.asList(R.id.button_1, R.id.button_2, R.id.button_3));
            Collections.shuffle(myList);

            // The correct
            Button button = (Button)findViewById(myList.remove(0));
            button.setText(country.getJSONObject("name").get("common").toString());
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    try {
                        score++;
                        mScore.setText(R.string.score_text);
                        mScore.append(" ");
                        mScore.append(String.valueOf(score));
                        startGame();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            for (int i=0; i<2; i++) {
                // The rest
                button = (Button) findViewById(myList.remove(0));
                button.setText(countries.getJSONObject(
                        new Random().nextInt(countries.length() + 1)
                ).getJSONObject("name").get("common").toString());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            score--;
                            mScore.setText(R.string.score_text);
                            mScore.append(" ");
                            mScore.append(String.valueOf(score));
                            startGame();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
    }

    JSONObject nextCountry() throws JSONException {
        // select random country
        Random rand = new Random();
        int randNum = rand.nextInt(countries.length()+1);
        country = countries.getJSONObject(randNum);
        // remove country so doesn't appear again
        countries.remove(randNum);
        setFlag(country);
        return country;
    }

    private void fetchApi() throws InterruptedException {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Make request
                            URL url = new URL("https://restcountries.com/v3.1/all");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.37");
                            con.setRequestMethod("GET");
                            // Read the response

                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())) ;
                            String inputLine;
                            StringBuilder content = new StringBuilder();
                            while ((inputLine = in.readLine()) != null) {
                                content.append(inputLine);
                            }
                            in.close();
                            // parse JSON

                            countries = new JSONArray(content.toString());
                        } catch (Exception e) {
                            Log.d("FailAPI", "Failed fetching the API" + e.toString());
                            finish();
                        }
                    }
                }
        );
        thread.start();
        thread.join();
   }

    private void finishGame() {
        // Add logic with the DAO, to store results TODO
        finish();
        Intent intent = new Intent(Game.this, GameResults.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    private void setFlag (JSONObject country) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String flagUrl = (String) country.getJSONObject("flags").get("png");
                    URL url = new  URL(flagUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    final Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFlagImage.setImageBitmap(myBitmap);
                        }
                    });
                } catch (Exception e) {
                    Log.d("failImageFlag", e.toString());
                }
            }
        }).start();
    }
}