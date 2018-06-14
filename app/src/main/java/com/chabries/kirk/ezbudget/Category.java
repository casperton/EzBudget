package com.chabries.kirk.ezbudget;

public class Category {

    //
    // Fields
    //

    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_NAME = "name";
    public static final String CATEGORY_COLUMN_DESCRIPTION = "description";
    public static final String CATEGORY_COLUMN_OPERATION = "operation";


    private String myDescription;  /**

     * 0: Credit
     * 1: Debit
     * 2: informative (nor credit or debit)   */

    private Integer myOperation;
    private String myName;
    private int myID;

    //
    // Constructors
    //
    public Category () {
        myOperation = OPERATION.UNKNOWN;
    };

     /**
     * Set the value of myDescription
     * @param newVar the new value of myDescription
     */
    public void setDescription (String newVar) {
        myDescription = newVar;
    }

    /**
     * Get the value of myDescription
     * @return the value of myDescription
     */
    public String getDescription () {
        return myDescription;
    }

    /**
     * Set the value of myOperation
     * 0: Credit
     * 1: Debit
     * 2: informative (nor credit or debit)
     * @param newOper the new value of myOperation
     */
    public void setOperation (Integer newOper) {
        myOperation = newOper;
    }

    /**
     * Get the value of myOperation
     * 0: Credit
     * 1: Debit
     * 2: informative (nor credit or debit)
     * @return the value of myOperation
     */
    public Integer getOperation () {
        return myOperation;
    }

    /**
     * Set the value of myName
     * @param newName the new value of myName
     */
   public void setName (String newName) {
        myName = newName;
    }

    /**
     * Get the value of myName
     * @return the value of myName
     */
    public String getName () {
        return myName;
    }

    /**
     * Set the value of myID
     * @param newVar the new value of myID
     */
    public void setID (int newVar) {
        myID = newVar;
    }

    /**
     * Get the value of myID
     * @return the value of myID
     */
    public int getID () {
        return myID;
    }

    //
    // Other methods
    //

    /**
     */
    public boolean isCredit()
    {
        return myOperation == OPERATION.CREDIT;

    }


    /**
     */
    public boolean isDebit()
    {
        return myOperation == OPERATION.DEBIT;
    }


    /**
     * return true if it isan informative category ( not credit or debit)
     * @return       boolean
     */
    public boolean isInformative()
    {
        return myOperation == OPERATION.INFORMATIVE;
    }

    public void setMyCategory(BalanceData _balancedata) {}

    public BalanceData getMyCategory() { return null; }
}

