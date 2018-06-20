package com.chabries.kirk.ezbudget;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

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
    EditText myCategory;
    EditText myDescription;
    EditText myPaymentDate;
    RadioButton myStatusPaid;
    RadioButton myStatusNotPaid;
    CheckBox myRecurrent;
    Button mySaveButton;

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private ProgressBar myProgress=null;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_bal_data);

        myDueDate = (EditText) findViewById(R.id.editBalDataDueDate);
        myValue = (EditText) findViewById(R.id.editBalDataValue);
        myCategory = (EditText) findViewById(R.id.editBalDataCategory);
        myDescription = (EditText) findViewById(R.id.editBalDataDescription);
        myPaymentDate = (EditText) findViewById(R.id.editBalDataPaymentDate);
        myStatusPaid = (RadioButton) findViewById(R.id.radioPayd);
        myStatusNotPaid = (RadioButton) findViewById(R.id.radioUnPaid);
        myRecurrent = (CheckBox) findViewById(R.id.checkBoxRecurrent);
        myProgress = (ProgressBar) findViewById(R.id.progressBarBalData);
        myProgress.setVisibility(View.INVISIBLE);

        Button mySaveButton = (Button)findViewById(R.id.buttonSaveBalData);

        final FragmentManager fm=getFragmentManager();
        final  TVShowFragment tv=new TVShowFragment();

        /**
         * Show Fragment to select the category on long click
         */
        myCategory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv.show(fm,"TV_tag");
                return true;
            }
        });

        mydb = new DBHelper(this);
        RadioButton myIncome,myOutcome,myInformative;


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            myIncome = (RadioButton) findViewById(R.id.radioIncome);
            myOutcome = (RadioButton) findViewById(R.id.radioOutcome);
            myInformative = (RadioButton) findViewById(R.id.radioInformative);

            if(Value>0){
                //means this is the view/edit part not the add  part.
                Cursor rs = mydb.getBalanceDataData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                //column values
                String theValue = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                String theDescription = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                Integer theStatus = rs.getInt(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
                Integer theCategory = rs.getInt(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                String theDueDate = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                String thePaymentDate = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE));
                String theLastModification = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP));



                if (!rs.isClosed())  {
                    rs.close();
                }

                mySaveButton.setVisibility(View.INVISIBLE);

                myValue.setText((CharSequence)theValue);
                myValue.setFocusable(false);
                myValue.setClickable(false);

                myDescription.setText((CharSequence)theDescription);
                myDescription.setFocusable(false);
                myDescription.setClickable(false);

                myCategory.setText(theCategory);
                myCategory.setFocusable(false);
                myCategory.setClickable(false);

                myDueDate.setText((CharSequence)theDueDate);
                myDueDate.setFocusable(false);
                myDueDate.setClickable(false);

                myPaymentDate.setText((CharSequence)thePaymentDate);
                myPaymentDate.setFocusable(false);
                myPaymentDate.setClickable(false);

                if (theStatus == PAY_STATUS.PAID_RECEIVED)  myStatusPaid.setChecked(true);
                else if (theStatus == PAY_STATUS.UNPAID_UNRECEIVED) myStatusNotPaid.setChecked(true);


            }
        }

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
                        //Todo: How to apply the correct date format here
                        myDueDate.setText(mDay + "/"+(mMonth+1)+"/"+mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });

/**
 * Long Click Show the DatePick Dialog, otherwhise one can enter date manualy
 */
        myPaymentDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myCalendar = Calendar.getInstance();
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);

                myDateDialog = new DatePickerDialog(DispBalData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        //Todo: How to apply the correct date format here
                        myPaymentDate.setText(mDay + "/"+(mMonth+1)+"/"+mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });


    }

    /**
     * Run when the save button is clicked
     * @param view
     */
    public void SaveButton(View view) {
        Bundle extras = null;
        extras = getIntent().getExtras();
        Integer theStatus= PAY_STATUS.UNKNOWN;


        Double theValue = Double.parseDouble(myValue.getText().toString());
        String theDueDate = myDueDate.getText().toString();
        String theCategoryNAME = myCategory.getText().toString();
        Integer theCategoryID = mydb.getCategoryID(theCategoryNAME);
        if (theCategoryID == Category.UNKNOWN) {
            Toast.makeText(getApplicationContext(), "UNKNOWN CATEGORY",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String theDesc = myDescription.getText().toString();
        Category theCategory = mydb.getCategory(theCategoryID);
        // STATUS Handling
        if(myStatusNotPaid.isChecked()) theStatus = PAY_STATUS.UNPAID_UNRECEIVED;
        else if(myStatusPaid.isChecked()) theStatus = PAY_STATUS.PAID_RECEIVED;
        else theStatus = PAY_STATUS.UNKNOWN;
        String thePaymentDate = myPaymentDate.getText().toString();

        //RECURRENCE HANDLING
        boolean theRecurrence = myRecurrent.isChecked();


        BalanceData theData = new BalanceData();
        theData.setValue(theValue);
        theData.setDescription(theDesc);
        theData.setCategory(mydb.getCategory(theCategoryID));
        theData.setDate(theDueDate);
        theData.setPaymentDate(thePaymentDate);
        theData.setStatus(theStatus);

        //fOR NOW JUST THE ADD OPERATION AVAILABLE
        if(mydb.insertBalanceData(theData)){
            Toast.makeText(getApplicationContext(), "done",
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "not done",
                    Toast.LENGTH_SHORT).show();
        }
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){  //edit

                if(mydb.updateBalanceData(id_To_Update,theData)){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{ //add

                if(mydb.insertBalanceData(theData)){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                //startActivity(intent);
            }
        }
    }


}
