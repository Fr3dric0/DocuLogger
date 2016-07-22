package com.fredl.documentation.models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 *  @author:    Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 *  @created:   23.07.2016
 *  @desc:      This is the master gson-model class. It defines how the output should look and function.
 *
 *  @structure: The following is an example of the structure for a json documentation object for JavaScript
 *
 *          {
 *              function: "getNameFromDB(name, scheema)",
 *              parameters: [
 *                  {
 *                      name: "name",
 *                      type: String,
 *                      details: "Is the name we want to search for in the database"
 *                  },
 *                  {
 *                      name: "scheema",
 *                      type: Object,
 *                      details: "The scheema we want to search for the name in"
 *                  }
 *              ],
 *              description: "The function takes two arguments name and scheema. The function will then search in the database
 *                            for occurenses of the provided name",
 *              returns: {
 *                  type: Object,
 *                  details: "If the name is found, then all the details about him is returned in an object. If not found,
 *                            return null"
 *              }
 *          }
 *
 */
public class Function {

    private String function;
    private ArrayList<Parameter> parameters = new ArrayList<>();
    private String description;
    private Returns returns;

    public Function(String function, String description){
        this.function = function;
        this.description = description;


    }


    public Returns getReturnDetails(){
        return this.returns;
    }
    public String getDescription(){
        return this.description;
    }
    public ArrayList<Parameter> getParameters(){
        return this.parameters;
    }
    public String getFunctionName(){
        return this.function;
    }

    /**
     *  @param:     (String)    type    What type the return value should return
     *              (String)    details Details about what the function will return.
     *                                  Thereby the user knows what he can expect from the function
     *  @desc:      Instantiates the property returns with the provided values.
     *              If type if empty, the method sets a warning label, so the user notices.
     *
     *  @return:    (Boolean)   True if everything went to plan. If not, return false
     * */
    public boolean setReturnDetails(String type, String details){
        if(type.equals("") || type == null){
            type = "<UNDEFINED>";
        }

        try {
            returns = new Returns(type, details);
        }catch(Exception e){
            return false;
        }

        return true;
    }

    /**
     *  @param:     (String)    name    The name of the parameter (much like this one)
     *              (String)    type    The datatype the object should be
     *              (String)    details An explanation of why the param is needed
     *  @desc:      Takes the three params, checks if name and type is empty.
     *                  IF empty, then put a warning label in each of them.
     *
     *              Details-param is optional.
     *
     *              The method adds this information to the ArrayList parameters, through the Parameter-object
     *  @return:    (boolean)   Returns true if everything went to plan. If not, return false
     * */
    public boolean addParam(String name, String type, String details){

        // If name is empty, set a warning so the user notices the error
        if(name.equals("") || name == null){
            name = "<NO NAME>";
        }

        // If type is empty, set a warning label so the user notices.
        if(type.equals("") || type == null){
            type = "<UNDEFINED>";
        }

        try {
            this.parameters.add(new Parameter(name, type, details));
        }catch(Exception e){ // If something goes wrong, return false
            return false;
        }

        return true;
    }
}
