package com.cs246.EzBudget.mFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceView;
import com.cs246.EzBudget.Database.DBBalanceView;
import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DispBalViewFragment extends Fragment {

    private Button mySaveButton;
    private Button myUpdateButton;
    private Button myDeleteButton;
    private DBBalanceView myDBBalView;
    private Calendar myCalendar;
    DatePickerDialog myDateDialog;
    EditText myInitialDate;
    EditText myFinalDate;
    EditText myKeyDate;
    private EditText myDescription;
    private EditText myTitle;
    private CheckBox isCurrent;
    View myView;
    int mDay;
    int mMonth;
    int mYear;
    private Long myIDtoChange = Long.valueOf(-1);

    @NonNull
    static public DispBalViewFragment newInstance(){
        DispBalViewFragment theFrag = new DispBalViewFragment();

        return theFrag;
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
        mySaveButton = theView.findViewById(R.id.dispBalViewSave);
        myUpdateButton = theView.findViewById(R.id.dispBalViewUpdate);
        myDeleteButton = theView.findViewById(R.id.dispBalViewDelete);

        myInitialDate = theView.findViewById(R.id.dispBalViewEditInitialDate);
        myFinalDate = theView.findViewById(R.id.dispBalViewEditFinalDate);
        myKeyDate = theView.findViewById(R.id.dispBalViewEditKeyDate);
        myDescription = theView.findViewById(R.id.editBalViewDispBalViewDescr);
        myTitle = theView.findViewById(R.id.dispBalViewEditViewTitle);
        isCurrent = theView.findViewById(R.id.dispBalViewIsCurrent);

        Bundle args = getArguments();
        if (args !=null) myIDtoChange = args.getLong("id");


            

            if(myIDtoChange>0){
                //means this is the view/edit part not the add  part.
                Cursor rs = myDBBalView.getDataCursor(myIDtoChange);
                rs.moveToFirst();

                //column values
                Long theID = rs.getLong(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_ID));
                String theTitle = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_TITLE));
                String theDescription = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_DESCRIPTION));
                String theInitialDate = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_INI_DATE));
                String theFinalDate = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_FINAL_DATE));
                String theKeyDate = rs.getString(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_KEY_DATE));
                Integer theCurrent = rs.getInt(rs.getColumnIndex(BalanceView.BALANCEVIEW_COLUMN_IS_CURRENT));


                if (!rs.isClosed())  {
                    rs.close();
                }

                mySaveButton.setVisibility(View.INVISIBLE);
                myUpdateButton.setVisibility(View.VISIBLE);
                myDeleteButton.setVisibility(View.VISIBLE);

                myTitle.setText((CharSequence)theTitle);
                myTitle.setFocusable(true);
                myTitle.setClickable(true);

                myDescription.setText((CharSequence)theDescription);
                myDescription.setFocusable(true);
                myDescription.setClickable(true);

                myInitialDate.setText((CharSequence)theInitialDate);
                myInitialDate.setFocusable(true);
                myInitialDate.setClickable(true);

                myFinalDate.setText((CharSequence)theFinalDate);
                myFinalDate.setFocusable(true);
                myFinalDate.setClickable(true);

                myKeyDate.setText((CharSequence)theKeyDate);
                myKeyDate.setFocusable(true);
                myKeyDate.setClickable(true);

                if (theCurrent == 1)  isCurrent.setChecked(true);
                else  isCurrent.setChecked(false);


            }else{
                mySaveButton.setVisibility(View.VISIBLE);
                myUpdateButton.setVisibility(View.INVISIBLE);
                myDeleteButton.setVisibility(View.INVISIBLE);
            }


        
        
/*
        final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                //setFinalDate(dayOfMonth, monthOfYear, year);
                updateDisplay(dayOfMonth,monthOfYear,year);
            }
        };
        */
        mySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View theView) {
                SaveButton(theView);
            }
        });
        myUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View theView) {
                SaveButton(theView);
            }
        });
        myDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View theView) {
                DeleteButton(theView);
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
                        setKeyDateText(mDay ,mMonth,mYear);

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
                        setInitialDateText(mDay,mMonth,mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });

/**
 * Long Click Show the DatePick Dialog, otherwhise one can enter date manualy
 */
        myFinalDate.setOnLongClickListener(new View.OnLongClickListener() {
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
                        setFinalDateText(mDay,mMonth,mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });

        return theView;
    }

    public void setKeyDateText(Integer mDay ,Integer mMonth,Integer mYear){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH,mMonth);
        c.set(Calendar.DAY_OF_MONTH,mDay);
        c.set(Calendar.YEAR,mYear);
        SimpleDateFormat sdf = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        //EditText myDate = myView.findViewById(R.id.dispBalViewEditKeyDate);
        // Get the date today using Calendar object.
        Date today = c.getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = sdf.format(today);
        myKeyDate.setText(reportDate);

    }

    public void setInitialDateText(int mDay ,int mMonth,int mYear){

        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        EditText myDate = myView.findViewById(R.id.dispBalViewEditInitialDate);
        myDate.setText(sdf.format(c.getTime()));

    }
    public void setFinalDateText(int mDay ,int mMonth,int mYear){
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DBHelper.DATE_FORMAT);
        //EditText myDate = myView.findViewById(R.id.dispBalViewEditFinalDate);
        myFinalDate.setText(sdf.format(c.getTime()));

    }




 public void DeleteButton(View view){

     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
     builder.setMessage(R.string.deleteConfirmation)
             .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                     myDBBalView.delete(myIDtoChange);
                     Toast.makeText(getActivity().getApplicationContext(), "Deleted Successfully",
                             Toast.LENGTH_SHORT).show();
                     //Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                     //startActivity(intent);
                 }
             })
             .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                     // User cancelled the dialog
                 }
             });

     AlertDialog d = builder.create();
     d.setTitle("Are you sure");
     d.show();


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
        //Long theID;
        String theDesc = myDescription.getText().toString();

        BalanceView theData = new BalanceView();
        theData.setInitialDate(theIntialDate);
        theData.setDescription(theDesc);
        theData.setFinalDate(theFinalDate);
        theData.setKeyDate(theKeyDate);
        theData.setTitle(theTitle);
        //theData.setID(theID);
        //fOR NOW JUST THE ADD OPERATION AVAILABLE



            if(myIDtoChange>0){  //edit

                if(myDBBalView.update(myIDtoChange,theData)){
                    Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                    //startActivity(intent);
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{ //add

                if(myDBBalView.insert(theData) > 0){
                    Toast.makeText(getActivity().getApplicationContext(), "Added",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not Added",
                            Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                //startActivity(intent);
            }

    }

}
