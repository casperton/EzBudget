package com.cs246.EzBudget;

/**
 * Class operation
 * To indicate the type of operation: Credit, debit, etc
 */
public class OPERATION {


  //
  // Fields
  //
  public static final Integer UNKNOWN = -1;
  public static final Integer CREDIT = 0; //indicates an Income
  public static final Integer DEBIT = 1; //indicates an Expense
  public static final Integer INFORMATIVE = 2; //indicates only informational value. not used to add or subtract.
  
  //
  // Constructors
  //
  private OPERATION() {}
  
  
  

}
