package Q1_Lexer;
import org.jetbrains.annotations.NotNull;

public class Token {

    private TokenType type;
    private String value;
    private int index;

    Token(TokenType Type, String Value, int Index) {
        this.type = Type;
        this.value = Value;
        this.index = Index;
    }

    public static String printToken(@NotNull Token token) {
        String tempS = "";
        final String s = "-Token(index = " + token.index + " ; Value = \"" + token.value + "\" ; Type = " + token.type + " )\n";
        if (token.type == TokenType.INVALID) {

            System.out.print("\u001B[31m" + s + "\u001B[0m");
            tempS = "Error: " + tempS + s;


        } else {
            System.out.print(s);
            tempS = tempS + s;
        }
        return tempS;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public int getIndex(){
        return this.index;
    }
}
