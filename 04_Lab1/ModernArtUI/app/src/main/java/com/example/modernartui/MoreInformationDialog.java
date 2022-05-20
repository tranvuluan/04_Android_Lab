package com.example.modernartui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
public class MoreInformationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog ( Bundle savedInstanceState ) {



        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        build.create();
        build.setMessage( R.string.dialog_text);
        build.setPositiveButton( R.string.dialog_visit, (dialog, id) ->
        {
            ((MainActivity)getActivity()).doPositiveClick();
        });
        build.setNegativeButton(R.string.dialog_not_now, (dialog, id) ->
        {
            ((MainActivity)getActivity()).doNegativeClick();
        });
        return build.show();
    }
}

