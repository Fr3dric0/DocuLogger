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
    public static final String OUT_FILE_NAME = "js_documentation";
    public static final String OUT_FILE_TYPE = ".json";
    private String inJson = "";
    private boolean fileCreated = false;
    private String writeErrorMessage = "";

    public DocuLogger(String path, String outFolder){

        if(!fileExists(path)){
            throw new NullPointerException("File does not exist!");
        }

        SupportedFiles filetype = FileIdentifier.identify(path);

        // The controller which masters the extraction.
        CommentExtractor extractor = new CommentExtractor(path, filetype);

        ArrayList<Comment> comments = extractor.extract();

        ArrayList<Function> funcList = new ArrayList<>();

        for(Comment com : comments){
            CommentMapper cm = new CommentMapper(com);

            if(cm.validComment()) {
                funcList.add(cm.map());
            }
        }

        this.inJson = gson.toJson(funcList);

        fileCreated = this.createOutFile(this.inJson, String.format("%s%s%s", outFolder, OUT_FILE_NAME, OUT_FILE_TYPE));

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
