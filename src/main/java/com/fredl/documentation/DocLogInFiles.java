package com.fredl.documentation;

import java.util.HashMap;

/**
 * Created by Acer on 22.07.2016.
 */
public enum DocLogInFiles {
    PYTHON("Python", ".py", "#", "'''", "", "'''"),
    JAVASCRIPT("JavaScript", ".js", "//", "/*", "*", "*/"),
    JAVA("JAVA", ".java", "//", "/*", "*", "*/"),
    PHP("PHP", ".php", "//", "/*", "*", "*/");

    private final String name;
    private final String filename;
    private final String commentSingleline;
    private final String commentMultilineStart;
    private final String commentMultilineEnd;
    private final String commentMultilineMiddle;

    DocLogInFiles(String name, String filename, String commentSingleline, String commentMultilineStart, String commentMultilineMiddle, String commentMultilineEnd){
        this.name = name;
        this.filename = filename;
        this.commentSingleline = commentSingleline;
        this.commentMultilineStart = commentMultilineStart;
        this.commentMultilineMiddle = commentMultilineMiddle;
        this.commentMultilineEnd = commentMultilineEnd;
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
        return this.name + " ("+this.filename+")";
    }
}
