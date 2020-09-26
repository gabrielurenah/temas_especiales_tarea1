package com.pucmm.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    private TextView mFullName;
    private TextView mGender;
    private TextView mDate;
    private TextView mProgramming;
    private TextView mLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mFullName = findViewById(R.id.full_name);
        mGender = findViewById(R.id.user_gender);
        mDate = findViewById(R.id.user_date);
        mProgramming = findViewById(R.id.user_programming);
        mLanguages = findViewById(R.id.user_languages);

        Intent intent = getIntent();
        HashMap<String, String> form = (HashMap<String, String>) intent.getSerializableExtra(MainActivity.FORM);

        if (form != null) {
            mFullName.setText(form.get(MainActivity.NAME));
            mGender.setText(form.get(MainActivity.GENDER));
            mDate.setText(form.get(MainActivity.BIRTHDATE));
            mProgramming.setText(form.get(MainActivity.PROGRAMMING));
            mLanguages.setText(form.get(MainActivity.LANGUAGES));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}