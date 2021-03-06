package com.cs246.EzBudget.mFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBBalanceDataRec;
import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;
import com.cs246.EzBudget.RECURRENT;
import com.cs246.EzBudget.mRecycler.CommonFragments;

import java.util.Calendar;

/**
 * This Fragment Allows the user to edit/delete/insert Data in the BalanceDataRec table of the Database
 */
public class DispRecBalDataFragment extends Fragment {





    private DBCategory myCatDB ;
    private DBBalanceData myDBBalanceData;
    private DBBalanceDataRec myDBBalanceDataRec;
    private boolean myAddToSumaryToo = false;

    Calendar myCalendar;
    DatePickerDialog myDateDialog;

    TextView myDueDate;
    EditText myValue;
    Switch myCategory;
    EditText myDescription;
    EditText myPaymentDate;
    RadioButton myStatusPaid;
    RadioButton myStatusNotPaid;

    RadioButton myRecOnce;
    RadioButton myRecWeekly;
    RadioButton myRecMonthly;
    RadioButton myRecBiWeekly;


    //this button will save the filds to the database
    Button mySaveButton;
    Button myUpdateButton;
    Button myDeleteButton;
    private View myView;
    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private ProgressBar myProgress=null;
    private Long myIDtoChange = Long.valueOf(-1);
    /**
     *
     */
    private LinearLayout myStatusLayOut;
    private LinearLayout myRecurrenceLayOut;



/*
Constructor
 */
    static public DispRecBalDataFragment newInstance(){
        DispRecBalDataFragment theFrag = new DispRecBalDataFragment();

        return theFrag;
    }

    /**
     *  Required empty public constructor
     */
    public DispRecBalDataFragment() {

        //
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_disp_rec_bal_data, container, false);

        bindLayOutItens();

        Bundle args = getArguments();
        if (args !=null) {
            myIDtoChange = args.getLong("id");
            myAddToSumaryToo = args.getBoolean("addToSummaryToo");

        }

        final FragmentManager fm=getActivity().getSupportFragmentManager();
        final ChooseCategoryDialogFrag tv=new ChooseCategoryDialogFrag();

        myProgress.setVisibility(View.INVISIBLE);
        myStatusLayOut.setVisibility(View.GONE);



        /**
         * Show Fragment to select the category on long click
         */
        /*
        myCategory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv.show(fm,"CATEGORY_SHOW_FRAGMENT");
                return true;
            }
        });*/

        myCatDB = new DBCategory(getActivity());
        myDBBalanceData = new DBBalanceData(getActivity());
        myDBBalanceDataRec = new DBBalanceDataRec(getActivity());

        if(myIDtoChange > 0) {

            if(myIDtoChange>0){  //update

                String theValue = "";
                String theDescription = "";
                Integer theStatus = PAY_STATUS.UNKNOWN;
                Long theCategory = Long.valueOf(-1);
                String theDueDate = "";
                String thePaymentDate = "";
                String theLastModification = "";
                String myCatName = "";
                Integer thePeriod = RECURRENT.UNKNOWN;
                Cursor rs = null;

                //means this came from the Edit Recurrent task in the menu
                    rs = myDBBalanceDataRec.getDataCursor(myIDtoChange);
                    if (rs !=null ) {
                        if (rs.getCount()>0) {
                            rs.moveToFirst();

                            //column values
                            theValue = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_VALUE));
                            theDescription = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_DESCRIPTION));
                            theStatus = PAY_STATUS.UNKNOWN;
                            theCategory = rs.getLong(rs.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_CATEGORY));
                            //this date is formateed in the Database Format
                            theDueDate = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_DUE_DATE));
                            theDueDate = DateHandler.convertStrFromDatabaseToHuman(theDueDate);
                            thePeriod = rs.getInt(rs.getColumnIndex(BalanceData.BALANCEDATAREC_COLUMN_PERIOD));

                            if(theCategory >0) myCatName = myCatDB.getName(theCategory);

                            if (!rs.isClosed()) {
                                rs.close();
                            }
                        }
                    }


                mySaveButton.setVisibility(View.INVISIBLE);
                myUpdateButton.setVisibility(View.VISIBLE);
                myDeleteButton.setVisibility(View.VISIBLE);

                myValue.setText((CharSequence)theValue);
                myValue.setFocusable(true);
                myValue.setClickable(true);

                myDescription.setText((CharSequence)theDescription);
                myDescription.setFocusable(true);
                myDescription.setClickable(true);

                //myCategory.setText(myCatName);
                myCategory.setFocusable(true);
                myCategory.setClickable(true);
                if (theCategory==DBCategory.GEN_INCOME) myCategory.setChecked(false);
                if (theCategory==DBCategory.GEN_OUTCOME) myCategory.setChecked(true);

                myDueDate.setText((CharSequence)theDueDate);
                myDueDate.setFocusable(false);
                myDueDate.setClickable(false);

                myPaymentDate.setText((CharSequence)thePaymentDate);
                myPaymentDate.setFocusable(true);
                myPaymentDate.setClickable(true);

                if (theStatus == PAY_STATUS.PAID_RECEIVED)  myStatusPaid.setChecked(true);
                else if (theStatus == PAY_STATUS.UNPAID_UNRECEIVED) myStatusNotPaid.setChecked(true);

                if(thePeriod == RECURRENT.UNKNOWN ) {
                    myRecOnce.setChecked(false);
                    myRecWeekly.setChecked(false);
                    myRecBiWeekly.setChecked(false);
                    myRecMonthly.setChecked(false);
                }
                else if (thePeriod == RECURRENT.ONCE) myRecOnce.setChecked(true);
                else if (thePeriod == RECURRENT.WEEKLY) myRecWeekly.setChecked(true);
                else if (thePeriod == RECURRENT.BI_WEEKLI) myRecBiWeekly.setChecked(true);
                else if (thePeriod == RECURRENT.MONTHLY) myRecMonthly.setChecked(true);

            }
        }else { //add new
            mySaveButton.setVisibility(View.VISIBLE);
            myUpdateButton.setVisibility(View.INVISIBLE);
            myDeleteButton.setVisibility(View.INVISIBLE);
            myRecOnce.setChecked(true);
            myCategory.setChecked(true);
        }

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
/**
 * Long Click Show the DatePick Dialog, otherwhise one can enter date manualy
 */



        myDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);

                myDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        setDueDateText(mDay ,mMonth,mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return;
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

                myDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        setMyPaymentDateText(mDay ,mMonth,mYear);

                    }
                },year,month,day);

                myDateDialog.show();
                return false;
            }
        });






        return myView;
    }

    /**
     * Bind the layout to the Variables
     */
    private void bindLayOutItens(){

        myDueDate = (TextView) myView.findViewById(R.id.dispRecBalDataDueDate);
        myValue = (EditText) myView.findViewById(R.id.dispRecBalDataValue);
        myCategory = (Switch) myView.findViewById(R.id.switchRecCategory);
        myDescription = (EditText) myView.findViewById(R.id.dispRecBalDataDescription);
        myPaymentDate = (EditText) myView.findViewById(R.id.dispRecBalDataPaymentDate);
        myStatusPaid = (RadioButton) myView.findViewById(R.id.dispRecBalDataRadioPaid);
        myStatusNotPaid = (RadioButton) myView.findViewById(R.id.dispRecBalDataRadioUnPaid);
        myRecOnce = (RadioButton) myView.findViewById(R.id.dispRecBalDataRadioOnce);
        myRecWeekly = (RadioButton) myView.findViewById(R.id.dispRecBalDataRadioWeekly);
        myRecMonthly = (RadioButton) myView.findViewById(R.id.dispRecBalDataRadioMonthly);
        myRecBiWeekly = (RadioButton) myView.findViewById(R.id.dispRecBalDataRadioBiWeekly);

        myProgress = (ProgressBar) myView.findViewById(R.id.dispRecBalDataBar);
        myStatusLayOut = (LinearLayout) myView.findViewById(R.id.dispRecBalDataLayoutStatus);
        myRecurrenceLayOut = (LinearLayout) myView.findViewById(R.id.dispRecBalDataLayoutRecurrence);
        mySaveButton = (Button)myView.findViewById(R.id.dispRecBalDataSave);
        myUpdateButton = (Button)myView.findViewById(R.id.dispRecBalDataUpdate);
        myDeleteButton = (Button)myView.findViewById(R.id.dispRecBalDataDelete);




    }

    /**
     * Set the text of the Due Date TextView
     * @param mDay   The Day
     * @param mMonth  the Month
     * @param mYear the Year
     */
    public void setDueDateText(int mDay ,int mMonth,int mYear){

        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
        //EditText myDate = myView.findViewById(R.id.dispBalViewEditInitialDate);
        myDueDate.setText(sdf.format(c.getTime()));

    }

    /**
     *  Set the Text of the Payent Date TextView
     * @param mDay the Day
     * @param mMonth the Month
     * @param mYear the Year
     */
    public void setMyPaymentDateText(int mDay ,int mMonth,int mYear){
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DateHandler.DATE_FORMAT,DateHandler.DEF_LOCALE);
        //EditText myDate = myView.findViewById(R.id.dispBalViewEditFinalDate);
        myPaymentDate.setText(sdf.format(c.getTime()));

    }

    /**
     * Runs when the Delete button is clicked
     * @param view
     */
    public void DeleteButton(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.deleteConfirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*myDBBalanceData.delete(myIDtoChange);

                        Toast.makeText(getActivity(), "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();*/
                        //delete the old records
                        Log.i("DBBalanceDataUpd", "Need to Update the related records: " + myIDtoChange);
                        if(myDBBalanceData.deleteSelected(myIDtoChange,myDBBalanceDataRec)){
                            if (CommonFragments.summaryFrag!=null) CommonFragments.summaryFrag.onResume();
                            Toast.makeText(getActivity().getApplicationContext(), " Deleted old Records from Balance Data", Toast.LENGTH_SHORT).show();
                            getActivity().getFragmentManager().popBackStack();
                        } else{
                            Toast.makeText(getActivity().getApplicationContext(), "not Deleted the Selected Selected", Toast.LENGTH_SHORT).show();
                        }
                        ChooseRecBalDataDialogFrag theChoFrag = (ChooseRecBalDataDialogFrag) CommonFragments.chooseRecData;
                        theChoFrag.close();
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
     * Set the text of the category TextView
     * @param theText
     */
    public void setCateGoryText(String theText){

        //myCategory.setText(theText);
    }

    /**
     * Run when the save button is clicked
     * @param view
     */
    public void SaveButton(View view) {
        Integer theStatus= PAY_STATUS.UNKNOWN;
        boolean theRecurrence = false;

        Double theValue = Double.parseDouble(myValue.getText().toString());
        String theDueDate = myDueDate.getText().toString();
        //String theCategoryNAME = myCategory.getText().toString();
        //Long theCategoryID = myCatDB.getID(theCategoryNAME);
        //if (theCategoryID == Category.UNKNOWN) {
        //    Toast.makeText(getActivity().getApplicationContext(), "UNKNOWN CATEGORY",
        //            Toast.LENGTH_SHORT).show();
        //    return;
        //}
        //if switch is income set the category general income
        //if switch is outcome set category to general outcome
        Long theCategoryID= Category.UNKNOWN;
        if (myCategory.isChecked()) {
            //it is a bill
            theCategoryID = DBCategory.GEN_OUTCOME;
        }else{
            //it is an income
            theCategoryID = DBCategory.GEN_INCOME;
        }


        String theDesc = myDescription.getText().toString();

        // STATUS Handling
        if(myStatusNotPaid.isChecked()) theStatus = PAY_STATUS.UNPAID_UNRECEIVED;
        else if(myStatusPaid.isChecked()) theStatus = PAY_STATUS.PAID_RECEIVED;
        else theStatus = PAY_STATUS.UNKNOWN;
        String thePaymentDate = myPaymentDate.getText().toString();

        //if none is selected it is once
        int theRecurrencePeriod = RECURRENT.ONCE;
        if(myRecOnce.isChecked()) theRecurrencePeriod = RECURRENT.ONCE;
        else if (myRecWeekly.isChecked())    theRecurrencePeriod = RECURRENT.WEEKLY;
        else if (myRecBiWeekly.isChecked())    theRecurrencePeriod = RECURRENT.BI_WEEKLI;
        else if (myRecMonthly.isChecked())    theRecurrencePeriod = RECURRENT.MONTHLY;
        //Log.i("SALVAREC","The Recurrent period: "+theRecurrencePeriod);
        BalanceData theData = new BalanceData();

        theData.setRecurrent(theRecurrencePeriod);
        theData.setValue(theValue);
        theData.setDescription(theDesc);
        theData.setCategory(theCategoryID);
        theData.setDueDateFromHuman(theDueDate);
        theData.setPaymentDateFromHuman(thePaymentDate);
        theData.setStatus(theStatus);
        //Log.i("SALVADATA", "data After: "+ theData.getDueDateHuman());
        //Log.i("SALVAREC","is Recurrent?: "+theData.isRecurrent());
        Long Value = myIDtoChange;
        if(Value>0){  //edit

            //if it came from the recurrent list fragment

                BalanceData myRecordToInsert = theData.getCopy();
                myRecordToInsert.setID(myIDtoChange);
                if(myDBBalanceDataRec.update(myIDtoChange,theData)){
                    Toast.makeText(getActivity().getApplicationContext(), "Updated Recurrent Table", Toast.LENGTH_SHORT).show();
                    //delete the old records
                    Log.i("DBBalanceDataUpd", "Need to Update the related records: " + myIDtoChange);
                    if(myDBBalanceData.deleteSelected(myIDtoChange,myDBBalanceDataRec)){
                        Toast.makeText(getActivity().getApplicationContext(), " Deleted old Records from Balance Data", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "not Deleted the Selected Selected", Toast.LENGTH_SHORT).show();
                    }
                    //insert new ones
                    if (myDBBalanceData.insert(myRecordToInsert,myRecordToInsert.isRecurrent()) > 0) {
                        //Log.i("SALVAREC","CHECK the recurrence again "+theData.isRecurrent());


                        Toast.makeText(getActivity().getApplicationContext(), "Added New Records",
                                Toast.LENGTH_SHORT).show();
                        if (CommonFragments.summaryFrag != null) CommonFragments.summaryFrag.onResume();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "not Added the New Records",
                                Toast.LENGTH_SHORT).show();
                    }

                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }

            getActivity().getFragmentManager().popBackStack();

        } else{ //add


            /**
             * isRec = indicate we add in the recurrent table and in the balance data table
             */

                //Log.i("SALVAREC","ENTERED IN RECURRENT DATABASE ADD "+theData.isRecurrent());
                if(theData.IsRecurrent()) { //Add only if the recurrent period is other than once
                    Long recId = myDBBalanceDataRec.insert(theData);
                    if ( recId > 0) {

                        Toast.makeText(getActivity().getApplicationContext(), "Added in Recurrent Table",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "not Added in recuurent",
                                Toast.LENGTH_SHORT).show();
                    }
                    theData.setID(recId);
                }
                //came from recurrent but must add to summary as well
                if (myAddToSumaryToo){
                    if (myDBBalanceData.insert(theData, theData.isRecurrent()) > 0) {
                        //Log.i("SALVAREC","CHECK the recurrence again "+theData.isRecurrent());


                        Toast.makeText(getActivity().getApplicationContext(), "Added",
                                Toast.LENGTH_SHORT).show();
                        if (CommonFragments.summaryFrag != null) CommonFragments.summaryFrag.onResume();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "not Added",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            getActivity().getFragmentManager().popBackStack();
        }

        ChooseRecBalDataDialogFrag theChoFrag = (ChooseRecBalDataDialogFrag) CommonFragments.chooseRecData;
        theChoFrag.close();
    }

}

