package com.example.appgeo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import DTO.CountryDTO;

public class MainActivity extends AppCompatActivity {
    private TextView txtFragCountryList;
    private TextView txtFragConvertCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextView
        txtFragCountryList = (TextView) findViewById(R.id.txtFragCountryList);
        txtFragConvertCurrency = (TextView) findViewById(R.id.txtFragConvertCurrency);

        // Set OnClickListener
        txtFragCountryList.setOnClickListener(onclickFragmentCountry);
        txtFragConvertCurrency.setOnClickListener(onclickFragmentConvertCurrency);

        // Load fragment country list
        txtFragCountryList.callOnClick();
    }

    // Handling Event
    private View.OnClickListener onclickFragmentCountry = new View.OnClickListener() {
        public void onClick(View v) {
            setActionBar("Country", "#0047b3");
            FragCountryList FragCountryList = new FragCountryList();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container, FragCountryList);
            fragmentTransaction.commit();
        }
    };

    private View.OnClickListener onclickFragmentConvertCurrency = new View.OnClickListener() {
        public void onClick(View v) {
            setActionBar("Convert Currency", "#0047b3");
            FragConvertCurrency  FragConvertCurrency = new FragConvertCurrency();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container, FragConvertCurrency);
            fragmentTransaction.commit();
        }
    };

    public void setActionBar(String title, String bgColor) {
        // TODO Auto-generated method stub
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(bgColor)));
        actionBar.setTitle(title);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.show();

    }
}