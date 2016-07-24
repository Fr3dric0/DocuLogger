package com.fredl.documentation.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 23.07.2016
 * @desc:
 */
public class FunctionGsonParsingTest {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Use Gson with pretty format

    @Test
    public void convertFunctionToGson() throws Exception{
        String results = "{\n" +
                "  \"function\": \"testFunc(nmn, db)\",\n" +
                "  \"parameters\": [\n" +
                "    {\n" +
                "      \"name\": \"mnm\",\n" +
                "      \"type\": \"String\",\n" +
                "      \"details\": \"The name of the person\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"db\",\n" +
                "      \"type\": \"Database\",\n" +
                "      \"details\": \"The database to search in\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"description\": \"test test test test\",\n" +
                "  \"returns\": {\n" +
                "    \"type\": \"Object\",\n" +
                "    \"details\": \"Returns details about the person with the provided name\"\n" +
                "  }\n" +
                "}";

        Function testFunc = new Function("testFunc(nmn, db)", "test test test test");
        testFunc.setReturnDetails("Object", "Returns details about the person with the provided name");
        testFunc.addParam("mnm", "String", "The name of the person");
        testFunc.addParam("db", "Database", "The database to search in");

        // Converting the class to json
        String json = gson.toJson(testFunc);

        // Test if the value is identical to the expected json
        if(!json.equals(results)){
            fail("When converting testFunc to json, something went wrong!");
        }
    }


}