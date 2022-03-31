package com.c.latansa.Profil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.c.latansa.R;

import java.util.Calendar;

public class Point extends AppCompatActivity {

    EditText idpointTanggalEditTExt;
    TextView idpointTotalTextView;
    RecyclerView idRecyclepoint;
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Point <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idpointTanggalEditTExt = findViewById(R.id.idpointtanggalEditText);
        idpointTanggalEditTExt.setFocusable(false);
        idpointTotalTextView = findViewById(R.id.idpointTotalPointTextView);
        idRecyclepoint = findViewById(R.id.idRecyclePoint);


        idpointTanggalEditTExt.setOnClickListener(v -> {

          showDateDialog();

        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showDateDialog(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> idpointTanggalEditTExt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();



    }
}