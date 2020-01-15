package Q2_Parser;

import Q1_Lexer.Token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Parser {
    public boolean parsable;
    public Parser(Token[] input, ParsingTable parsingTable) {
        this.parsable = getParsable(input, parsingTable);
    }

    public static boolean getParsable(Token[] input, ParsingTable parsingTable) {
        String expected,found = "";

        Stack<String> inputStack = new Stack<>();
        for (int i = input.length - 1; i >= 0; i--) {
            inputStack.add(inputOfToken(input[i]));
        }
        String[] terminals = parsingTable.terminals;
        String[] nonTerminals = parsingTable.nonTerminals;
        Stack<String> parsingStack = new Stack<>();
        parsingStack.push(terminals[terminals.length - 1]);
        if (!inputStack.peek().equals("$"))
            parsingStack.push(nonTerminals[0]);
        String[] parsingMatrixExtract;
        while (!inputStack.empty()) {
            System.out.println("input: " + inputStack.toString());
            System.out.println("Stack: " + parsingStack.toString());
            expected=parsingStack.peek();
            found=inputStack.peek();
            System.out.println("Matching \"" +found+ "\" with \"" + expected + "\"");
            if (inputStack.peek().equals(parsingStack.peek())) {
                System.out.println("Matching Successful !");
                System.out.println("Removing terminal match ! ");
                inputStack.pop();
                parsingStack.pop();
            } else if (FirstFollow.isNonTerminal(parsingStack.peek(), parsingTable.grammar)) {
                System.out.println("Matching Failed !");
                parsingMatrixExtract = parsingTable.parsingMatrix[Arrays.asList(nonTerminals).indexOf(parsingStack.peek())][Arrays.asList(terminals).indexOf(inputStack.peek())];
                System.out.println("Replacing \"" + parsingStack.peek() + "\" Respectively with  ");
                if (parsingMatrixExtract[0] != null && !parsingMatrixExtract[0].equals("#")) {
                    parsingStack.pop();
                    for (int i =parsingMatrixExtract.length-1;i>=0;i--) {
                        parsingStack.add(parsingMatrixExtract[i]);
                        System.out.println("-> " + parsingMatrixExtract[i]);
                    }
                } else if (parsingMatrixExtract[0] != null) {
                    System.out.println("-> Epsilon \nResulting only in the Removal of \"" + parsingStack.peek() + "\"");
                    parsingStack.pop();
                } else {
                    System.out.println("-> Null ");
                    System.out.println("Error Parsing Failed ! ");
                    return false;
                }
            } else if(parsingStack.peek().equals("$")) {
                System.out.println("Error Parsing Failed ! ");
                System.out.println("Matching terminals Failed !\nClosing parenthesis found but was never opened !");
                return false;
            }else if(parsingStack.peek().equals(")")){
                System.out.println("Matching terminals Failed !\nOpening parenthesis found but was never closed !");
                return false;
            }else {
                System.out.println("Matching terminals Failed ! (check Grammar)");
                return false;
            }
        }

        return true;
    }

    public static String inputOfToken(Token token) {
        switch (token.getType()) {
            case INTEGER:
                return "INTEGER";
            case END:
            case OPERATOR:
            case PARENTHESIS:
                return token.getValue();
            case IDENTIFIER:
                return "IDENTIFIER";

        }
        return null;
    }

    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b)
    {  Set<T> merge= new HashSet<T>() {{ addAll(a);addAll(b); } };
        return merge;
    }

}
