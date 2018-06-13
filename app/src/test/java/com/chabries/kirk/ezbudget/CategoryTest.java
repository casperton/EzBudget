package com.chabries.kirk.ezbudget;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Test Class for the Category.
 * Week 08 Team Activity
 * Created by Salvatore
 */
public class CategoryTest {

    @Test
    public void getOperation() throws Exception{
        Integer output;
        Integer expected = OPERATION.UNKNOWN;

        Category thisCat = new Category();
        output = thisCat.getOperation();
        assertEquals(expected,output);
    }

    @Test
    public void isCredit() throws Exception {
        boolean output;
        boolean expected = false;
        Category thisCat = new Category();
        output = thisCat.isCredit();
        assertEquals(expected,output);
    }

    @Test
    public void isDebit() throws Exception {
        boolean output;
        boolean expected = false;

        Category thisCat = new Category();
        output = thisCat.isDebit();
        assertEquals(expected,output);
    }

    @Test
    public void isInformative() throws Exception {
        boolean output;
        boolean expected = false;
        Category thisCat = new Category();
        output = thisCat.isInformative();
        assertEquals(expected,output);
    }
}