package com.chabries.kirk.ezbudget;

/**
 * Just to indicate the status of the income/outcome
 * it it is income indicate if it is already received or not
 * it it is a bill indicate if it is already paid or not
 */
public class PAY_STATUS {

    //
    // Fields
    //
    public static final Integer UNKNOWN = -1;
    public static final Integer PAID_RECEIVED = 0;
    public static final Integer UNPAID_UNRECEIVED = 1;

    //
    // Constructors
    //
    private PAY_STATUS() {}

}
