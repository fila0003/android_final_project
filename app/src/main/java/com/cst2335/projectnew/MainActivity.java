package com.cst2335.projectnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    Button btnSearch, btnLoadFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnLoadFromDatabase = findViewById(R.id.btnLoadFromDatabase);

        String lastSearch = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("SEARCH", "");


        etSearch.setText(lastSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = etSearch.getText().toString().trim().toLowerCase();

                if (!search.isEmpty())
                {
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("SEARCH", search).apply();

                    DetailsFragment.flag = 0;
                    Intent intent = new Intent(MainActivity.this, CarsActivity.class);
                    intent.putExtra("searchString", search);
                    startActivity(intent);
                }

            }
        });

        btnLoadFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailsFragment.flag = 1;
                Intent intent = new Intent(MainActivity.this, LocalStorageCarsActivity.class);
                startActivity(intent);

            }
        });

    }
}