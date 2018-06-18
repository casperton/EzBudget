package com.chabries.kirk.ezbudget;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DispBalData extends AppCompatActivity {

    private DBHelper mydb ;
/*
    public static final String BALANCEDATA_COLUMN_DUE_DATE = "date";
    public static final String BALANCEDATA_COLUMN_PAYMENT_DATE = "paymentDate";
    public static final String BALANCEDATA_COLUMN_DESCRIPTION = "description";
    public static final String BALANCEDATA_COLUMN_CATEGORY = "idCategory";
    public static final String BALANCEDATA_COLUMN_VALUE = "value";
    public static final String BALANCEDATA_COLUMN_STATUS = "status";
*/
    Calendar myCalendar;
    DatePickerDialog myDateDialog;
    EditText myDueDate;
    EditText myValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_bal_data);

        myDueDate = (EditText) findViewById(R.id.editBalDataDueDate);
        myValue = (EditText) findViewById(R.id.editBalDataValue);
/**
 * Long Click Show the DatePick Dialog, otherwhise one can enter date manualy
 */
        myDueDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myCalendar = Calendar.getInstance();
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);

                myDateDialog = new DatePickerDialog(DispBalData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        myDueDate.setText(mDay + "/"+(mMonth+1)+"/"+mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });




    }
}
