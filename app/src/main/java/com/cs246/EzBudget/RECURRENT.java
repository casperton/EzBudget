package com.cs246.EzBudget;

/**
 * To handle Reccurrent Balance Data
 */
public class RECURRENT {
    //
    // Fields
    //
    public static final Integer UNKNOWN = -1;
    public static final Integer ONCE = 0;
    public static final Integer WEEKLY = 1;
    public static final Integer BI_WEEKLI = 2;
    public static final Integer MONTHLY = 3;

    /**
     * Get a String identifier for the status
     * @param theStatus to get a sring identifier
     * @return The String identifier for the Payment Status
     */
    public static String getStrName(Integer theStatus){
     String myRecStatus="U";
     if(theStatus==UNKNOWN) myRecStatus="U";
     if(theStatus==ONCE) myRecStatus="O";
     if(theStatus==WEEKLY) myRecStatus="W";
     if(theStatus==BI_WEEKLI) myRecStatus="B";
     if(theStatus==MONTHLY) myRecStatus="M";


    return myRecStatus;
    }

    //
    // Constructors
    //
    private RECURRENT() {}
}
