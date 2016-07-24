package com.fredl.documentation.controllers.extractor;

import com.fredl.documentation.data.SupportedFiles;
import com.fredl.documentation.models.Comment;

import java.util.ArrayList;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 23.07.2016
 * @desc:   Handles the operation of extracting the comments from the file, with the surrounding functions.
 *          The actual parsing of this data is done later by an external class.
 */
public class CommentExtractor {
    private SupportedFiles filetype;
    private String path;

    public CommentExtractor(String path, SupportedFiles filetype){
        this.path = path;
        this.filetype = filetype;

    }


    public ArrayList<Comment> extract(){
        switch (filetype){
            case JAVA:
                throw new NullPointerException("JAVA is currently not supported");

            case JAVASCRIPT:
                return new JavaScriptExtractor(this.path).extract();

            case PYTHON:
                throw new NullPointerException("PYTHON is currently not supported");

            case PHP:
                throw new NullPointerException("PHP is currently not supported");

            default:
                throw new NullPointerException("Could not allocate the extraction to a suitable engine");
        }

    }
}
