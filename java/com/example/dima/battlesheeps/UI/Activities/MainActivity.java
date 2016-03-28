package com.example.dima.battlesheeps.UI.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Fragments.DifficultyFragment;
import com.example.dima.battlesheeps.UI.Listeners.DifficultyDialogListener;

public class MainActivity extends FragmentActivity implements DifficultyDialogListener {

    private final String TAG = "MainActivity";

    private final Game mGame = new Game();
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGameButton = (Button) findViewById(R.id.startGameButton);
        if (startGameButton != null) {
            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("difficulty", mGame.getmDifficulty());
                    bundle.putSerializable("board", mGame.getmBoard());
                    intent.putExtras(bundle);
                    MainActivity.this.startActivity(intent);
                }
            });
        }

        Button difficultyDialogButton = (Button) findViewById(R.id.difficultyButton);
        if (difficultyDialogButton != null) {
            difficultyDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDifficultyDialog();
                }
            });
        }
    }

    private void showDifficultyDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DifficultyFragment difficultyFragment = new DifficultyFragment();
        difficultyFragment.registerListener(this);

        difficultyFragment.show(fm, "fragment_difficulty");
    }

    @Override
    public void onSubmitDifficultyDialog(int difficultyChosen) {
        Log.e(TAG,"Difficulty Chosen: " + difficultyChosen);
        difficulty = difficultyChosen;
    }
}
