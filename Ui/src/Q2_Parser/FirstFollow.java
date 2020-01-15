package Q2_Parser;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class FirstFollow {

    public static SortedSet<String> First(String tNt, Grammar grammar) {
        SortedSet<String> set = new TreeSet<>();
        if (isTerminal(tNt, grammar) || tNt.equals("#")) {
            set.add(tNt);
        } else if (isNonTerminal(tNt, grammar)) {
            for (int i = 0; i < grammar.rules.length; i++) {
                if (grammar.rules[i].getName().equals(tNt)) {
                    for (int j = 0; j < grammar.rules[i].getSubRules().length; j++)
                        set.addAll(First(grammar.rules[i].getSubRules()[j][0], grammar));
                }
            }
        }
        return set;
    }

    public static SortedSet<String> Follow(String Nt, Grammar grammar) {
        SortedSet<String> set = new TreeSet<>();
        if (isNonTerminal(Nt, grammar)) {
            for (int i = grammar.rules.length - 1; i >= 0; i--) {
                if (Nt.equals(grammar.nonTerminals[0])) {
                    set.add("$");
                }
                if (Nt.equals(grammar.rules[i].getName())) {
                    for (int r = grammar.rules.length - 1; r >= 0; r--) {
                        for (int x = 0; x < grammar.rules[r].getSubRules().length; x++) {
                            for (int y = 0; y < grammar.rules[r].getSubRules()[x].length; y++) {

                                if (Nt.equals(grammar.rules[r].getSubRules()[x][y]) && y != grammar.rules[r].getSubRules()[x].length - 1) {

                                    if (First(grammar.rules[r].getSubRules()[x][y + 1], grammar).contains("#")) {
                                        set.addAll(First(grammar.rules[r].getSubRules()[x][y + 1], grammar));
                                        set.remove("#");
                                        set.addAll(Follow(grammar.rules[r].getName(), grammar));
                                    } else {
                                        set.addAll(First(grammar.rules[r].getSubRules()[x][y + 1], grammar));
                                    }
                                    break;

                                } else if (Nt.equals(grammar.rules[r].getSubRules()[x][y]) && y == grammar.rules[r].getSubRules()[x].length - 1 && !grammar.rules[r].getName().equals(Nt)) {
                                    set.addAll(Follow(grammar.rules[r].getName(), grammar));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return set;
    }

    public static Boolean isTerminal(String s, Grammar grammar) {
        return Arrays.asList(grammar.terminals).contains(s);
    }

    public static Boolean isNonTerminal(String s, Grammar grammar) {
        return Arrays.asList(grammar.nonTerminals).contains(s);
    }

}
