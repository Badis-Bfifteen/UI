package Q2_Parser;

import java.util.ArrayList;

public class Rule {
    private String name;
    private String[][] subRules;

    Rule(String rule) {
        ArrayList<String[]> subRules = new ArrayList<>();
        this.name = rule.split("->")[0];
        String[] subOr = (rule.split("->")[1]).split("\\|");
        for (String i : subOr) {
            subRules.add(i.split(","));
        }
        this.subRules = new String[subRules.size()][];
        this.subRules = subRules.toArray(this.subRules);
    }

    public static String print(Rule r) {
        String tempString="";
        for (int row = 0; row < r.subRules.length; row++) {
            System.out.print("-" + r.name + "(");
            tempString=tempString+"-" + r.name + "(";
            for (int column = 0; column < r.subRules[row].length; column++) {
                System.out.print(" \"" + r.subRules[row][column] + "\" ");
                tempString=tempString+" \"" + r.subRules[row][column] + "\" ";
            }
            System.out.println(")");
            tempString=tempString+")\n";

        }
        return tempString;
    }

    public static int nbrSub(Rule r) {
        int i = 0;
        for (int row = 0; row < r.subRules.length; row++) {
            i++;
        }
        return i;
    }

    /*public static int maxSub(Rule[] r) {
        int i=0;
        for (int row = 0; row < r.length; row++) {
            for (int column = 0; column < r[row].getSubRules().length; column++) {
                if(i<r[row].getSubRules().length)
                    i=r[row].getSubRules().length;
            }
        }
        return i;
    }
    public static int maxUnit(Rule[] r) {
        int i=0;
        for (int row = 0; row < r.length; row++) {
            for (int column = 0; column < r[row].getSubRules().length; column++) {
                if(i<r[row].getSubRules()[column].length)
                    i=r[row].getSubRules()[column].length;
            }
        }
        return i;
    }*/

    public String getName() {
        return this.name;
    }

    public String[][] getSubRules() {
        return this.subRules;
    }
}
