package com.kvalwis;


import java.util.*;
/**
 *  This Class is used to evaluate the Expression and return the result
 *  */
public class Solver {
    /**Static maps to store operators for easy accessing rather than switch case
     *
     */
    static final Map<Character,Integer> precedenceMap ;
    static final Map<Character,Compute> operatorMap;

    /**
     * Initializing static elements
     *
     */
    static {
        precedenceMap= new HashMap<>();
        precedenceMap.put('+',1);
        precedenceMap.put('-',1);
        precedenceMap.put('*',2);
        precedenceMap.put('/',2);
        precedenceMap.put('%',2);
        precedenceMap.put('(',0);
        precedenceMap.put('^',3);

        operatorMap = new HashMap<>();
        operatorMap.put('+', new Add());
        operatorMap.put('-', new Sub());
        operatorMap.put('*', new Multiply());
        operatorMap.put('/', new Divide());
        operatorMap.put('%', new Modulo());
        operatorMap.put('^',new Exponent());
    }

    private final String source;
    Stack<Double> number;
    Stack<Character> operators;
    boolean hasError = false;
     Solver(String source) {
        this.source = source;
        number = new Stack<>();
        operators = new Stack<>();
    }

     private int start = 0;
     private int current = 0;

    /**
     * Method to solve the expression and return  double
     * if there is no error otherwise return an empty optional double*/
    public OptionalDouble solve(){
        //Filling up Stacks
        while(!isAtEnd() && !hasError){
            start = current;
            scan();
        }

        //Processing the stacks
        while(!operators.isEmpty()&&!hasError){
            operate();
        }
        if(number.isEmpty()){
            CalC.error("No valid Input found");
            hasError = true;
        }
        if(hasError){
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(number.peek());
    }

    /**
     * Method to scan the input and evaluate whenever necessary
     *
     */
    private void scan(){
        char ch = advance();
        if(ch==' ') return;

        if(isDigit(ch)){
            double n = number();
            number.push(n);
            return;
        }
        if(ch=='('){
            operators.push(ch);
            return;
        }
        if(ch==')'){
            if(operators.isEmpty()){
                CalC.error("Expected ')'");
            }
            while(!operators.isEmpty() &&operators.peek()!='('){
                operate();
            }
            if(!operators.isEmpty() && operators.peek()=='(') {
                operators.pop();
            } else{
                CalC.error("Expected ')'");
            }
            return;
        }

        if(precedenceMap.containsKey(ch)){
            while(!hasError && !operators.isEmpty() &&precedenceMap.get(operators.peek())>=precedenceMap.get(ch)){
                operate();
            }
            operators.push(ch);
        }else{
            CalC.error("Invalid input : "+ch);
            hasError = true;
        }
    }

    //Method to read number including decimal fom input
    private double number(){
        while(isDigit(peek())) advance();

        if(peek()=='.'&& isDigit(peekNext())){
            advance();

            while(isDigit(peek())) advance();
        }

        return Double.parseDouble(source.substring(start,current));
    }

    /***
     * Method to calculate the operations
     *
     */
    private void operate(){
        if(number.size()<2){
            CalC.error("Current Version doesn't support Unary Operations");
            hasError = true;
            return;
        }
        double d1 = number.pop();
        double d2 = number.pop();
        char operator = operators.pop();
        double result = operatorMap.get(operator).doCompute(d1,d2);
        number.push(result);
    }


    /**Additional methods for easy scanning
     *
     * @return
     */
    private char advance(){
        return source.charAt(current++);
    }
    
    private boolean isAtEnd(){
        return current>= source.length();
    }

    private boolean isDigit(char ch){
        return ch>='0' && ch<='9';
    }

    private char peek(){
        if(isAtEnd()) return '\0';
        return source.charAt(current);
    }
    private char peekNext(){
        if(current+1>=source.length()) return '\0';
        return source.charAt(current+1);
    }

}
