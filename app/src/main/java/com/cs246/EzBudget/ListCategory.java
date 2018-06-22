package com.cs246.EzBudget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolder;

import java.util.List;

/**
 * public class MainActivity extends AppCompatActivity {
 * <p>
 * //Database instance
 * DBHelper mydb;
 *
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * setContentView(R.layout.activity_list_category);
 * }
 * }
 */

public class ListCategory extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;

    DBHelper mydb;
    private ProgressBar myProgress=null;
    private List<String> listCategoryNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        myRecyclerView = (RecyclerView) findViewById(R.id.ReciclerViewCategory);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) findViewById(R.id.myBar);
        myProgress.setVisibility(View.INVISIBLE);
        mydb = new DBHelper(this);
        new BackGroundCategory(myRecyclerView,myProgress,this,BackGroundCategory.CAT_ALL, RecyclerViewHolder.LAYOUT_ONE,null).execute();


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

}
