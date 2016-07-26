package com.fredl.documentation;

public class Main{

    public static void main(String[] args){
        DocuLogger apiJSLogger;

        // Deklare documentation-logger for api.js
        try{
            apiJSLogger = new DocuLogger("./src/main/resources/api.js");
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }





    }
}