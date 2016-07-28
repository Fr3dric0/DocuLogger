package com.fredl.documentation.models;

/**
 * @author:     Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created:    24.07.2016
 * @desc:       Comment class is used to store basic information about the documentation, before it is mapped.
 *              This class handles the basic trimming of both the function-name and the actual comment.
 */
public class Comment {
    private String comment;
    private String functionName;


    public Comment(String comment, String functionName){
        this.functionName = trimFunction(functionName);
        this.comment = trimComment(comment);
    }

    /**
     *  @param:     (String)    func    The function's name
     *  @desc:      Removes all the whitespace-chars.
     *              Then checks if the first word, is either 'function' or 'class',
     *                  * If so. Then remove the word
     *  @return:    (String)    A trimmed down name
     * */
    private String trimFunction(String func){
        String f = func.replaceAll(" ", "");
        StringBuilder trimmed = new StringBuilder(f);
        String prefix = "function";
        String classPrefix = "class";

        // @TODO:ffl - Find a better way to trim the 'function' prefix from the name
        if(f.indexOf(prefix) == 0){
            trimmed.delete(0, prefix.length());
        }else if(f.indexOf(classPrefix) == 0){
            trimmed.delete(0, classPrefix.length());
        }

        // Saves everything from the function-name, to the '(' sign
        String name = trimmed.toString();
        name = name.substring(0, name.indexOf("("));

        return name;
    }


    /**
     *  @param:     (String)    com     The raw comment
     *  @desc:      Takes the raw comment, removes all the whitespace before the '*' and '/*' chars.
     *              Thereby looking much slicker
     *  @return:    (String)    Trimmed down comment
     * */
    private String trimComment(String com){

        String trimmed = "";

        String[] lines = com.split("\n");

        int idx = 0;
        for(String l : lines){
            int startPos = 0;

            /*
            *   We know each multiline comment should be formatted with
            *   a '*' char for each new line. We use this information to find the first char in each line,
            *   and remove everything before this.
            *
            * */
            if(l.contains("/*")){

                startPos = l.indexOf("/*");

            }else if(l.contains("*")){
                startPos = l.indexOf("*");
            }

            trimmed += l.substring(startPos, l.length());

            // !IMPORTANT! - The other classes splits the comment string into lines.
            //               Therefore do we have to keep splitting the comment into lines here.
            if(idx != lines.length-1){
                trimmed += "\n";
            }
        }

        return trimmed;
    }



    @Override
    public String toString(){
        return "/**...*/ "+this.functionName;
    }
    public String getComment() {
        return comment;
    }
    public String getFunctionName() {
        return functionName;
    }
}
