package com.kvalwis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.OptionalDouble;

/**
 * Main Method
 *
 */
public class CalC
{
    public static void main( String[] args ) throws IOException {
        run();
    }

    private static void run() throws IOException {

        System.out.println("CalC - A Command line based Expression Solver");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        Solver solver;

        for(;;){
            System.out.print("CalC>> ");
            String line = br.readLine();
            if(line == null) break;
            if(line.equalsIgnoreCase("HELP")){
                help();
                continue;
            }
            if(line.equalsIgnoreCase("EXIT")) break;
            solver = new Solver(line);
            OptionalDouble result = solver.solve();
            if(result.isPresent()){
                System.out.println("Solution : "+line+" = "+result.getAsDouble());
            }else{
                System.out.println("!Oops!");
                System.out.println("Enter Valid Input");
            }

        }
    }

    public static void error(String msg){
        System.out.println(msg);
    }

    /**
     * Helper
     */

    private static void help(){
        System.out.println();
        System.out.println("!! Welcome to CalC !!");
        System.out.println("This is a command Line based tool to evaluate arithmetic expressions");
        System.out.println("Supported operations in this Version (Only Binary Operations)");
        System.out.println("Addition"+ " : +");
        System.out.println("Subtraction"+" : -");
        System.out.println("Multiplication"+" *");
        System.out.println("Division" + " : /");
        System.out.println("Power of"+" : ^");
        System.out.println("Modulo"+" : %");

        System.out.println();
    }
}
