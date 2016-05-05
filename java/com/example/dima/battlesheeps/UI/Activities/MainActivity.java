package com.example.dima.battlesheeps.UI.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Fragments.DifficultyFragment;
import com.example.dima.battlesheeps.UI.UIListeners.MainActivityDifficultyDialogListener;
import com.example.dima.battlesheeps.UI.Constants.Constants;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements MainActivityDifficultyDialogListener {

    private final String TAG = "MainActivity";

    private HashMap<String, String> mSettings = new HashMap<>();

    private LinearLayout difficultyDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSettings();

        setContentView(R.layout.activity_main);

        Button startGameButton = (Button) findViewById(R.id.startGameButton);
        if (startGameButton != null) {
            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_SETTINGS_KEY, mSettings);
                    intent.putExtras(bundle);
                    MainActivity.this.startActivity(intent);
                }
            });
        }

        difficultyDialogButton = (LinearLayout) findViewById(R.id.difficultyButton);
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
        difficultyFragment.setCurrentDifficulty(Integer.parseInt(mSettings.get(Constants.SETTINGS_DIFFICULTY_KEY)));
        difficultyFragment.registerUIListener(this);
        difficultyFragment.show(fm, "fragment_difficulty");
    }

    @Override
    public void onDialogSubmit(int chosenDifficulty) {
        setNewDifficultyOnView(chosenDifficulty);
    }

    private void initSettings(){
        mSettings.put(Constants.SETTINGS_DIFFICULTY_KEY,"0");
    }

    public void setNewDifficultyOnView(int newDifficulty){
        mSettings.put(Constants.SETTINGS_DIFFICULTY_KEY,newDifficulty + "");
        ImageView diffImage = (ImageView) findViewById(R.id.difficultyImage);
        TextView diffText = (TextView) findViewById(R.id.difficultyText);
        int imageId = 0;
        String difficultyText = "Baby Sheep";
        switch(newDifficulty){
            case 0:
                difficultyText = "Baby Sheep";
                imageId = R.drawable.difficulty_0;
                break;
            case 1:
                difficultyText = "Rambo Sheep";
                imageId = R.drawable.difficulty_1;
                break;
            case 2:
                difficultyText = "Killer Sheep";
                imageId = R.drawable.difficulty_2;
                break;
        }
        diffImage.setImageResource(imageId);
        diffText.setText(difficultyText);
    }
}
