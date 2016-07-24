package com.fredl.documentation.controllers.extractor;

import com.fredl.documentation.data.SupportedFiles;
import com.fredl.documentation.models.ExtractedComment;

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

    public ArrayList<ExtractedComment> extract(){
        ArrayList<ExtractedComment> comments = new ArrayList<>();
        Boolean multilineComment = false;
        Boolean singlelineComment = false;

        try(BufferedReader br = new BufferedReader( new FileReader(this.path) )){

            String line;

            while( (line = br.readLine()) != null ){

                String regexMultilineComment = "/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/\n";

                if(!multilineComment){
                    int comIdx = line.indexOf(commentStart);

                    if(comIdx == -1){
                        comIdx = line.indexOf(comment);
                    }else{
                        multilineComment = true;
                    }

                    if(comIdx != -1){
                        singlelineComment = true;
                    }

                }
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return comments;
    }

}
