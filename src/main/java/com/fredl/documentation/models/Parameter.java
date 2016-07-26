package com.fredl.documentation.models;

import com.fredl.documentation.data.ValidDocTags;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 *  @created: 22.07.2016
 *  @desc:
 */
public class Parameter {
    private String name;
    private String type;
    private String details;

    private static final String START_PARAM = "[";
    private static final String END_PARAM = "]";

    public Parameter(String name, String type, String details){
        this.name = name;
        this.type = type;
        this.details = details;
    }

    /**
     *  @param:     (String)    comment     Unnmapped comment
     *  @desc:      If the user has an unmapped comment, he can call this static method
     *              which maps the params for him.
     *  @return:    (ArrayList) The mapped params, in an ArrayList
     * */
    public static ArrayList<Parameter> map(String comment){
        ArrayList<Parameter> params = new ArrayList<>();

        identifyParams(comment);

        return params;
    }

    private static HashMap<String, String> identifyParams(String str){
        //param: name,   type
        HashMap<String, String> p = new HashMap<>();
        ArrayList<String> types = findTypes(str);
        ArrayList<String> names = new ArrayList<>();


        return p;
    }

    private static ArrayList<String> findTypes(String str){
        ArrayList<String> types = new ArrayList<>();

        int nextIdx = 0;
        int startParam;
        int endParam;

        // @TODO:ffl implement functionality which identifies if the params is just params inside the description, or a new type
        /*
        *   Since we don't know how many params there is.
        *   We run an infinite loop, until the IndexOutOfBoundsException gets thrown by the String
        * */
        while(true){
            startParam = str.indexOf(START_PARAM, nextIdx)+1;
            endParam = str.indexOf(END_PARAM, nextIdx);

            try{
                types.add(str.substring(startParam, endParam));

                System.out.println(str.substring(startParam, endParam));

                nextIdx = endParam+1;
            }catch(IndexOutOfBoundsException e){
                break;
            }
        }

        return types;
    }

    /**
     *  @param:     [String]    type    The type we should validate
     *              [String]    str     The String the type is contained in.
     *
     *  @desc:      Checks if the type is valid. Be confirming it's position
     *
     * */
    private static String validateType(String type, String str){




        return type;
    }


    private static String cleanComment(String com){
        String str = "";
        String replaceParamTag = String.format("(@%s):?", ValidDocTags.PARAM.getName());


        return str;
    }


    // GETTERS
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getDetails() {
        return details;
    }

    // toString
    @Override
    public String toString(){
        return String.format("(%s)\t%s\t%s", this.type, this.name, this.details);
    }
}
