package com.cs246.EzBudget;

/**
 * Class to Handle Categories of Financial Data
 *
 */
public class Category {

    /**
     * To be used whe the category is not known
     */
    static public final Long UNKNOWN = Long.valueOf(-1);
    //
    // Fields
    //

    /**
     * FIELDS FOR DATABASE DESCRIPTION
     */
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_ID = "idCat";
    public static final String CATEGORY_COLUMN_NAME = "catName";
    public static final String CATEGORY_COLUMN_DESCRIPTION = "catDescription";
    public static final String CATEGORY_COLUMN_OPERATION = "operation";


    /**
     GENERAL INCOME
     Whatever income it is

     */
    public static final String DB_CAT_GEN_INCOME_NAME = "INCOME";
    public static final String DB_CAT_GEN_INCOME_DESCRIPTION = "Whatever income it is";
    public static final Integer DB_CAT_GEN_INCOME_OPERATION = OPERATION.CREDIT;
    public static final Category DB_CAT_GEN_INCOME = new Category(DB_CAT_GEN_INCOME_NAME,DB_CAT_GEN_INCOME_DESCRIPTION,DB_CAT_GEN_INCOME_OPERATION);


    /**
     GENERAL OUTCOME
     Whatever bill it is

     */
    public static final String DB_CAT_GEN_OUTCOME_NAME = "BILL";
    public static final String DB_CAT_GEN_OUTCOME_DESCRIPTION = "Whatever BILL it is";
    public static final Integer DB_CAT_GEN_OUTCOME_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_GEN_OUTCOME = new Category(DB_CAT_GEN_OUTCOME_NAME,DB_CAT_GEN_OUTCOME_DESCRIPTION,DB_CAT_GEN_OUTCOME_OPERATION);

    //Most common budget categories
    /**
     * HOUSING is generally the largest item in a family budget. Depending on the type and cost of your home,
     * you may be spending a sizable percentage of your income on paying for this living space.
     * It is to your advantage to create a basic budget before selecting your living quarters.
     * By doing this, you can allow your budget numbers to influence your housing decision and
     * decrease the likelihood of committing to a property that continually pushes your budget
     * into the red.
     */
    public static final String DB_CAT_HOUSING_NAME = "HOUSING";
    public static final String DB_CAT_HOUSING_DESCRIPTION = "The sum of the monthly mortgage payment, hazard insurance,property taxes, and homeowner association fees.\n" +
            "\n" +
            "Housing expense is sometimes referred to as PITI, standing for principal, interest, taxes, and insurance.";
    public static final Integer DB_CAT_HOUSING_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_HOUSING = new Category(DB_CAT_HOUSING_NAME,DB_CAT_HOUSING_DESCRIPTION,DB_CAT_HOUSING_OPERATION);

    /**
     FOOD
     You can’t survive without it. Food needs to be very high on your prioritized budget list.

     Groceries
     Restaurants
     Pet Food/Treats

     */
    public static final String DB_CAT_FOOD_NAME = "FOOD";
    public static final String DB_CAT_FOOD_DESCRIPTION = "What do you spend on food";
    public static final Integer DB_CAT_FOOD_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_FOOD = new Category(DB_CAT_FOOD_NAME,DB_CAT_FOOD_DESCRIPTION,DB_CAT_FOOD_OPERATION);

    /**
     TRANSPORTATION
     Transportation is important. But you’re going to need more than gasoline and oil changes . . . .

     Fuel
     Tires
     Oil Changes
     Maintenance
     Parking Fees
     Repairs
     DMV Fees
     Vehicle Replacement – This should be for reasonable vehicle replacements; fancy add-ons should come from your Fun Money category.

     */
    public static final String DB_CAT_TRANSPORTATION_NAME = "TRANSPORTATION";
    public static final String DB_CAT_TRANSPORTATION_DESCRIPTION = "    Fuel\n" +
            "     Tires\n" +
            "     Oil Changes\n" +
            "     Maintenance\n" +
            "     Parking Fees\n" +
            "     Repairs\n" +
            "     DMV Fees\n" +
            "     Vehicle Replacement – This should be for reasonable vehicle replacements; fancy add-ons should come from your Fun Money category.\n";
    public static final Integer DB_CAT_TRANSPORTATION_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_TRANSPORTATION = new Category(DB_CAT_TRANSPORTATION_NAME,DB_CAT_TRANSPORTATION_DESCRIPTION,DB_CAT_TRANSPORTATION_OPERATION);

    /**
     Education
     */
    public static final String DB_CAT_EDUCATION_NAME = "EDUCATION";
    public static final String DB_CAT_EDUCATION_DESCRIPTION = "What do you spend with Education";
    public static final Integer DB_CAT_EDUCATION_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_EDUCATION = new Category(DB_CAT_EDUCATION_NAME,DB_CAT_EDUCATION_DESCRIPTION,DB_CAT_EDUCATION_OPERATION);

    /**
     Utilities

     Electricity
     Water
     Heating
     Garbage
     Phones
     Cable
     Internet

     */
    public static final String DB_CAT_UTILITIES_NAME = "UTILITIES";
    public static final String DB_CAT_UTILITIES_DESCRIPTION = "\n" +
            "    Electricity\n" +
            "    Water\n" +
            "    Heating\n" +
            "    Garbage\n" +
            "    Phones\n" +
            "    Cable\n" +
            "    Internet\n";
    public static final Integer DB_CAT_UTILITIES_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_UTILITIES = new Category(DB_CAT_UTILITIES_NAME,DB_CAT_UTILITIES_DESCRIPTION,DB_CAT_UTILITIES_OPERATION);

    /**
     Clothing

     Wear something. It’s kind of socially important.
     But don’t go overboard here with all the latest trends – that’s for your Fun Money category to manage.

     Children’s Clothing
     Adult’s Clothing

     */
    public static final String DB_CAT_CLOTHING_NAME = "CLOTHING";
    public static final String DB_CAT_CLOTHING_DESCRIPTION = "Children’s Clothing\n" +
            "Adult’s Clothing";
    public static final Integer DB_CAT_CLOTHING_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_CLOTHING = new Category(DB_CAT_CLOTHING_NAME,DB_CAT_CLOTHING_DESCRIPTION,DB_CAT_CLOTHING_OPERATION);

    /**
     Medical

     Even if you are healthy and don’t have many medical expenditures, make sure you consider these categories.

     Primary Care
     Dental Care
     Specialty Care – Think orthodontics, optometrists, etc.
     Medications
     Medical Devices


     */
    public static final String DB_CAT_MEDICAL_NAME = "MEDICAL";
    public static final String DB_CAT_MEDICAL_DESCRIPTION = "Primary Care\n" +
            "     Dental Care\n" +
            "     Specialty Care – Think orthodontics, optometrists, etc.\n" +
            "     Medications\n" +
            "     Medical Devices";
    public static final Integer DB_CAT_MEDICAL_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_MEDICAL = new Category(DB_CAT_MEDICAL_NAME,DB_CAT_MEDICAL_DESCRIPTION,DB_CAT_MEDICAL_OPERATION);

    /**
     *
     Insurance

     The goal of insurance is to pay for expenses you can’t afford but desperately need to cover. Raise your deductibles to save some money if you have a fully funded emergency fund.

     Health Insurance
     Homeowner’s Insurance
     Renter’s Insurance
     Auto Insurance
     Life Insurance
     Disability Insurance
     Identity Theft Protection
     Longterm Care Insurance

     */
    public static final String DB_CAT_INSURANCE_NAME = "INSURANCE";
    public static final String DB_CAT_INSURANCE_DESCRIPTION = "     Health Insurance\n" +
            "     Homeowner’s Insurance\n" +
            "     Renter’s Insurance\n" +
            "     Auto Insurance\n" +
            "     Life Insurance\n" +
            "     Disability Insurance\n" +
            "     Identity Theft Protection\n" +
            "     Longterm Care Insurance";
    public static final Integer DB_CAT_INSURANCE_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_INSURANCE = new Category(DB_CAT_INSURANCE_NAME,DB_CAT_INSURANCE_DESCRIPTION,DB_CAT_INSURANCE_OPERATION);

    /**
     *
     * retirement
     It’s important to have a retirement plan you can depend on. With Social Security wavering, who knows if you’ll be able to depend on the government for assistance. It is often recommended to save and invest for retirement as a high priority in your prioritized budget.

     Financial Planning
     Investing

     */
    public static final String DB_CAT_RETIREMENT_NAME = "RETIREMENT";
    public static final String DB_CAT_RETIREMENT_DESCRIPTION = "     Health Insurance\n" +
            "     Homeowner’s Insurance\n" +
            "     Renter’s Insurance\n" +
            "     Auto Insurance\n" +
            "     Life Insurance\n" +
            "     Disability Insurance\n" +
            "     Identity Theft Protection\n" +
            "     Longterm Care Insurance";
    public static final Integer DB_CAT_RETIREMENT_OPERATION = OPERATION.DEBIT;
    public static final Category DB_CAT_RETIREMENT = new Category(DB_CAT_RETIREMENT_NAME,DB_CAT_RETIREMENT_DESCRIPTION,DB_CAT_RETIREMENT_OPERATION);

    /**
     *
     Salary/Wages
     */
    public static final String DB_CAT_SALARY_NAME = "SALARY";
    public static final String DB_CAT_SALARY_DESCRIPTION = "Salary/Wages";
    public static final Integer DB_CAT_SALARY_OPERATION = OPERATION.CREDIT;
    public static final Category DB_CAT_SALARY = new Category(DB_CAT_SALARY_NAME,DB_CAT_SALARY_DESCRIPTION,DB_CAT_SALARY_OPERATION);

    /**
     *
     Tax Refunds
     */
    public static final String DB_CAT_TAX_REFUNDS_NAME = "TAX_REFUNDS";
    public static final String DB_CAT_TAX_REFUNDS_DESCRIPTION = "Tax Refunds";
    public static final Integer DB_CAT_TAX_REFUNDS_OPERATION = OPERATION.CREDIT;
    public static final Category DB_CAT_TAX_REFUNDS = new Category(DB_CAT_TAX_REFUNDS_NAME,DB_CAT_TAX_REFUNDS_DESCRIPTION,DB_CAT_TAX_REFUNDS_OPERATION);



    /**
     *
     Investment Income (IRA or 401k distributions)
     Interests
     */
    public static final String DB_CAT_INVESTMENTS_NAME = "INVESTMENTS";
    public static final String DB_CAT_INVESTMENTS_DESCRIPTION = "Investment Income (IRA or 401k distributions)\n" +
            "Interests";
    public static final Integer DB_CAT_INVESTMENTS_OPERATION = OPERATION.CREDIT;
    public static final Category DB_CAT_INVESTMENTS = new Category(DB_CAT_INVESTMENTS_NAME,DB_CAT_INVESTMENTS_DESCRIPTION,DB_CAT_INVESTMENTS_OPERATION);

    private String myDescription;  /**

     * 0: Credit
     * 1: Debit
     * 2: informative (nor credit or debit)   */

    private Integer myOperation;
    private String myName;
    private Long myID;

    //
    // Constructors
    //
    public Category () {
        myOperation = OPERATION.UNKNOWN;
    };

    public Category(String theName, String theDesc,Integer theOper){
        this.myName = theName;
        this.myDescription = theDesc;
        this.myOperation = theOper;
    }
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
    public void setID (Long newVar) {
        myID = newVar;
    }

    /**
     * Get the value of myID
     * @return the value of myID
     */
    public Long getID () {
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

