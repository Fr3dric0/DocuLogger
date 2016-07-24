package com.fredl.documentation.controllers.extractor;

import com.fredl.documentation.data.SupportedFiles;
import com.fredl.documentation.models.Comment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 24.07.2016
 * @desc:
 */
public class JavaScriptExtractor {
    private File file;
    private String path;
    private SupportedFiles js = SupportedFiles.JAVASCRIPT;
    private String commentStart = "/*";
    private String commentEnd = "*/";
    private String comment = "//";

    public JavaScriptExtractor(String path){
        this.file = new File(path);
        this.path = path;
    }

    public ArrayList<Comment> extract(){
        ArrayList<Comment> comments = new ArrayList<>();

        // Boolean states for when the iteration should capture data
        Boolean captureComment = false;
        Boolean captureFunctionName = false;

        // Boolean states for when the iteration has CAPTURED data
        Boolean commentCaptured = false;
        Boolean functionCaptured = false;

        //  If data-capture could not finish.
        //      i.e. if no function name was found
        //  Abort the current capturing, and reset data
        Boolean abortCapture = false;

        // Datastores
        String com = "";
        String functionName = "";

        //Read file
        try(BufferedReader br = new BufferedReader( new FileReader(this.path) )){

            String line;
            while( (line = br.readLine()) != null ){

                // If current line contains the start-comment sign,
                // begin capturing comments
                if(line.contains("/*")){
                    captureComment = true;
                }

                // STATE: Capture comment
                if(captureComment){
                    com += line; // Store current line in file

                    /*
                    *   When the comment has ended.
                    *   change the state from capture comment, to capture the function name
                    * */
                    if(line.contains("*/")){
                        commentCaptured = true;
                        captureComment = false;

                        captureFunctionName = true;
                    }else{
                        com += "\n"; // IF the comment hasn't ended, include a new line
                    }
                }

                // STATE: Capture function name
                if(captureFunctionName){

                    if(line.contains("function")){

                        functionName = line;

                        // Set state to function captured
                        functionCaptured = true;
                        captureFunctionName = false;
                    }

                    /*
                    *   IF the line contains a new multiline-comment, the comment might be unrelated to
                    *   a function, or we have missed the definition.
                    *
                    *   Either way, ABORT CAPTURE.
                    * */
                    if(line.contains("/*")){
                        abortCapture = true;
                    }
                }

                // STATE: Abort OR Store comment and function-name
                if( (commentCaptured && functionCaptured) || abortCapture){

                    // Store the comment
                    if(commentCaptured && functionCaptured) {
                        comments.add(new Comment(com, functionName));
                    }

                    // Reset the values
                    com = "";
                    functionName = "";

                    // Reset all states
                    captureComment = false;
                    captureFunctionName = false;
                    abortCapture = false;
                    commentCaptured = false;
                    functionCaptured = false;
                }

            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return comments;
    }



}
