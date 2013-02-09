package com.rvr.fourteenone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

/**
 * Created with IntelliJ IDEA.
 * User: ramon
 * Date: 26-12-12
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class SelectPlayerDialogFragment extends DialogFragment {

    static SelectPlayerDialogFragment newInstance(String title, String player1, String player2) {
        SelectPlayerDialogFragment selectPlayer = new SelectPlayerDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("player1", player1);
        args.putString("player2", player2);
        selectPlayer.setArguments(args);
        return selectPlayer;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher)
                .setTitle(getArguments().getString("title"))
                .setPositiveButton(getArguments().getString("player2"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity)getActivity()).doPlayer2Click();
                            }
                        })
                .setNegativeButton(getArguments().getString("player1"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity)getActivity()).doPlayer1Click();
                            }
                        })
                .create();
    }
}
