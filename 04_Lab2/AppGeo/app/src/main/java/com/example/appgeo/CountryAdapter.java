package com.example.appgeo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import DTO.CountryDTO;

public class CountryAdapter extends BaseAdapter {
    private Context context;
    private List<CountryDTO> countryList;
    private int layout;

    public CountryAdapter(Context context, int layout, List<CountryDTO> countryList) {
        this.context = context;
        this.countryList = countryList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    class ViewHolder {
        TextView txtCountryName;
        TextView txtCountryCapital;
        TextView txtCountryCode;
        ImageView imgFlag;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();

            //mapping
            holder.txtCountryName = (TextView) view.findViewById(R.id.info_countryName);
            holder.txtCountryCode = (TextView) view.findViewById(R.id.info_countryCode);
            holder.txtCountryCapital = (TextView) view.findViewById(R.id.info_countryCapital);
            holder.imgFlag = (ImageView) view.findViewById(R.id.item_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // get country
        CountryDTO countryDTO = countryList.get(i);
        holder.txtCountryName.setText(countryDTO.getCountryName());
        holder.txtCountryCode.setText("Code: " + countryDTO.getCountryCode());
        holder.txtCountryCapital.setText("Capital: " + countryDTO.getCapital());
        // get image
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(countryDTO.getFlagUrl());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        public void run() {
                            holder.imgFlag.setImageBitmap(bmp);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Error", e.toString());
                }
            }
        });
        thread.start();
        return view;

    }
}
