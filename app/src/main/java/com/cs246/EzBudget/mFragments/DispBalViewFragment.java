package com.cs246.EzBudget.mFragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.ListCategory;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DispBalViewFragment extends Fragment {

    private Button mySaveBtn;
    private DBBalanceView myDBBalView;
    private Calendar myCalendar;
    DatePickerDialog myDateDialog;
    EditText myInitialDate;
    EditText myFinalDate;
    EditText myKeyDate;
    private EditText myDesc;
    private EditText myTitle;
    private Boolean isCurrent;
    int id_To_Update = 0;

    View myView;
    int mDay;
    int mMonth;
    int mYear;


    @NonNull
    static public DispBalViewFragment newInstance(){
        return new DispBalViewFragment();
    }

    public DispBalViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView= inflater.inflate(R.layout.fragment_disp_bal_view, container, false);
        myView = theView;
        myDBBalView = new DBBalanceView(getActivity());
        mySaveBtn = theView.findViewById(R.id.dispBalViewSave);
        EditText myInitialDate = theView.findViewById(R.id.dispBalViewEditInitialDate);
        EditText myFinalDate = theView.findViewById(R.id.dispBalViewEditFinalDate);
        EditText myKeyDate = theView.findViewById(R.id.dispBalViewEditKeyDate);
        EditText myDesc = theView.findViewById(R.id.editBalViewDispBalViewDescr);
        EditText myTitle = theView.findViewById(R.id.dispBalViewEditViewTitle);
        Boolean isCurrent = false;
/*
        final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                //setFinalDate(dayOfMonth, monthOfYear, year);
                updateDisplay(dayOfMonth,monthOfYear,year);
            }
        };
        */
        mySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View theView) {
                SaveButton(theView);
            }
        });

        /*
        myInitialDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment newFragment = new DatePickerDialogFragment(mDateSetListener);
                newFragment.show(ft, "dialog");
                return false;
            }
        });



        myDateDialog = new DatePickerDialog(this,
                R.style.AppTheme, mDateSetListener, mYear, mMonth, mDay);


        myFinalDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myDateDialog.show();
                return false;
            }
        });
*/
        /**
         * Long Click Show the DatePick Dialog, otherwhise one can enter date manualy
         */
        myKeyDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myCalendar = Calendar.getInstance();
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);

                myDateDialog = new DatePickerDialog(myView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        setKeyDateText(mDay ,mMonth+1,mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });

/**
 * Long Click Show the DatePick Dialog, otherwhise one can enter date manualy
 */
        myInitialDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myCalendar = Calendar.getInstance();
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);

                myDateDialog = new DatePickerDialog(myView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        Integer moth = mMonth;
                        Log.i("DATE_SAL_MONTH: ",moth.toString());
                        setInitialDateText(mDay,mMonth+1,mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });



        return theView;
    }

    public void setKeyDateText(int mDay ,int mMonth,int mYear){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH,mMonth);
        c.set(Calendar.DAY_OF_MONTH,mDay);
        c.set(Calendar.YEAR,mYear);
        SimpleDateFormat sdf = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        EditText myDate = myView.findViewById(R.id.dispBalViewEditKeyDate);
        // Get the date today using Calendar object.
        Date today = c.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = sdf.format(today);
        myDate.setText(reportDate);

    }

    public void setInitialDateText(int mDay ,int mMonth,int mYear){
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth+1, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        EditText myDate = myView.findViewById(R.id.dispBalViewEditInitialDate);
        myDate.setText(sdf.format(c.getTime()));

    }
    public void setFinalDateText(int mDay ,int mMonth,int mYear){
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        EditText myDate = myView.findViewById(R.id.dispBalViewEditFinalDate);
        myDate.setText(sdf.format(c.getTime()));

    }



    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // do stuff with the date the user selected
    }


    /**
     * Run when the save button is clicked
     * @param view
     */
    public void SaveButton(View view) {
        Bundle extras = null;
        extras = getActivity().getIntent().getExtras();
        Integer theStatus= PAY_STATUS.UNKNOWN;


        String theIntialDate = myInitialDate.getText().toString();
        String theFinalDate = myFinalDate.getText().toString();
        String theKeyDate = myKeyDate.getText().toString();
        String theTitle = myTitle.getText().toString();
        Long theID;
        String theDesc = myDesc.getText().toString();


        BalanceView theData = new BalanceView();
        theData.setInitialDate(theIntialDate);
        theData.setDescription(theDesc);
        theData.setFinalDate(theFinalDate);
        theData.setKeyDate(theKeyDate);
        theData.setTitle(theTitle);
        //theData.setID(theID);
        //fOR NOW JUST THE ADD OPERATION AVAILABLE
        if(myDBBalView.insert(theData) > 0){
            Toast.makeText(getActivity().getApplicationContext(), "done",
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getActivity().getApplicationContext(), "not done",
                    Toast.LENGTH_SHORT).show();
        }
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){  //edit

                if(myDBBalView.update(id_To_Update,theData)){
                    Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{ //add

                if(myDBBalView.insert(theData)>0){
                    Toast.makeText(getActivity().getApplicationContext(), "saved",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not saved",
                            Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                //startActivity(intent);
            }
        }
    }

}
