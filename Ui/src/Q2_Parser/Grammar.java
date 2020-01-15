package Q2_Parser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Grammar {

    public static String grammarPathName = "C:\\Users\\B15\\Documents\\IntelliJ_IDEA\\TPCompiler\\src\\Q2_Parser\\ParsingRules.txt";
    public String[] grammarLines;
    public Rule[] rules;
    public String[] nonTerminals;
    public String[] terminals;
    public int nbrRules;

    public Grammar(String grammarPathName) throws IOException {
        this.grammarLines = getGrammarLines(grammarPathName);
        this.rules = getRules(this.grammarLines);
        this.nonTerminals = getNonTerminals(this.rules);
        this.terminals = getTerminals(this.rules, this.nonTerminals);
        this.nbrRules = getNbrRules(this.rules);
    }
    public Grammar(String[] grammarLines){
        this.grammarLines = grammarLines;
        this.rules = getRules(this.grammarLines);
        this.nonTerminals = getNonTerminals(this.rules);
        this.terminals = getTerminals(this.rules, this.nonTerminals);
        this.nbrRules = getNbrRules(this.rules);
    }

    private String[] getGrammarLines(String GrammarPathName) throws IOException {
        String line;
        ArrayList<String> lines = new ArrayList<>();
        System.out.println("Opening Grammar File... ");
        File grammarFile = new File(GrammarPathName);
        FileReader fileReader = new FileReader(grammarFile);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        while ((line = bufferReader.readLine()) != null) {
            lines.add(line);
        }
        System.out.println("Grammar File opened and closed Successfully !");
        String[] arrLines = new String[lines.size()];
        arrLines = lines.toArray(arrLines);
        return arrLines;
    }

    private Rule[] getRules(String[] grammarLines) {
        ArrayList<Rule> alRules = new ArrayList<>();
        for (int i = grammarLines.length - 1; i >= 0; i--) {
            Rule rule = new Rule(grammarLines[i]);
            alRules.add(rule);
        }
        Rule[] Rules = new Rule[alRules.size()];
        Rules = alRules.toArray(Rules);
        System.out.println("Grammar Rules Loaded Successfully ! ");
        Collections.reverse(Arrays.asList(Rules));
        return Rules;
    }

    private String[] getNonTerminals(Rule[] rules) {
        ArrayList<String> alNonTerminals = new ArrayList<>();
        for (Rule r : rules) {
            alNonTerminals.add(r.getName());
        }
        String[] arrNonTerminals = new String[alNonTerminals.size()];
        arrNonTerminals = alNonTerminals.toArray(arrNonTerminals);
        //Collections.reverse(Arrays.asList(arrNonTerminals));
        return arrNonTerminals;
    }

    private String[] getTerminals(Rule[] rules, String[] nonTerminals) {
        ArrayList<String> alTerminals = new ArrayList<>();
        for (Rule r : rules) {
            for (String[] as : r.getSubRules()) {
                for (String s : as) {
                    if (!Arrays.asList(nonTerminals).contains(s) && !s.equals("#")) {
                        alTerminals.add(s);
                    }
                }
            }
        }
        alTerminals.add("$");
        String[] arrTerminals = new String[alTerminals.size()];
        arrTerminals = alTerminals.toArray(arrTerminals);
        return arrTerminals;
    }

    public int getNbrRules(Rule[] rules) {
        int i = 0;
        for (Rule r : rules) {
            i += Rule.nbrSub(r);
        }
        return i;
    }
}