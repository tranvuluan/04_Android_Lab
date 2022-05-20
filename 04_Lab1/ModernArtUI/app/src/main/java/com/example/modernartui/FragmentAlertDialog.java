package com.example.modernartui;

import android.view.MenuItem;

public interface FragmentAlertDialog {

    void showDialog ( MenuItem item );
    void doPositiveClick();
    void doNegativeClick();
}
