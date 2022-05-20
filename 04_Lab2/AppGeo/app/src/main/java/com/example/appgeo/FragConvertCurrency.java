package com.example.appgeo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DTO.CountryCurrencyDTO;

public class FragConvertCurrency extends Fragment {
    protected FragmentActivity mActivity;
    static final String CRAW_LIST_CURRENCY = "https://www.fxexchangerate.com/currency-converter-rss-feed.html";
    private static final String FILE_NAME = "history.txt";
    static ArrayList<CountryCurrencyDTO> listCurrency = new ArrayList<CountryCurrencyDTO>();
    static ArrayList<String> listValueDropDown = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    View view;
    Spinner dropdown1, dropdown2;
    Button btnConvert;
    Button btnHistory;
    TextView txtResult;
    EditText inputValue;
    HandleXML obj;
    TextView txtListHistory;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.frag_convert_currency, container, false);
        initView();
        adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item, listValueDropDown);
        crawlDataCurrencyDropDown();

        // Event: onClick
        btnConvert.setOnClickListener(handleConvertCurrency);
        btnHistory.setOnClickListener(handleShowDialogHistory);
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
        dropdown1 = (Spinner) view.findViewById(R.id.spinner1);
        dropdown2 = (Spinner) view.findViewById(R.id.spinner2);
        txtResult = (TextView) view.findViewById(R.id.txtResult);
        inputValue = (EditText) view.findViewById(R.id.inputValue);
        btnConvert = (Button) view.findViewById(R.id.btnConvert);
        btnHistory = (Button) view.findViewById(R.id.btnHistory);
    }

    // init Dropdown
    public void initDropDown(int vt1, int vt2) {
        for (CountryCurrencyDTO currency : listCurrency) {
            listValueDropDown.add(currency.getName());
        }
        Log.e("Country List", listValueDropDown.toString());
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);

        dropdown1.setSelection(vt1);
        dropdown2.setSelection(vt2);

        adapter.notifyDataSetChanged();
    }

    protected void crawlDataCurrencyDropDown() {
        Thread CrawlList = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder content = new StringBuilder();
                try {
                    URL url = new URL(CRAW_LIST_CURRENCY);
                    InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                    }
                    bufferedReader.close();

                    org.jsoup.nodes.Document document = Jsoup.parse(content.toString());
                    org.jsoup.nodes.Element fxSidebarFrom = document.getElementById("fxSidebarFrom");
                    Elements inputElements = fxSidebarFrom.getElementsByTag("option");
                    for (org.jsoup.nodes.Element inputElement : inputElements) {
                        String code = inputElement.attr("value");
                        String name = inputElement.text();
                        CountryCurrencyDTO CountryCurrencyDTO = new CountryCurrencyDTO(code, name);
                        listCurrency.add(CountryCurrencyDTO);
                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initDropDown(0, 1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        CrawlList.start();
    }


    // Handle onClick
    private View.OnClickListener handleConvertCurrency = new View.OnClickListener() {
        public void onClick(View v) {
            String currency1 = listCurrency.get(dropdown1.getSelectedItemPosition()).getCode().toLowerCase();
            String currency2 = listCurrency.get(dropdown2.getSelectedItemPosition()).getCode().toLowerCase();
            String url_rss = "https://" + currency1 + ".fxexchangerate.com/" + currency2 + ".xml";
            RSSParser(url_rss);
        }
    };

    private View.OnClickListener handleShowDialogHistory = new View.OnClickListener() {
        public void onClick(View v) {
            Dialog dialogHistory = new Dialog(mActivity);
            dialogHistory.setContentView(R.layout.dialog_history_currency);
            txtListHistory = (TextView) dialogHistory.findViewById(R.id.txtListHistory);
            load();
            dialogHistory.show();
        }
    };

    public void load() {
        FileInputStream fis = null;

        try {
            fis = mActivity.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            txtListHistory.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void RSSParser(String url) {
        obj = new HandleXML(url);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        String[] arrString = obj.getDescription().trim().split("<br/>");
        String[] resultOfCur1ToCur2 = arrString[0].trim().replaceAll(" +", " ").split(" ");
        String[] resultOfCur2ToCur1 = arrString[1].trim().replaceAll(" +", " ").split(" ");

        double valueResultOfCur1ToCur2 = Double.valueOf(String.valueOf(inputValue.getText().toString())) * Double.valueOf(resultOfCur1ToCur2[3]);
        double valueResultOfCur2ToCur1 = Double.valueOf(String.valueOf(inputValue.getText().toString())) * Double.valueOf(resultOfCur2ToCur1[3]);

        String resultCur1ToCur2 = String.valueOf(inputValue.getText()) + " " + resultOfCur1ToCur2[1] + " = " + String.valueOf(valueResultOfCur1ToCur2) + resultOfCur1ToCur2[4];
        String resultCur2ToCur1 = String.valueOf(inputValue.getText()) + " " + resultOfCur2ToCur1[1] + " = " + String.valueOf(valueResultOfCur2ToCur1) + resultOfCur2ToCur1[4];
        String resultToView = resultCur1ToCur2 + "\n" + resultCur2ToCur1;
        txtResult.setText(resultToView);
        save(resultToView);
//        Log.e("resultOfCur1ToCur2", resultOfCur1ToCur2);
//        Log.e("resultOfCur2ToCur1", resultOfCur2ToCur1);
    }


    public void save(String data) {
        String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String text =  "--------------------------\n"+data +  " \n" + strDate + "\n--------------------------\n";

        Toast.makeText(mActivity, "Saved to " + text + "/" + FILE_NAME,
                Toast.LENGTH_LONG).show();
        FileOutputStream fos = null;
        try {
            fos = mActivity.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write(text.getBytes());
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            Log.e("An error occurred.", e.toString());
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
