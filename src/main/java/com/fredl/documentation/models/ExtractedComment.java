package com.fredl.documentation.models;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 24.07.2016
 * @desc:
 */
public class ExtractedComment {
    private String comment;
    private String functionName;


    public ExtractedComment(String comment, String functionName){
        this.comment = comment;
        this.functionName = functionName;
    }

    public String getComment() {
        return comment;
    }

    public String getFunctionName() {
        return functionName;
    }
}
