package Q2_Parser;

import java.io.IOException;
import java.util.Arrays;

public class test {
    public static void main(String[] args) throws IOException {
        Grammar grammar =new Grammar(Grammar.grammarPathName);
        System.out.println("Terminals :");
        for (String i : grammar.terminals) {
            System.out.println(i);
        }
        System.out.println("NonTerminals :");
        for (String i : grammar.nonTerminals) {
            System.out.println(i);
        }
        System.out.println(grammar.nbrRules + " Rules :");
        Rule[] Rules = grammar.rules;
        for (Rule i : Rules) {
            Rule.print(i);
        }
        System.out.println("Firsts :");
        for (String s : grammar.nonTerminals) {
            System.out.println("First(" + s + ") = " + FirstFollow.First(s,grammar));
        }
        System.out.println("Follows :");
        for (String s : grammar.nonTerminals) {
            System.out.println("Follow(" + s + ") = " + FirstFollow.Follow(s,grammar));
        }
        ParsingTable parsingTable=new ParsingTable(grammar);
        System.out.println(Arrays.deepToString(parsingTable.parsingMatrix));

    }
}
