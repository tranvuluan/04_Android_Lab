package com.example.appgeo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import DTO.CountryDTO;

public class FragCountryList extends Fragment {
    protected FragmentActivity mActivity;
    View view;
    ListView lvCountry;
    ArrayList<CountryDTO> countryList;
    CountryAdapter adapter;
    ImageView imgTest;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.frag_countrylist, container, false);
        initView();
        getCountryFromAPI();
        event();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity = (FragmentActivity) context;
        }
    }


    public void initView() {
        lvCountry = (ListView) view.findViewById(R.id.listView);
        countryList = new ArrayList<CountryDTO>();
    }

    public void getCountryFromAPI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder content = new StringBuilder();
                try {
                    URL url = new URL("http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&username=caoth&style=full");
                    InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    JSONObject jsonObject = new JSONObject(content.toString());
                    JSONArray jsonCountrys = jsonObject.getJSONArray("geonames");
                    for (int i = 0; i < jsonCountrys.length(); i++) {
                        JSONObject jsonCountry = jsonCountrys.getJSONObject(i);
                        CountryDTO country = new CountryDTO();
                        country.setCountryName(jsonCountry.getString("countryName"));
                        country.setCountryCode(jsonCountry.getString("countryCode"));
                        country.setCapital(jsonCountry.getString("capital"));
                        country.setPopulation(jsonCountry.getString("population"));
                        country.setContinent(jsonCountry.getString("continentName"));
                        country.setCurrencyCode(jsonCountry.getString("currencyCode"));
                        country.setGeonameId(jsonCountry.getString("geonameId"));
                        country.setLanguages(jsonCountry.getString("languages"));
                        country.setContinentName(jsonCountry.getString("continentName"));
                        country.setFlagUrl("http://img.geonames.org/flags/x/" + country.getCountryCode().toLowerCase() + ".gif");
                        country.setMap("http://img.geonames.org/img/country/250/" + country.getCountryCode() + ".png");
                        countryList.add(country);
                    }
                    if (mActivity != null) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new CountryAdapter(mActivity, R.layout.item_list, countryList);
                                lvCountry.setAdapter(adapter);
//                                Toast.makeText(mActivity, "Loaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", e.toString());
                }

            }
        }).start();
    }

    public  void event() {
        listViewEvent();
    }

    public void listViewEvent() {
        lvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryDTO country = countryList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("country", country);
                FragCountryDetail FragCountryDetail = new FragCountryDetail();
                FragCountryDetail.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, FragCountryDetail);
                fragmentTransaction.commit();
            }
        });
    }
}
