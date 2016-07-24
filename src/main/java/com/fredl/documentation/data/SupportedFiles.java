package com.fredl.documentation.data;

import java.util.HashMap;

/**
 *  @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 *  @created: 22.07.2016
 *  @desc:
 */
public enum SupportedFiles {
    PYTHON("Python", ".py", "#", "'''", "", "'''"),
    JAVASCRIPT("JavaScript", ".js", "//", "/*", "*", "*/"),
    JAVA("JAVA", ".java", "//", "/*", "*", "*/"),
    PHP("PHP", ".php", "//", "/*", "*", "*/");

    private final String name;
    private final String fileprefix;
    private final String commentSingleline;
    private final String commentMultilineStart;
    private final String commentMultilineEnd;
    private final String commentMultilineMiddle;

    private SupportedFiles(String name, String fileprefix, String commentSingleline, String commentMultilineStart, String commentMultilineMiddle, String commentMultilineEnd){
        this.name = name;
        this.fileprefix = fileprefix;
        this.commentSingleline = commentSingleline;
        this.commentMultilineStart = commentMultilineStart;
        this.commentMultilineMiddle = commentMultilineMiddle;
        this.commentMultilineEnd = commentMultilineEnd;
    }

    public String getFilename(){
        return this.name;
    }
    public String getFileprefix(){
        return this.fileprefix;
    }

    public HashMap<String, String> getMultilineCommentOperator(){
        HashMap<String, String> comMultiline = new HashMap<>();

        comMultiline.put("start", this.commentMultilineStart);
        comMultiline.put("middle", this.commentMultilineMiddle);
        comMultiline.put("end", this.commentMultilineEnd);

        return comMultiline;
    }

    public String getSignleLineCommentOperator(){
        return this.commentSingleline;
    }

    @Override
    public String toString(){
        return this.name + " ("+this.fileprefix+")";
    }
}
