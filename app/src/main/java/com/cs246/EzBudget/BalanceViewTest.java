package com.cs246.EzBudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cs246.EzBudget.Database.DBHelper;
import com.cs246.EzBudget.mBackGrounds.BackGroundBalance;
import com.cs246.EzBudget.mRecycler.RecyclerViewHolder;

public class BalanceViewTest extends AppCompatActivity {

    private static final String TAG = BalanceViewTest.class.getName();

    private RecyclerView myRecyclerView;
    RecyclerView.LayoutManager myLayoutManager;

    Button myIncomeBtn;
    Button myBillsBtn;
    DBHelper mydb;
    private ProgressBar myProgress=null;
    private BackGroundBalance myBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(MainActivity.DEBUG) Log.i(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_view_test);

        myBillsBtn = (Button) findViewById(R.id.buttonBVGetBills);
        myIncomeBtn = (Button) findViewById(R.id.buttonBVGetIncomes);
        myRecyclerView = (RecyclerView) findViewById(R.id.ReciclerViewBVTest);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        myProgress = (ProgressBar) findViewById(R.id.progressBarBVTest);
        myProgress.setVisibility(View.INVISIBLE);
        mydb = DBHelper.getInstance(this);
        myBackground = new BackGroundBalance(myRecyclerView,myProgress,this,BackGroundBalance.BAL_ALL, RecyclerViewHolder.LAYOUT_ONE);
        myBackground.execute();

        myIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackGroundBalance(myRecyclerView,myProgress,view.getContext(),BackGroundBalance.BAL_INCOMES, RecyclerViewHolder.LAYOUT_ONE).execute();

            }
        });

        myBillsBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        new BackGroundBalance(myRecyclerView,myProgress,view.getContext(),BackGroundBalance.BAL_BILLS, RecyclerViewHolder.LAYOUT_ONE).execute();

        }
    });

    }
}
