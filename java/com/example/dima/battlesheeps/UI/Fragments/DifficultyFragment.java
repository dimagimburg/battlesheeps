package com.example.dima.battlesheeps.UI.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Listeners.DifficultyDialogListener;

import java.util.Vector;

public class DifficultyFragment extends DialogFragment {

    private EditText mEditText;
    private int selectedDifficulty;

    private Vector<DifficultyDialogListener> mListeners = new Vector<>();

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

                                for (DifficultyDialogListener l : mListeners) {
                                    l.onSubmitDifficultyDialog(convertedIdToDifficulty);
                                }
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

    public void registerListener(DifficultyDialogListener l){
        mListeners.add(l);
    }
}