package com.fredl.documentation;
import com.fredl.documentation.controllers.FileIdentifier;
import com.fredl.documentation.controllers.extractor.CommentExtractor;
import com.fredl.documentation.controllers.mapper.CommentMapper;
import com.fredl.documentation.data.SupportedFiles;
import com.fredl.documentation.models.Comment;
import com.fredl.documentation.models.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); // Enable pretty formatting
    private File inFile;
    private static final String OUT_FILE_NAME = "js_documentation";
    private static final String OUT_FILE_TYPE = ".json";
    private String inJson = "";
    private boolean fileCreated = false;
    private String writeErrorMessage = "";

    public DocuLogger(String path){

        if(!fileExists(path)){
            throw new NullPointerException("File does not exist!");
        }

        SupportedFiles filetype = FileIdentifier.identify(path);

        // Extract the comments from the file
        CommentExtractor extractor = new CommentExtractor(path, filetype);
        ArrayList<Comment> comments = extractor.extract();

        // List of all the mapped comments
        ArrayList<Function> funcList = new ArrayList<>();

        // Map comments, and place in fundList
        for(Comment com : comments){
            CommentMapper cm = new CommentMapper(com);

            if(cm.validComment()) {
                funcList.add(cm.map());
            }
        }

        // Convert comment and documentation to JSON
        this.inJson = gson.toJson(funcList);

    }

    public boolean makeFile(String dir){
        return makeFile(dir, OUT_FILE_NAME);
    }

    /**
     *  @param:     [String]    dir         The directory to where the file should go
     *              [String]    filename    Filename for the json file
     *  @desc:      Checks that the arguments are valid. Then calls the createOutMethod
     *  @return:    [Boolean]   True if the file vas created successfully. If not, false
     * */
    public boolean makeFile(String dir, String filename){
        if(filename.length() < 1){
            throw new IllegalArgumentException("Empty filename not supported!");
        }

        // If the slash sign isn't at the end of the directory. Just add it
        if(!dir.substring(dir.length()-1, dir.length()).equals("/")){
            dir = dir + "/";
        }

        // Check if the .json fileending is included in the filename
        if(filename.substring(filename.length()-OUT_FILE_TYPE.length(), filename.length()).equals(OUT_FILE_TYPE)){
            filename = filename.substring(0, filename.length()-OUT_FILE_TYPE.length());
        }

        return this.createOutFile(this.inJson, String.format("%s%s%s", dir, filename, OUT_FILE_TYPE));
    }

    public String getJson(){
        return this.inJson;
    }
    public String getFileWriteError(){
        return writeErrorMessage;
    }
    public boolean outFileCreated(){
        return fileCreated;
    }


    private boolean createOutFile(String json, String filename){
        /*
        *   The Filewrite system, writes into the file line by line.
        *   We split the json-string into each line. to preserve formatting
        * */
        List<String> lines = Arrays.asList(json.split("\n"));

        Path file = Paths.get(filename);

        try{
            Files.write(file, lines, Charset.forName("UTF-8"));
        }catch(IOException ioe){
            this.writeErrorMessage = ioe.getMessage();
            return false;
        }

        return true;
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
