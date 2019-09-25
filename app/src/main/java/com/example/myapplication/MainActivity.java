package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private List<Warrior> army = new ArrayList<>();
    private WarriorAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        army = new ArrayList<>();
        adaptor = new WarriorAdaptor(this, R.layout.row, army);
        ListView listView = findViewById(R.id.listview);

        listView.setAdapter(adaptor);
    }

    public void showChangeLanguageDialog(View view) {
        final String[] lang = {"English", "Thai"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        setLocale("en");
                        recreate();
                        break;
                    case 1:
                        setLocale("th");
                        recreate();
                        break;
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addData(View view) {
        final Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.form);
        dialog.setCancelable(true);

        Button OKButton = dialog.findViewById(R.id.ok_form_button);
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = dialog.findViewById(R.id.name_editText);
                EditText hpEditText = dialog.findViewById(R.id.hp_editText);
                EditText atkEditText = dialog.findViewById(R.id.atk_editText);

                String name = nameEditText.getText().toString();
                String hp = hpEditText.getText().toString();
                String atk = atkEditText.getText().toString();

                if (name.equals("") || hp.equals("") || atk.equals("")) {
                    Toast.makeText(MainActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
                }
                else {
                    army.add(new Warrior(name, Integer.parseInt(hp), Integer.parseInt(atk)));
                    adaptor.notifyDataSetChanged();
                }

                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void loadLocale() {
        SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String lang = pref.getString("My_lang", "");
        setLocale(lang);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getBaseContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();
    }

}
