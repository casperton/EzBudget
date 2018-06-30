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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cs246.EzBudget.BalanceData;
import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.Database.DBBalanceData;
import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.DateHandler;
import com.cs246.EzBudget.PAY_STATUS;
import com.cs246.EzBudget.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DispBalDataFragment extends Fragment {

    private DBCategory myCatDB ;
    private DBBalanceData myDBBalanceData;
    private boolean myIsRecurrent = false;
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
    Button myUpdateButton;
    Button myDeleteButton;
    private View myView;
    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;
    private ProgressBar myProgress=null;
    private Long myIDtoChange = Long.valueOf(-1);
    private LinearLayout myStatusLayOut;



    static public DispBalDataFragment newInstance(){
        DispBalDataFragment theFrag = new DispBalDataFragment();

        return theFrag;
    }

    public DispBalDataFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_disp_bal_data, container, false);

        Bundle args = getArguments();
        if (args !=null) {
            myIDtoChange = args.getLong("id");
            myIsRecurrent = args.getBoolean("isRec");
        }

        myDueDate = (EditText) myView.findViewById(R.id.dispBalDataDueDate);
        myValue = (EditText) myView.findViewById(R.id.dispBalDataValue);
        myCategory = (EditText) myView.findViewById(R.id.dispBalDataCategory);
        myDescription = (EditText) myView.findViewById(R.id.dispBalDataDescription);
        myPaymentDate = (EditText) myView.findViewById(R.id.dispBalDataPaymentDate);
        myStatusPaid = (RadioButton) myView.findViewById(R.id.dispBalDataRadioPayd);
        myStatusNotPaid = (RadioButton) myView.findViewById(R.id.dispBalDataRadioUnPaid);
        myRecurrent = (CheckBox) myView.findViewById(R.id.dispBalDataCheckBoxRecurrent);
        myProgress = (ProgressBar) myView.findViewById(R.id.dispBalDataBar);
        myProgress.setVisibility(View.INVISIBLE);
        myStatusLayOut = (LinearLayout) myView.findViewById(R.id.dispBalDataLayoutStatus);
        mySaveButton = (Button)myView.findViewById(R.id.dispBalDataSave);
        myUpdateButton = (Button)myView.findViewById(R.id.dispBalDataUpdate);
        myDeleteButton = (Button)myView.findViewById(R.id.dispBalDataDelete);

        final FragmentManager fm=getActivity().getSupportFragmentManager();
        final ChooseCategoryDialogFrag tv=new ChooseCategoryDialogFrag();

        if (myIsRecurrent) myStatusLayOut.setVisibility(View.INVISIBLE);

        /**
         * Show Fragment to select the category on long click
         */
        myCategory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv.show(fm,"CATEGORY_SHOW_FRAGMENT");
                return true;
            }
        });

        myCatDB = new DBCategory(getActivity());
        myDBBalanceData = new DBBalanceData(getActivity());

        if(myIDtoChange > 0) {
           Long Value = myIDtoChange;

            if(Value>0){  //update
                //means this is the view/edit part not the add  part.
                Cursor rs = myDBBalanceData.getDataCursor(Value);
                rs.moveToFirst();

                //column values
                String theValue = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_VALUE));
                String theDescription = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DESCRIPTION));
                Integer theStatus = rs.getInt(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_STATUS));
                Long theCategory = rs.getLong(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_CATEGORY));
                String theDueDate = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_DUE_DATE));
                String thePaymentDate = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_PAYMENT_DATE));
                String theLastModification = rs.getString(rs.getColumnIndex(BalanceData.BALANCEDATA_COLUMN_TIMESTAMP));

                String myCatName=myCatDB.getName(theCategory);

                if (!rs.isClosed())  {
                    rs.close();
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

                myCategory.setText(myCatName);
                myCategory.setFocusable(true);
                myCategory.setClickable(true);

                myDueDate.setText((CharSequence)theDueDate);
                myDueDate.setFocusable(false);
                myDueDate.setClickable(false);

                myPaymentDate.setText((CharSequence)thePaymentDate);
                myPaymentDate.setFocusable(true);
                myPaymentDate.setClickable(true);

                if (theStatus == PAY_STATUS.PAID_RECEIVED)  myStatusPaid.setChecked(true);
                else if (theStatus == PAY_STATUS.UNPAID_UNRECEIVED) myStatusNotPaid.setChecked(true);


            }
        }else {
            mySaveButton.setVisibility(View.VISIBLE);
            myUpdateButton.setVisibility(View.INVISIBLE);
            myDeleteButton.setVisibility(View.INVISIBLE);
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
        myDueDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
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

    public void setDueDateText(int mDay ,int mMonth,int mYear){

        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DateHandler.DATE_FORMAT);
        //EditText myDate = myView.findViewById(R.id.dispBalViewEditInitialDate);
        myDueDate.setText(sdf.format(c.getTime()));

    }
    public void setMyPaymentDateText(int mDay ,int mMonth,int mYear){
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DateHandler.DATE_FORMAT);
        //EditText myDate = myView.findViewById(R.id.dispBalViewEditFinalDate);
        myPaymentDate.setText(sdf.format(c.getTime()));

    }

    public void DeleteButton(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setMessage(R.string.deleteConfirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDBBalanceData.delete(myIDtoChange);
                        Toast.makeText(getActivity().getApplicationContext(), "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                       // startActivity(intent);
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

public void setCateGoryText(String theText){

        myCategory.setText(theText);
}
    /**
     * Run when the save button is clicked
     * @param view
     */
    public void SaveButton(View view) {
        Integer theStatus= PAY_STATUS.UNKNOWN;


        Double theValue = Double.parseDouble(myValue.getText().toString());
        String theDueDate = myDueDate.getText().toString();
        String theCategoryNAME = myCategory.getText().toString();
        Long theCategoryID = myCatDB.getID(theCategoryNAME);
        if (theCategoryID == Category.UNKNOWN) {
            Toast.makeText(getActivity().getApplicationContext(), "UNKNOWN CATEGORY",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String theDesc = myDescription.getText().toString();
        Category theCategory = myCatDB.get(theCategoryID);
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
        theData.setCategory(theCategoryID);
        theData.setDueDateFromHuman(theDueDate);
        theData.setPaymentDateFromHuman(thePaymentDate);
        theData.setStatus(theStatus);


        Long Value = myIDtoChange;
        if(Value>0){  //edit

            if(myDBBalanceData.update(myIDtoChange,theData)){
                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                //startActivity(intent);
            } else{
                Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
            }
        } else{ //add

            if(myDBBalanceData.insert(theData) > 0){
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
