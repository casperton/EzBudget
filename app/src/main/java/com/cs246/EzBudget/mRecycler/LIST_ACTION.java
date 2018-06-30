package com.cs246.EzBudget.mRecycler;

/**
 * The different kind of actions to be performed by the listfragments
 */
public class LIST_ACTION {

    /**
     * LIST_ADD Action List the Data and show button to add new
     * LIST_CHOOSE Action do not show button to add new and return the choice to the caller
     */
    public final static int ACT_LIST_ADD = 1;
    public final static int ACT_LIST_CHOOSE = 2;
}
