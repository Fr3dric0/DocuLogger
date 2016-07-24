package com.fredl.documentation.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 23.07.2016
 * @desc:
 */
public class FunctionModelTest {

    @Test
    public void settingAndGettingReturnDetails() throws Exception {
        // Arrange
        String obj = "Object";
        String det = "If name is found, return the user details. If not, return null";
        Function function = new Function("getNameFromDB()", "Searches the database for the correct name");
        function.setReturnDetails(obj, det);

        // Test what happens when an incomplete value is provided to the return details
        Function func2 = new Function("getNameFromDB()", "Searches the database for the correct name");
        func2.setReturnDetails("", "");


        // Act
        Returns r = function.getReturnDetails();
        Returns r2 = func2.getReturnDetails(); // Get the incomplete value


        // Assert
        assertNotNull(r);
        if(!r.getType().equals(obj)){
            fail("The Returns value 'type' did not return the value provided in");
        }
        if(!r.getDetails().equals(det)){
            fail("The Returns value 'details' did not return the value provided in");
        }

        // Assert the incomplete version
        assertNotNull(r2);
        if(!r2.getType().equals("<UNDEFINED>") ){
            fail("Expected Returns value 'type' to return '<UNDEFINED>' when argument was empty. Instead got: "+r2.getType());
        }

        if(!r2.getDetails().equals("")){
            fail("Expected Returns value 'details' to be empty, when argument was empty. Instead got: "+r2.getDetails());
        }

    }

    @Test
    public void addingParameters() throws Exception{
        Function testFunc1 = new Function("testingFunc()", "");

        String testName = "nmn";
        String testType = "String";
        String testDet = "...";

        testFunc1.addParam(testName, testType, testDet);

        Parameter param = testFunc1.getParameters().get(0);

        if(!param.getName().equals(testName) || !param.getType().equals(testType) || !param.getDetails().equals(testDet)){
            fail("Expected the values provided to the param, to be returned identically! Instead got: "+param);
        }
    }



}