package com.cs246.EzBudget;

import android.view.View;

/**
 * Interface to handle click listener for the RecyclerViews
 */
public interface RecyclerClickListener {

    void OnClick(View view, int position, boolean isLongClick);


}
