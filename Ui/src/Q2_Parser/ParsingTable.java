package Q2_Parser;

import java.util.Arrays;

public class ParsingTable {
    public String[] terminals;
    public String[] nonTerminals;
    public String[][][] parsingMatrix;
    public Grammar grammar;

    public ParsingTable(Grammar grammar) {
        this.grammar = grammar;
        this.terminals = this.grammar.terminals;
        this.nonTerminals = this.grammar.nonTerminals;
        this.parsingMatrix = getParsingTable(this.nonTerminals, this.terminals, this.grammar);
    }


    public static String[][][] getParsingTable(String[] nonTerminals, String[] terminals, Grammar grammar) {
        Rule[] rules = grammar.rules;
        String[] epsilon = {"#"};
        String[][][] ParsingTable = new String[nonTerminals.length][terminals.length][1];
        for (int i = 0; i < ParsingTable.length; i++) {
            for (int j = 0; j < ParsingTable[i].length; j++) {
                if (FirstFollow.First(nonTerminals[i], grammar).contains(terminals[j])) {
                    ParsingTable[i][j] = SubRuleOf(rules[i], terminals[j], grammar);
                }
                if (FirstFollow.First(nonTerminals[i], grammar).contains("#")) {
                    if (FirstFollow.Follow(nonTerminals[i], grammar).contains(terminals[j])) {
                        ParsingTable[i][j] = epsilon;
                    }
                }
            }
        }
        return ParsingTable;
    }

    public static String[] SubRuleOf(Rule rule, String Terminal, Grammar grammar) {
        for (int row = 0; row < rule.getSubRules().length; row++) {
            if (terminalExistInRule(rule, Terminal)) {
                if (Arrays.asList(rule.getSubRules()[row]).contains(Terminal))
                    return rule.getSubRules()[row];
            } else if (FirstFollow.First(rule.getName(), grammar).contains(Terminal))
                return rule.getSubRules()[row];
        }
        return new String[0];
    }

    public static boolean terminalExistInRule(Rule rule, String Terminal) {
        for (int row = 0; row < rule.getSubRules().length; row++) {
            if (Arrays.asList(rule.getSubRules()[row]).contains(Terminal))
                return true;
        }
        return false;
    }
}
