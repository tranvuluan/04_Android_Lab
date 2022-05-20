package com.example.appgeo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.net.URL;

import DTO.CountryDTO;

public class FragCountryDetail extends Fragment {
        protected FragmentActivity mActivity;
        TextView txtName, txtCapital, txtPopulation, txtCurrencyCode, txtCountryCode,
                txtLanguages;
        View view;
        ImageView imgFlag, imgMap;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            this.view = (View) inflater.inflate(R.layout.frag_country_detail, container, false);
            initView();
            Bundle bundle = this.getArguments();
            CountryDTO countryDTO = (CountryDTO) bundle.getSerializable("country");
//            Log.e("County", countryDTO.toString());
            loadInfo(countryDTO);
            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            if (context instanceof Activity) {
                mActivity = (FragmentActivity) context;
            }
        }

        protected void initView() {
            txtCountryCode = (TextView) view.findViewById(R.id.country_code);
            txtName = (TextView) view.findViewById(R.id.country_name);
            txtCapital = (TextView) view.findViewById(R.id.capital);
            txtPopulation = (TextView) view.findViewById(R.id.population);
            txtCurrencyCode = (TextView) view.findViewById(R.id.currency);
            txtLanguages = (TextView) view.findViewById(R.id.languages);
            imgFlag = (ImageView) view.findViewById(R.id.img_national_flag);
            imgMap = (ImageView) view.findViewById(R.id.img_map);
        }

        protected  void loadInfo(CountryDTO countryDTO) {
            txtName.setText(countryDTO.getCountryName());
            txtCapital.setText(countryDTO.getCapital());
            txtPopulation.setText(countryDTO.getPopulation());
            txtCurrencyCode.setText(countryDTO.getCurrencyCode());
            txtLanguages.setText(countryDTO.getLanguages());
            txtCountryCode.setText(countryDTO.getCountryCode());
            //load image country map
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // get flag image from url
                        URL url = new URL(countryDTO.getFlagUrl());
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        // get map image from url
                        URL urlMap = new URL(countryDTO.getMap());
                        Log.d("CountryDetailActivity", "urlMap: " + countryDTO.getMap());
                        Bitmap bmpMap = BitmapFactory.decodeStream(urlMap.openConnection().getInputStream());

                        mActivity.runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        imgFlag.setImageBitmap(bmp);
                                        imgMap.setImageBitmap(bmpMap);
                                    }
                                }
                        );
                    } catch (Exception e) {
                        Log.d("Error", e.toString());
                    }
                }
            });
            thread.start();
        }
}
