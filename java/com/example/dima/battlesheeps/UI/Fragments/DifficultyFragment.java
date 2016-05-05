package com.example.dima.battlesheeps.UI.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.UIListeners.MainActivityDifficultyDialogListener;

import java.util.Vector;

public class DifficultyFragment extends DialogFragment {

    private final String TAG = "DifficultyFragment";

    private int currentDifficulty;
    private Vector<MainActivityDifficultyDialogListener> mUIListeners = new Vector<>();

    public DifficultyFragment() {
        // Empty constructor required for DialogFragment
    }

    public int getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(int currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    private void setCurrentDificultyonView(RadioGroup rg){
        switch(getCurrentDifficulty()){
            case 0:
            default:
                rg.check(R.id.amateur);
                break;
            case 1:
                rg.check(R.id.advanced);
                break;
            case 2:
                rg.check(R.id.hard);
                break;
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_difficulty, null);

        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.difficultyGroup);
        setCurrentDificultyonView(rg);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Please Choose Difficulty:")
                .setPositiveButton("Choose",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int chosenId = rg.getCheckedRadioButtonId();
                                int convertedIdToDifficulty;
                                switch (chosenId) {
                                    case R.id.amateur:
                                        default:
                                        convertedIdToDifficulty = 0;
                                        break;
                                    case R.id.advanced:
                                        convertedIdToDifficulty = 1;
                                        break;
                                    case R.id.hard:
                                        convertedIdToDifficulty = 2;
                                        break;
                                }
                                setNewDifficultyOnView(convertedIdToDifficulty);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        builder.setView(view);
        return builder.create();

    }

    public void registerUIListener(MainActivityDifficultyDialogListener l){
        mUIListeners.add(l);
    }

    private void fireToUIDifficultyChanged(int difficulty){
        for(MainActivityDifficultyDialogListener l : mUIListeners){
            l.onDialogSubmit(difficulty);
        }
    }

    public void setNewDifficultyOnView(int newDifficulty){
        fireToUIDifficultyChanged(newDifficulty);
    }

}
