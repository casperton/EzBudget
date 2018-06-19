package com.chabries.kirk.ezbudget;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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
    EditText myCategory;
    EditText myDescription;
    EditText myPaymentDate;
    RadioButton myStatusPaid;
    RadioButton myStatusNotPaid;
    CheckBox myRecurrent;
    Button mySaveButton;
    Button catBut;
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
        Button mySaveButton = (Button)findViewById(R.id.buttonSaveBalData);
        Button catBut = (Button)findViewById(R.id.buttonShowCat);

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
                        myDueDate.setText(mDay + "/"+(mMonth+1)+"/"+mYear);

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
        Bundle extras = getIntent().getExtras();
        Integer theStatus= PAY_STATUS.UNKNOWN;


        Double theValue = Double.parseDouble(myValue.getText().toString());
        String theDueDate = myDueDate.getText().toString();
        Integer theCategoryID = Integer.getInteger(myCategory.getText().toString());
        String theDesc = myDescription.getText().toString();
        Category theCategory = mydb.getCategory(theCategoryID);
        // STATUS Handling
        if(myStatusNotPaid.isChecked()) theStatus = PAY_STATUS.UNPAID_UNRECEIVED;
        else if(myStatusPaid.isChecked()) theStatus = PAY_STATUS.PAID_RECEIVED;
        String thePaymentDate = myPaymentDate.getText().toString();

        //RECURRENCE HANDLING
        boolean theRecurrence = myRecurrent.isChecked();


        BalanceData theData = new BalanceData();
        theData.setValue(theValue);
        theData.setDescription(theDesc);
        theData.setCategory(theCategory);
        //theData.setDate(theDueDate);
        //theData.setPaymentDate(thePaymentDate);
        theData.setStatus(theStatus);


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
                Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                startActivity(intent);
            }
        }
    }

    /**
     * Open the dialog to choose the Category
     * https://www.youtube.com/watch?v=Z7oekIFb7fA
     * @param theview
     */
    public void openCatDialog(View theview){
        AlertDialog.Builder theBuilder = new AlertDialog.Builder(this);
        LayoutInflater theInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View theRow = theInflater.inflate(R.layout.raw_category_item,null);


        theBuilder.setView(theRow);

        AlertDialog theDialog = theBuilder.create();
        theDialog.show();
    }
}
