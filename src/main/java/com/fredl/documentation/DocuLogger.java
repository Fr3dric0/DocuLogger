package com.fredl.documentation;
import com.fredl.documentation.controllers.FileIdentifier;
import com.fredl.documentation.controllers.extractor.CommentExtractor;
import com.fredl.documentation.controllers.mapper.CommentMapper;
import com.fredl.documentation.data.SupportedFiles;
import com.fredl.documentation.models.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;

/**
 * @desc:   DocuLogger.io handles the process of automatically documenting your functions and classes, when programming.
 *
 *              1. When the object is created it will first checks if the file exists. If not, the class will
 *                 throw an NullPointerException
 *              2. When the file is identified and loaded. The program will try to identify
 *
 * @guide:  The way you go forth on using this object is by follow the following steps
 *              1. When the object is instantiated, you give it the path to the program you would like to document
 */
public class DocuLogger {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Enable pretty formatting
    private File inFile;

    public DocuLogger(String path){
        if(fileExists(path)){
            //System.out.println("Hi and welcome to DocuLogger!");
        }else{
            throw new NullPointerException("File does not exist!");
        }

        SupportedFiles filetype = FileIdentifier.identify(path);

        // The controller which masters the extraction.
        CommentExtractor extractor = new CommentExtractor(path, filetype);

        ArrayList<Comment> comments = extractor.extract();

        CommentMapper cm = new CommentMapper(comments.get(0));
        cm.map();

    }


    /**
     *  @param: (String)    path    Path to the file
     *  @desc:  Creates a test file, with the path provided as referance.
     *          Lastly it checks if the file exists.
     *  @return: (Boolean)  true if file exists, false if not.
     * */
    private boolean fileExists(String path){
        File testFile = new File(path);
        return testFile.exists();
    }

}
