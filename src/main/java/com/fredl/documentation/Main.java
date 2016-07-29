package com.fredl.documentation;

import java.util.Scanner;

public class Main{
    private static int tries = 3;

    public static void main(String[] args){

        run();
    }

    /*
    *   @desc: Eit lite terminalbassert gui
    * */
    private static void run(){
        Scanner sc = new Scanner(System.in);
        String inPath;
        String outPath;
        System.out.println("VELKOMMEN TIL DocuLogger");

        // Get path to JavaScript program
        while(true){
            System.out.print("Namnet på programmet ditt: ");
            inPath = sc.nextLine();

            if(inPath.length() > 0){
                break;
            }else{
                System.err.println("Ugyldig namn!\n");
            }
        }

        // Get directory to where the user want's the json file to go
        System.out.println("Flott. No trenger vi bere å vite kvar du vil skrive ut programmet ditt");
        System.out.print("Bane: ");
        outPath = sc.nextLine();

        // Instanciate the DocuLogger object,
        // and run the makeFile method
        try{
            DocuLogger apiJSLogger = new DocuLogger(inPath);
            apiJSLogger.makeFile(outPath);
        }catch(NullPointerException npe){
            System.err.println("JavaScript fila finst ikkje!");
            tries--;

            if(tries > 0){
                System.out.println("Du har "+tries+" Forsøk igjen, før programmet stoppar");
                run();
            }else{
                System.out.println("Programmet kunne ikkje køyre.\nHa ein fin dag!");
                System.exit(-1);
            }
        }

    }
}