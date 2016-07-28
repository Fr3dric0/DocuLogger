package com.fredl.documentation.controllers;

import com.fredl.documentation.data.SupportedFiles;

/**
 * @author: Fredrik F. Lindhagen <fred.lindh96@gmail.com>
 * @created: 23.07.2016
 * @desc:
 */
public class FileIdentifier {

    public FileIdentifier(){

    }

    public static SupportedFiles identify(String path) {
        SupportedFiles[] supportedfiles = SupportedFiles.values();

        String buffer;
        String prefix;
        SupportedFiles sf;
        for(int i = 0; i < supportedfiles.length; i++){
            sf = supportedfiles[i]; // The value that could be returned

            prefix = sf.getFileprefix();

            // If the length of the prefix is larger than the prefix itself, there is no point in checking..
            if(prefix.length() > path.length()){
                System.out.println("TRUE");
                continue;
            }

            buffer = path.substring(path.length()-prefix.length(), path.length());

            if(buffer.equals(prefix)){
                return sf;
            }
        }

        return null;
    }

}
