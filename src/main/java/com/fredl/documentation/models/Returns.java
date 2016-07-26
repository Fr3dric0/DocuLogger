package com.fredl.documentation.models;

import com.fredl.documentation.data.ValidDocTags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 *  @created: 23.07.2016
 *  @desc:
 */
public class Returns {
    private String type;
    private String details;

    public Returns(String type, String details){
        if(type.equals("") || type == null){
            this.type = "<UNDEFINED>";
        }else {
            this.type = type;
        }
        this.details = details;
    }

    /**
     *  @desc:  Returns constructor for when the comment hasn't been mapped yet
     * */
    public Returns(String com){
        this.type = null;
        this.details = null;
        this.mapReturns(com);
    }

    private void mapReturns(String com){
        this.type = findType(com);
        this.details = cleanDescription(com);
    }

    /**
     *  @param:     (String)    str     The
     *  @desc:      Cleanes the comment by removing unnecessary chars, like whitespace before each sentence.
     *              It also combines multiline description, to one signlelined
     *
     *  @return:    (String)    The cleaned up description
     * */
    private String cleanDescription(String str){
        String replaceReturnTag = String.format("(@%s):?", ValidDocTags.RETURN.getName()); // Identifies @return or @return:
        String replaceTypeTag = "(" + this.type + ")";

        String buffer = str.replace(replaceTypeTag, "");
        buffer = buffer.replaceAll(replaceReturnTag, "");
        String[] lines = buffer.split("\n");

        String cleaned = "";

        int idx = 0;
        for(String l : lines) {
            l = l.replaceFirst("\\*", "");
            /*
            *   We want to remove evt. whitespace- or tab-chars, before each sentence starts.
            *   Therefore we do a regex-search to find the first letter/number in each line
            * */
            Pattern r = Pattern.compile("\\S");
            Matcher m = r.matcher(l);

            if (m.find()) {
                if (!m.group(0).equals("") || !m.group(0).isEmpty()) {
                    cleaned += l.substring(l.indexOf(m.group(0)));

                    if(idx < lines.length-1){
                        cleaned += "\n";
                    }
                }
            }
        }

        // IF the description is empty. Then warn the user to ensure he notices
        if(cleaned.equals("") || cleaned.isEmpty()){
            cleaned = "<NO DESCRIPTION>";
        }

        return cleaned;
    }


    /**
     *  @param:     (String)    str     The comment where we can expect to find the type
     *  @desc:      Uses basic-basic regex, by finding the index of the first '(' and ')'.
     *              Datatypes must always be enclosed params
     *  @return:    (String)    The datatype matching the description
     * */
    private String findType(String str){
        return str.substring(str.indexOf("(")+1, str.indexOf(")"));
    }


    // GETTERS
    public String getDetails(){
        return this.details;
    }
    public String getType(){
        return this.type;
    }

    // toString
    @Override
    public String toString(){
        return String.format("(%s) %s", this.type, this.details);
    }
}
