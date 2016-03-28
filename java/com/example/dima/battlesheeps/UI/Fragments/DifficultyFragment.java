package com.example.dima.battlesheeps.UI.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.MVCListeners.MainActivityEventListener;
import com.example.dima.battlesheeps.UI.UIListeners.MainActivityDifficultyDialogListener;

import java.util.Vector;

public class DifficultyFragment extends DialogFragment {

    private final String TAG = "DifficultyFragment";

    private Vector<MainActivityDifficultyDialogListener> mUIListeners = new Vector<>();

    public DifficultyFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_difficulty, null);

        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.difficultyGroup);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("title")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int chosenId = rg.getCheckedRadioButtonId();
                                int convertedIdToDifficulty;
                                switch (chosenId) {
                                    case R.id.amateur:
                                        default:
                                        convertedIdToDifficulty = 1;
                                        break;
                                    case R.id.advanced:
                                        convertedIdToDifficulty = 2;
                                        break;
                                    case R.id.hard:
                                        convertedIdToDifficulty = 3;
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
