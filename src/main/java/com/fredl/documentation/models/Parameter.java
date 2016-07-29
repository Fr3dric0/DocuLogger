package com.fredl.documentation.models;

import com.fredl.documentation.data.ValidDocTags;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String CANCEL_SIGN = "\\";

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
        ArrayList<Parameter> params = identifyParams(comment);

        return params;
    }

    /**
     *  @param:     [String]    str     Text which contains the unmapped params
     *  @desc:      Goes systematically through the unmapped param-string.
     *                  1. Find the datatypes: Because of its distinct format '[String]', the datatype is the easiest to find
     *                  2. Find param-names: We know that the param-names is the first thing to come after the datatypes.
     *                     We will therefore use the values in pt 1, to identify the names
     *                  3. The descriptions is the most difficult data to identify, which is the reason we handle this last.
     *              When all the data is collected, we stitch them together into a Parameter-object, and returns the List to the user.
     *  @return:    [ArrayList<Parameter>]  The list of all the identified params
     * */
    private static ArrayList<Parameter> identifyParams(String str){
        ArrayList<Parameter> p = new ArrayList<>();
        ArrayList<String> types = findTypes(str);
        ArrayList<String> names = identifyNames(types, str);
        ArrayList<String> description = findDescriptions(names, types, str);

        for(int i = 0; i < types.size()-1; i++){
            try {
                p.add(new Parameter(names.get(i), types.get(i), description.get(i)));
            }catch(IndexOutOfBoundsException e){
                // Do nothing
            }
        }

        return p;
    }

    /**
     *  @param:     [ArrayList<String>]     names       List of all the identified names, ordered correctly
     *              [ArrayList<String>]     types       List of all the identified types, ordered correctly
     *              [String]                str         The unmapped commentary String
     *  @desc:      The way we identify which descriptions belongs to which param, is to start right after the param-name, then continue to we
     *              encounter the type declaration for the next param.
     *  @return:    [ArrayList<String>]     Returns the cleaned up description, ordered correctly
     * */
    private static ArrayList<String> findDescriptions(ArrayList<String> names, ArrayList<String> types, String str){
        ArrayList<String> desc = new ArrayList<>();

        int idx = 0;
        String buffer = "";

        String[] lines = str.split("\n");
        for(String line : str.split("\n")){
            line = line.replaceFirst("\\*", ""); // Remove the dots in before the comment

            buffer += line;

            if(idx < lines.length-1){
                buffer += "\n";
            }
            idx++;
        }

        String d = "";
        idx = 0;
        for(String name : names){
            int startSearch = buffer.indexOf(name)+name.length()+1; // Starts the search right after each param-names

            // Remove the useless part of the string
            buffer = buffer.substring(startSearch);

            Pattern r = Pattern.compile("\\S");
            Matcher m = r.matcher(buffer);

            if(m.find()){
                String firstChar = m.group(0); // Find the first letter in the description

                try {
                    /*
                    *   As long as the indexnumber is less than the size of the List, we should stop collecting chars when the next
                    *   type declaration pops up.
                    *   If we know the index is the same size as the List. Then the description will stop, when the string stops.
                    * */
                    if(idx < names.size()-1) {
                        String endString = START_PARAM + types.get(idx + 1) + END_PARAM;
                        d = buffer.substring(buffer.indexOf(firstChar), buffer.indexOf(endString));
                    }else{
                        d = buffer.substring(buffer.indexOf(firstChar));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            desc.add(cleanDesc(d));

            idx++;
        }

        return desc;
    }


    private static String cleanDesc(String desc){
        String cleaned = "";

        int idx = 0;
        for(String l : desc.split("\n")){
            // @TODO:ffl - Identify if the next line has included tabbing. i.e.
            /* Lorem lipsum dolor sit amed
            *       # Test test test: @TODO:ffl - We should identify the tab here
            *           Compro dos celeb @TODO:ffl - And the double tab here
            * */
            Pattern r = Pattern.compile("\\S");
            Matcher m = r.matcher(l);

            if(m.find()){
                int start = l.indexOf(m.group(0));
                cleaned += l.substring(start);

                /*
                if(idx < l.length()-1){
                    cleaned += "\n";
                }*/
            }

            idx++;
        }
        return cleaned;
    }

    /**
     *  @param:     [ArrayList<String>]     types   The identified datatypes for the parameters
     *              [String]                str     The commentary string
     *  @desc:      Uses the information from the datatypes, to identify the connecting names
     *
     * */
    private static ArrayList<String> identifyNames(ArrayList<String> types, String str){
        ArrayList<String> names = new ArrayList<>();

        int searchFrom;
        String buffer = str;

        for(String t : types){
            searchFrom = buffer.indexOf(t)+t.length()+1; // Start after the type declaration

            buffer = buffer.substring(searchFrom);
            Pattern r = Pattern.compile("\\S"); // Find the first char after the type declaration
            Matcher m = r.matcher(buffer);

            if(m.find()){
                String name = findName(buffer.substring( buffer.indexOf(m.group(0)) ));

                names.add(name); // Place the value in a HashMap
            }

        }

        return names;
    }

    private static String findName(String str){
        int end = str.indexOf(" "); // First the first occurrence of whitespace

        return str.substring(0, end);
    }



    /**
     *  @param:     [String]    str     The string where the params lay
     *  @desc:      Identifies the '[' and ']' signs, while excluding '\[' and '\]' in the commentaries
     *              The way we find these, is by using indexOf() for '[' and ']'. Then we keen an counter (nextIdx), for were to start the indexOf search.
     *
     *              All this is done in an infinate loop, which stops when an IndexOutOfBoundsException gets thrown.
     *  @return:    [ArrayList<String>] A list of all the valid types found in the commentary
     * */
    private static ArrayList<String> findTypes(String str){
        ArrayList<String> types = new ArrayList<>();

        int nextIdx = 0;
        int startParam;
        int endParam;

        /*
        *   Since we don't know how many params there is.
        *   We run an infinite loop, until the IndexOutOfBoundsException gets thrown by the String
        * */
        while(true){
            startParam = str.indexOf(START_PARAM, nextIdx);
            endParam = str.indexOf(END_PARAM, nextIdx);

            // Check if we should skip this param
            if( ignoreType(str, nextIdx) ){
                nextIdx = endParam+1;
                continue;
            }

            try{

                String type = str.substring(startParam+1, endParam);

                types.add(type);

                nextIdx = endParam+1;

            }catch(IndexOutOfBoundsException e){
                break;
            }
        }

        return types;
    }

    /**
     *  @param:     [String]    str     The comment string
     *              [int]       next    Index we should skip over
     *  @desc:      Checks if the chars '[' and ']' don't include the cancel sign '\', like this \[ and \]
     *              If so, we should ignore it.
     *  @return:    [Boolean]   True if '[' and ']' include the '\'. If not, false
     * */
    private static boolean ignoreType(String str, int next){
        int start = str.indexOf(START_PARAM, next);
        int end = str.indexOf(END_PARAM, next);

        return (start-1 == str.indexOf(CANCEL_SIGN+START_PARAM, next) && (end-1 == str.indexOf(CANCEL_SIGN+END_PARAM, next)) );
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
