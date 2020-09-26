package com.pucmm.tarea1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {

    private Button mClean;
    private Button mSend;
    private EditText mLastName;
    private EditText mName;
    private ImageView mCalendarIcon;
    private RadioGroup mProgramming;
    private Spinner mGender;
    private TextView mDate;
    private CheckBox mJava;
    private CheckBox mPython;
    private CheckBox mJavascript;
    private CheckBox mGolang;
    private CheckBox mCSharp;
    private CheckBox mCCPlusPlus;

    public static final String NAME = "NAME";
    public static final String GENDER = "GENDER";
    public static final String BIRTHDATE = "BIRTHDATE";
    public static final String PROGRAMMING = "PROGRAMMING";
    public static final String LANGUAGES = "LANGUAGES";
    public static final String FORM = "FORM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClean = findViewById(R.id.btn_clean);
        mSend = findViewById(R.id.btn_send);
        mDate = findViewById(R.id.birthday);
        mGender = findViewById(R.id.spn_gender);
        mLastName = findViewById(R.id.lastName);
        mName = findViewById(R.id.name);
        mCalendarIcon = findViewById(R.id.icon_calendar);
        mProgramming = findViewById(R.id.radio_group_prog);
        mCCPlusPlus = findViewById(R.id.cbx_c);
        mCSharp = findViewById(R.id.cbx_c_sharp);
        mJavascript = findViewById(R.id.cbx_js);
        mPython = findViewById(R.id.cbx_python);
        mGolang = findViewById(R.id.cbx_go);
        mJava = findViewById(R.id.cbx_java);

        //C A L E N D A R    S T A R T
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Fecha de Nacimiento");
        final MaterialDatePicker mdp = builder.build();

        mCalendarIcon.setOnClickListener(v -> mdp.show(getSupportFragmentManager(), "DATE_PICKER"));

        mdp.addOnPositiveButtonClickListener(selection -> mDate.setText(mdp.getHeaderText()));
        //C A L E N D A R    E N D

        mClean.setOnClickListener((View.OnClickListener) v -> clearForm((ViewGroup)findViewById(R.id.main_constraint_layout)));

        mSend.setOnClickListener(this::sendData);

        mProgramming.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            ViewGroup vg = (ViewGroup)findViewById(R.id.main_constraint_layout);
            if (checkedId == R.id.radio_no) {
                handleCheckboxes(vg, false);
            } else {
                handleCheckboxes(vg, true);
            }
        });
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);

            if (view instanceof EditText) { ((EditText)view).setText(""); }

            if (view instanceof Spinner) { ((Spinner)view).setSelection(0); }

            if (view instanceof RadioGroup) { ((RadioGroup)view).check(R.id.radio_yes); }

            if (view instanceof CheckBox) { ((CheckBox)view).setChecked(false); }

            if (view instanceof TextView) { mDate.setText(""); }

            if (view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    public boolean emptyFields(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);

            if (view instanceof EditText  && ((EditText) view).getText().toString().equals("")) {
                return true;
            }

            if (view instanceof TextView && mDate.getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    public void sendData(View v) {

        //V A L I D A T I O N S
        if (emptyFields((ViewGroup)findViewById(R.id.main_constraint_layout))) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getBaseContext(), InfoActivity.class);

        HashMap<String,String> form = new HashMap<>();
        int selectedRadioBtn = mProgramming.getCheckedRadioButtonId();
        String programming = "Me gusta programar.";
        String languages = "";

        if (selectedRadioBtn == R.id.radio_yes) {
            // CHECKBOX section
            StringJoiner joiner = new StringJoiner(", ");
            if (mJava.isChecked()) joiner.add(mJava.getText());
            if (mPython.isChecked()) joiner.add(mPython.getText());
            if (mJavascript.isChecked()) joiner.add(mJavascript.getText());
            if (mGolang.isChecked()) joiner.add(mGolang.getText());
            if (mCSharp.isChecked()) joiner.add(mCSharp.getText());
            if (mCCPlusPlus.isChecked()) joiner.add(mCCPlusPlus.getText());

            languages = joiner.toString();

        } else { programming = "No me gusta programar."; }

        form.put(NAME, mName.getText().toString() + " " + mLastName.getText().toString());
        form.put(GENDER, mGender.getSelectedItem().toString());
        form.put(BIRTHDATE, mDate.getText().toString());
        form.put(LANGUAGES, languages);
        form.put(PROGRAMMING, programming);

        intent.putExtra(FORM, form);
        startActivity(intent);
    }

    public void handleCheckboxes (ViewGroup group, boolean status) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);

            if(view instanceof CheckBox) {
                CheckBox cbx = (CheckBox)view;
                cbx.setEnabled(status);
                if (!status) cbx.setChecked(false);
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                handleCheckboxes((ViewGroup)view,status);
        }
    }

}