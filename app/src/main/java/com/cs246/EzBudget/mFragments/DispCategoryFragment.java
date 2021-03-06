package com.cs246.EzBudget.mFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cs246.EzBudget.Category;
import com.cs246.EzBudget.Database.DBCategory;
import com.cs246.EzBudget.OPERATION;
import com.cs246.EzBudget.R;

/**
 * This Fragment will show the Category information on the screen
 * It will allow add and edit Category information like Description,credit/debit,  etc.
 */
public class DispCategoryFragment extends Fragment {

    private DBCategory mydb ;
    private Button mySaveButton;
    private Button myUpdateButton;
    private Button myDeleteButton;

    TextView myName ;
    TextView myDescription;
    RadioGroup myOperation;
    View myView;

    Long myIDtoChange = Long.valueOf(-1);

    @NonNull
    static public DispCategoryFragment newInstance(){
        return new DispCategoryFragment();
    }

    /**
     * Required empty public constructor
     */
    public DispCategoryFragment() {
        //
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_disp_category, container, false);

        mySaveButton = myView.findViewById(R.id.dispCategorySave);
        myUpdateButton = myView.findViewById(R.id.dispCategoryUpdate);
        myDeleteButton = myView.findViewById(R.id.dispCategoryDelete);

        myName = (TextView) myView.findViewById(R.id.dispCatTextName);
        myDescription = (TextView) myView.findViewById(R.id.dispCatDescription);
        myOperation = (RadioGroup) myView.findViewById(R.id.dispCatRadioGroupOperation);
        RadioButton myIncome,myOutcome,myInformative;
        myIncome = (RadioButton) myView.findViewById(R.id.dispCatRadioIncome);
        myOutcome = (RadioButton) myView.findViewById(R.id.dispCatRadioOutcome);
        myInformative = (RadioButton) myView.findViewById(R.id.dispCatRadioInformative);

        mydb = new DBCategory(getActivity());


        Bundle args = getArguments();


        if(args !=null) {
            myIDtoChange = args.getLong("id");


            if(myIDtoChange>0){
                //means this is the view/edit part not the add category part.
                Cursor rs = mydb.getDataCursor(myIDtoChange);
                String thisName = "";
                String thisDescription = "";
                Integer thisOperation = OPERATION.UNKNOWN;

                if (rs !=null) {
                    rs.moveToFirst();

                    //column values
                    thisName = rs.getString(rs.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
                    thisDescription = rs.getString(rs.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION));
                    thisOperation = rs.getInt(rs.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION));


                    if (!rs.isClosed()) {
                        rs.close();
                    }
                }
                mySaveButton.setVisibility(View.INVISIBLE);
                myUpdateButton.setVisibility(View.VISIBLE);
                myDeleteButton.setVisibility(View.VISIBLE);


                myName.setText((CharSequence)thisName);
                myName.setFocusable(false);
                myName.setClickable(false);

                myDescription.setText((CharSequence)thisDescription);
                myDescription.setFocusable(false);
                myDescription.setClickable(false);

                if (thisOperation == OPERATION.CREDIT)  myIncome.setChecked(true);
                else if (thisOperation == OPERATION.DEBIT) myOutcome.setChecked(true);
                else if (thisOperation == OPERATION.INFORMATIVE) myInformative.setChecked(true);


            }else{
                mySaveButton.setVisibility(View.VISIBLE);
                myUpdateButton.setVisibility(View.INVISIBLE);
                myDeleteButton.setVisibility(View.INVISIBLE);
            }
        }

        //Click Listeners
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


        return myView;
    }

    /**
     * Runs when the Delete Button is pressed
     * @param view the view
     */
    public void DeleteButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.deleteConfirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.delete(myIDtoChange);
                        Toast.makeText(getActivity(), "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();

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
     * Run when the save/update button is clicked
     * @param view
     */
    public void SaveButton(View view) {
        Bundle extras = getActivity().getIntent().getExtras();
        Integer thisOperation= OPERATION.UNKNOWN;

        RadioButton income = (RadioButton) myView.findViewById(R.id.dispCatRadioIncome);
        RadioButton outcome = (RadioButton) myView.findViewById(R.id.dispCatRadioOutcome);
        RadioButton informative = (RadioButton) myView.findViewById(R.id.dispCatRadioInformative);



            if(myIDtoChange>0){  //edit Category
                String thisName = myName.getText().toString();
                String thisDesc = myDescription.getText().toString();
                if(income.isChecked()) thisOperation = OPERATION.CREDIT;
                else if(outcome.isChecked()) thisOperation = OPERATION.DEBIT;
                else if(informative.isChecked()) thisOperation = OPERATION.INFORMATIVE;

                if(mydb.update(myIDtoChange,thisName,thisDesc, thisOperation)){
                    Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{ //add Category
                String thisName = myName.getText().toString();
                String thisDesc = myDescription.getText().toString();
                if(income.isChecked()) thisOperation = OPERATION.CREDIT;
                else if(outcome.isChecked()) thisOperation = OPERATION.DEBIT;
                else if(informative.isChecked()) thisOperation = OPERATION.INFORMATIVE;
                if(mydb.insert(thisName,thisDesc, thisOperation)>0){
                    Toast.makeText(getActivity().getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }

            }

    }





}
