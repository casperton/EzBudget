package com.cs246.EzBudget.SummaryView;

import android.support.v7.widget.RecyclerView;

/**
 * Listener used by the RecyclerItemTouchHelper class for swipeable items
 * Taken from the demo:
 * https://www.androidhive.info/2017/09/android-recyclerview-swipe-delete-undo-using-itemtouchhelper/
 */
public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
