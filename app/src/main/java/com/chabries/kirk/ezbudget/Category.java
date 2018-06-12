package com.chabries.kirk.ezbudget;

public class Category {

    //
    // Fields
    //

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
    public Category () { };

    //
    // Methods
    //


    //
    // Accessor methods
    //

    /**
     * Set the value of myDescription
     * @param newVar the new value of myDescription
     */
    private void setDescription (String newVar) {
        myDescription = newVar;
    }

    /**
     * Get the value of myDescription
     * @return the value of myDescription
     */
    private String getDescription () {
        return myDescription;
    }

    /**
     * Set the value of myOperation
     * 0: Credit
     * 1: Debit
     * 2: informative (nor credit or debit)
     * @param newOper the new value of myOperation
     */
    private void setOperation (Integer newOper) {
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
    private void setName (String newName) {
        myName = newName;
    }

    /**
     * Get the value of myName
     * @return the value of myName
     */
    private String getName () {
        return myName;
    }

    /**
     * Set the value of myID
     * @param newVar the new value of myID
     */
    private void setID (int newVar) {
        myID = newVar;
    }

    /**
     * Get the value of myID
     * @return the value of myID
     */
    private int getID () {
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


}

