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
            buffer = path.substring(path.length()-prefix.length(), path.length());

            if(buffer.equals(prefix)){
                return sf;
            }
        }

        return null;
    }

    private static String[] getFileprefixes(){
        SupportedFiles[] sf = SupportedFiles.values();
        String[] prefixes = new String[sf.length];

        for(int i = 0; i < sf.length; i++){
            prefixes[i] = sf[i].getFileprefix();
        }

        return prefixes;
    }
}
