package com.example.dima.battlesheeps.UI.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.DBHelper;
import com.example.dima.battlesheeps.BL.Difficulty;
import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;


public class GameOverActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener {

    double longitude = 0;
    double latitude = 0;

    GoogleApiClient mGoogleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        View v;
        String whoWon = getIntent().getExtras().getString(Constants.WINNER_KEY);
        final int difficulty = getIntent().getExtras().getInt("difficulty");
        if (whoWon.equals("Player")) {


            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }

            mGoogleApiClient.connect();


            final Game mGame = (Game) getIntent().getExtras().getParcelable("game");
            int score = getIntent().getExtras().getInt("score");

            mGame.mPlayer.setScore(score);
            mGame.mDifficulty = new Difficulty(difficulty);
            DBHelper dbhelper = new DBHelper(this);
            mGame.dbhelper = dbhelper;
            v = findViewById(R.id.winnerPlayer);
            TextView scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setText("Score: " + score);
            if (mGame.isHighScore()) {
                final String[] name = new String[1];
                LayoutInflater li = LayoutInflater.from(GameOverActivity.this);
                View promptsView = li.inflate(R.layout.set_name_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameOverActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        name[0] = userInput.getText().toString();
                                        mGame.mPlayer.setName(name[0]);
                                        mGame.mPlayer.setLongitude(longitude);
                                        mGame.mPlayer.setLatitude(latitude);
                                        mGame.addPlayer();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        } else {
            v = findViewById(R.id.winnerRival);
        }
        v.setVisibility(View.VISIBLE);

        Button backToMenu = (Button) findViewById(R.id.backToMenu);
        Button highScores = (Button) findViewById(R.id.goToHishScoreButton);

        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                GameOverActivity.this.startActivity(intent);
            }
        });

        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, HighScore.class);
                Bundle bundle = new Bundle();
                bundle.putInt("difficulty", difficulty);
                intent.putExtras(bundle);
                GameOverActivity.this.startActivity(intent);
            }
        });


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            longitude = mLastLocation.getLatitude();
            latitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
