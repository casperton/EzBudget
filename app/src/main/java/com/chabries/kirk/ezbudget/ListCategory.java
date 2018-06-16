package com.chabries.kirk.ezbudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 public class MainActivity extends AppCompatActivity {

 //Database instance
 DBHelper mydb;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_list_category);
 }
 }

 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListCategory extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    private ProgressBar myProgress=null;
    private List<String> listCategoryNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        mydb = new DBHelper(this);
        //ArrayList array_list = mydb.getAllCategories();
        listCategoryNames = mydb.getAllCategoryNames();

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, listCategoryNames);
        myProgress = (ProgressBar) findViewById(R.id.myBar);
        myProgress.setVisibility(View.INVISIBLE);
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DispCategory.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DispCategory.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }


    /**
     * Load database to ListView

     class AssyncLoad extends AsyncTask<Void,Category,Integer>
     {

     ArrayAdapter<String> assyncAdapter;
     Category myCat;
     @Override
     protected void onPreExecute() {
     assyncAdapter = (ArrayAdapter<String>) obj.getAdapter();
     super.onPreExecute();
     myProgress.setMax(100);
     myCat = new Category();
     }

     @Override
     protected Integer doInBackground(Void... voids) {
     String message;


     myProgress.setVisibility(View.VISIBLE);
     myProgress.setProgress(0);
     try {

     FileInputStream fileInputStream = openFileInput("numbers.txt");
     InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
     StringBuffer stringBuffer = new StringBuffer();
     int count =0;

     while ((message=bufferedReader.readLine()) != null){
     count +=1;
     if (count > 100 ) count = 100;
     Thread.sleep(250);
     myCat.setName(message);
     myCat.setOperation(count);
     myCat.setDescription("");
     //will update the progress bar (onProgressUpdate)
     publishProgress(myCat);

     }


     } catch (FileNotFoundException e) {
     e.printStackTrace();
     } catch (IOException e) {
     e.printStackTrace();
     } catch (InterruptedException e) {
     e.printStackTrace();
     }
     return null;
     }

     @Override
     protected void onProgressUpdate(Category... values) {
     super.onProgressUpdate(values);
     myProgress.setProgress(10); //todo   Set Progress
     assyncAdapter.add(values[0].myStringValue);
     }

     @Override
     protected void onPostExecute(Integer integer) {
     super.onPostExecute(integer);
     Toast.makeText(getApplication().getApplicationContext(),"Load Numbers Finished",Toast.LENGTH_LONG).show();
     }
     }

     */
}
