package com.cs246.EzBudget;

public class BALANCE_ITEM {
    ////////////////////////////////////////////////////////////////////////
    // MEssage from Salvatore. You should use the class OPERATION for that
    // OPERATION.CREDIT = BALANCE_ITEM.INCOME
    // OPERATION.DEBIT = BALANCE_ITEM.EXPENSE
    // OPERATION.INFORMATIVE =>  used to store the cash flow.which is only informative
    /////////////////////////////////////////////////////////////////////

    // These constants will be used to determine if an item is income or an expense
    // This is needed for the summary view since expenses require a different layout
    
    public static final Integer UNKNOWN = -1;
    public static final Integer EXPENSE = 0;
    public static final Integer INCOME = 1;

}
