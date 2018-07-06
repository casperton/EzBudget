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
 * A simple {@link Fragment} subclass.
 */
public class DispCategoryFragment extends Fragment {

    private DBCategory mydb ;

    TextView myName ;
    TextView myDescription;
    RadioGroup myOperation;
    View myView;

    Long myIDtoChange = Long.valueOf(-1);

    @NonNull
    static public DispCategoryFragment newInstance(){
        return new DispCategoryFragment();
    }

    public DispCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_disp_category, container, false);


        myName = (TextView) myView.findViewById(R.id.dispCatTextName);
        myDescription = (TextView) myView.findViewById(R.id.dispCatDescription);
        myOperation = (RadioGroup) myView.findViewById(R.id.dispCatRadioGroupOperation);


        mydb = new DBCategory(getActivity());
        RadioButton myIncome,myOutcome,myInformative;

        Bundle args = getArguments();


        if(args !=null) {
            myIDtoChange = args.getLong("id");
            myIncome = (RadioButton) myView.findViewById(R.id.dispCatRadioIncome);
            myOutcome = (RadioButton) myView.findViewById(R.id.dispCatRadioOutcome);
            myInformative = (RadioButton) myView.findViewById(R.id.dispCatRadioInformative);

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
                Button b = (Button)myView.findViewById(R.id.dispCatButtonSave);
                b.setVisibility(View.INVISIBLE);

                myName.setText((CharSequence)thisName);
                myName.setFocusable(false);
                myName.setClickable(false);

                myDescription.setText((CharSequence)thisDescription);
                myDescription.setFocusable(false);
                myDescription.setClickable(false);

                if (thisOperation == OPERATION.CREDIT)  myIncome.setChecked(true);
                else if (thisOperation == OPERATION.DEBIT) myOutcome.setChecked(true);
                else if (thisOperation == OPERATION.INFORMATIVE) myInformative.setChecked(true);


            }
        }
        return myView;
    }


    public void Delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.deleteConfirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.delete(myIDtoChange);
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
    public void Save(View view) {
        Bundle extras = getActivity().getIntent().getExtras();
        Integer thisOperation= OPERATION.UNKNOWN;

        RadioButton income = (RadioButton) myView.findViewById(R.id.dispCatRadioIncome);
        RadioButton outcome = (RadioButton) myView.findViewById(R.id.dispCatRadioOutcome);
        RadioButton informative = (RadioButton) myView.findViewById(R.id.dispCatRadioInformative);

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){  //edit Category
                String thisName = myName.getText().toString();
                String thisDesc = myDescription.getText().toString();
                if(income.isChecked()) thisOperation = OPERATION.CREDIT;
                else if(outcome.isChecked()) thisOperation = OPERATION.DEBIT;
                else if(informative.isChecked()) thisOperation = OPERATION.INFORMATIVE;

                if(mydb.update(myIDtoChange,thisName,thisDesc, thisOperation)){
                    Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                    //startActivity(intent);
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
                //Intent intent = new Intent(getActivity().getApplicationContext(),ListCategory.class);
                //startActivity(intent);
            }
        }
    }





}
