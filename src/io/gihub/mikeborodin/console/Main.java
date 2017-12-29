package io.gihub.mikeborodin.console;

import io.gihub.mikeborodin.interpreter.context.Context;
import io.gihub.mikeborodin.interpreter.lexer.Lexer;
import io.gihub.mikeborodin.interpreter.parser.IStatement;
import io.gihub.mikeborodin.interpreter.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {

        if(args.length!=1){
            System.out.println("We need only 1 main argument");
            return;
        }

        File codeFile = new File(args[0]);
        if(!codeFile.exists()){
            System.out.println("Wrong src file");
            return;
        }

        ConsoleInputManager im = new ConsoleInputManager();
        Context context = new Context(im,
                codeFile.getParentFile().getAbsolutePath()
        );


        printHello();
        String fsCode;



        try
        {
            fsCode = new Scanner(codeFile).useDelimiter("\\Z").next();
            System.out.println(ANSI_CYAN+fsCode+ANSI_RESET);
        }
        catch(FileNotFoundException e)
        {
            System.out.println(ANSI_RED+"Source file not found"+ANSI_RESET);
            return;
        }

        Lexer lexer = new Lexer(fsCode);
        Parser parser = new Parser(lexer);

        System.out.println(ANSI_YELLOW+"Output:\n"+ANSI_BLUE);
        try{
            IStatement cur = parser.buildStatement();
            while (cur!=null){
                cur.execute(context);
                cur = parser.buildStatement();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private static void printHello() {
        System.out.println(
                ANSI_YELLOW
                        + "############################\n"
                        + "######## FILE SCRIPT #######\n"
                        + "############################\n"
                        + "v0.1.0       by MikeBorodin\n"
                + ANSI_RESET);

    }
}
