package com.example.dima.battlesheeps.UI.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.Controllers.LoginMenuController;
import com.example.dima.battlesheeps.MVCListeners.MainActivityEventListener;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Fragments.DifficultyFragment;
import com.example.dima.battlesheeps.UI.UIListeners.MainActivityDifficultyDialogListener;

import java.util.Vector;

public class MainActivity extends FragmentActivity implements MainActivityDifficultyDialogListener {

    private final String TAG = "MainActivity";

    private final Game mGame = new Game();
    private Vector<MainActivityEventListener> mMCVListeners = new Vector<>();

    private Button difficultyDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LoginMenuController gameController = new LoginMenuController(mGame,this);

        setContentView(R.layout.activity_main);

        Button startGameButton = (Button) findViewById(R.id.startGameButton);
        if (startGameButton != null) {
            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("game", mGame);
                    intent.putExtras(bundle);
                    gameController.destroy();
                    MainActivity.this.startActivity(intent);
                }
            });
        }

        difficultyDialogButton = (Button) findViewById(R.id.difficultyButton);
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
        difficultyFragment.setCurrentDifficulty(mGame.getmDifficulty());
        difficultyFragment.registerUIListener(this);
        difficultyFragment.show(fm, "fragment_difficulty");
    }

    public void registerMVCListener(MainActivityEventListener l){
        mMCVListeners.add(l);
    }

    @Override
    public void onDialogSubmit(int chosenDifficulty) {
        fireDifficultyChanged(chosenDifficulty);
    }

    private void fireDifficultyChanged(int difficulty){
        Log.e(TAG, "fireDifficultyChanged");
        for(MainActivityEventListener l : mMCVListeners){
            l.difficultyChangedOnDialog(difficulty);
        }
    }

    public void setNewDifficultyOnView(int newDifficulty){
        difficultyDialogButton.setText("Difficulty: " + newDifficulty);
    }
}
