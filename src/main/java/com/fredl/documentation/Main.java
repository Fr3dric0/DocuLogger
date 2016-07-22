package com.fredl.documentation;

public class Main{

    public static void main(String[] args){

        try{
            DocuLogger videoJSLogger = new DocuLogger("./src/main/resources/api.js");
        }catch(NullPointerException npe){
            System.out.println("THe filepath provided is not valid!");
        }


    }
}