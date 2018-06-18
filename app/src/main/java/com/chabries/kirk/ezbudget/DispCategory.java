package com.chabries.kirk.ezbudget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DispCategory extends AppCompatActivity {

    private DBHelper mydb ;

    TextView myName ;
    TextView myDescription;
    RadioGroup myOperation;

    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_category);
        myName = (TextView) findViewById(R.id.editTextName);
        myDescription = (TextView) findViewById(R.id.editTextDescription);
        myOperation = (RadioGroup) findViewById(R.id.radioGroupOperation);


        mydb = new DBHelper(this);
        RadioButton myIncome,myOutcome,myInformative;

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            myIncome = (RadioButton) findViewById(R.id.radioIncome);
            myOutcome = (RadioButton) findViewById(R.id.radioOutcome);
            myInformative = (RadioButton) findViewById(R.id.radioInformative);

            if(Value>0){
                //means this is the view/edit part not the add category part.
                Cursor rs = mydb.getCategoryData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                //column values
                String thisName = rs.getString(rs.getColumnIndex(Category.CATEGORY_COLUMN_NAME));
                String thisDescription = rs.getString(rs.getColumnIndex(Category.CATEGORY_COLUMN_DESCRIPTION));
                Integer thisOperation = rs.getInt(rs.getColumnIndex(Category.CATEGORY_COLUMN_OPERATION));


                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.buttonSaveCategory);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_category, menu);
            } else{
                getMenuInflater().inflate(R.menu.list_category, menu);
            }
        }
        return true;
    }

    /**
     *  Action to perform when some menu item is chosen
     * @param item The Selectedmenu item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Category:
                Button b = (Button)findViewById(R.id.buttonSaveCategory);
                b.setVisibility(View.VISIBLE);
                myName.setEnabled(true);
                myName.setFocusableInTouchMode(true);
                myName.setClickable(true);

                myDescription.setEnabled(true);
                myDescription.setFocusableInTouchMode(true);
                myDescription.setClickable(true);

                myOperation.setEnabled(true);
                myOperation.setFocusableInTouchMode(true);
                myOperation.setClickable(true);



                return true;
            case R.id.Delete_Category:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteConfirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteCategory(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                                startActivity(intent);
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

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        Integer thisOperation= OPERATION.UNKNOWN;

        RadioButton myIncome = (RadioButton) findViewById(R.id.radioIncome);
        RadioButton myOutcome = (RadioButton) findViewById(R.id.radioOutcome);
        RadioButton myInformative = (RadioButton) findViewById(R.id.radioInformative);

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){  //edit Category
                String thisName = myName.getText().toString();
                String thisDesc = myDescription.getText().toString();
                if(myIncome.isChecked()) thisOperation = OPERATION.CREDIT;
                else if(myOutcome.isChecked()) thisOperation = OPERATION.DEBIT;
                else if(myInformative.isChecked()) thisOperation = OPERATION.INFORMATIVE;

                if(mydb.updateCategory(id_To_Update,thisName,thisDesc, thisOperation)){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),ListCategory.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{ //add Category
                String thisName = myName.getText().toString();
                String thisDesc = myDescription.getText().toString();
                if(myIncome.isChecked()) thisOperation = OPERATION.CREDIT;
                else if(myOutcome.isChecked()) thisOperation = OPERATION.DEBIT;
                else if(myInformative.isChecked()) thisOperation = OPERATION.INFORMATIVE;
                if(mydb.insertCategory(thisName,thisDesc, thisOperation)){
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

}
