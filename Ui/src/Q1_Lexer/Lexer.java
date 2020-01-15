package Q1_Lexer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Lexer {

    @NotNull
    public static Token[] getTokens(@NotNull String sourceCode) {
        System.out.println("Extracting Tokens from Expression...");
        Token[] tokenArray;
        ArrayList<Token> tokenArrayList;
        tokenArrayList = new ArrayList<>();
        boolean Integer;
        boolean Identifier;
        boolean Invalid;
        Integer = false;
        Identifier = false;
        Invalid = false;
        Symbol nextSymbol;
        char nextChar;
        int tokenNbr;
        tokenNbr = 0;


        for (int i = 0; i < sourceCode.length() && !sourceCode.isEmpty(); i++) {
            nextChar = sourceCode.charAt(i);
            nextSymbol = Lexer.getSymbol(nextChar);
            switch (nextSymbol) {
                case Letter:
                    if (i == 0) {
                        Identifier = true;
                    } else {
                        if (Integer) {
                            Invalid = true;
                            Identifier = false;
                            Integer = false;
                        }
                    }
                    break;
                case Digit:
                    if (i == 0) {
                        Integer = true;
                    }
                    break;
                case Operator:
                    if (i == 0) {
                        Token token = new Token(TokenType.OPERATOR, String.valueOf(nextChar), tokenNbr++);
                        tokenArrayList.add(token);
                        sourceCode = sourceCode.substring(i + 1);
                    } else {
                        String strToken = sourceCode.substring(0, i);
                        sourceCode = sourceCode.substring(i);
                        if (Integer) {
                            Token token = new Token(TokenType.INTEGER, strToken, tokenNbr++);
                            tokenArrayList.add(token);
                        } else {
                            if (Identifier) {
                                Token token = new Token(TokenType.IDENTIFIER, strToken, tokenNbr++);
                                tokenArrayList.add(token);
                            } else {
                                if (Invalid) {
                                    Token token = new Token(TokenType.INVALID, strToken, tokenNbr++);
                                    tokenArrayList.add(token);
                                }
                            }
                        }
                    }
                    i = -1;
                    Integer = false;
                    Identifier = false;
                    Invalid = false;
                    break;
                case Space:
                    if (i != 0) {
                        String strToken = sourceCode.substring(0, i);
                        sourceCode = sourceCode.substring(i + 1);
                        if (Integer) {
                            Token token = new Token(TokenType.INTEGER, strToken, tokenNbr++);
                            tokenArrayList.add(token);
                        } else {
                            if (Identifier) {
                                Token token = new Token(TokenType.IDENTIFIER, strToken, tokenNbr++);
                                tokenArrayList.add(token);
                            } else {
                                if (Invalid) {
                                    Token token = new Token(TokenType.INVALID, strToken, tokenNbr++);
                                    tokenArrayList.add(token);
                                }
                            }
                        }
                        i = -1;
                        Integer = false;
                        Identifier = false;
                        Invalid = false;
                    } else {
                        sourceCode = sourceCode.substring(i + 1);
                        i = -1;
                    }
                    break;
                case Parenthesis:
                    if (i == 0) {
                        Token token = new Token(TokenType.PARENTHESIS, String.valueOf(nextChar), tokenNbr++);
                        tokenArrayList.add(token);
                        sourceCode = sourceCode.substring(i + 1);
                    } else {
                        String strToken = sourceCode.substring(0, i);
                        sourceCode = sourceCode.substring(i);
                        if (Integer) {
                            Token token = new Token(TokenType.INTEGER, strToken, tokenNbr++);
                            tokenArrayList.add(token);
                        } else {
                            if (Identifier) {
                                Token token = new Token(TokenType.IDENTIFIER, strToken, tokenNbr++);
                                tokenArrayList.add(token);
                            } else {
                                if (Invalid) {
                                    Token token = new Token(TokenType.INVALID, strToken, tokenNbr++);
                                    tokenArrayList.add(token);
                                }
                            }
                        }
                    }
                    i = -1;
                    Integer = false;
                    Identifier = false;
                    Invalid = false;
                    break;
                case Invalid:
                    Integer = false;
                    Identifier = false;
                    Invalid = true;
            }

            if ((sourceCode.length() != 0) && (i == (sourceCode.length()) - 1)) {
                String strToken = sourceCode;
                sourceCode = sourceCode.substring(0, i);
                if (Integer) {
                    Token token = new Token(TokenType.INTEGER, strToken, tokenNbr++);
                    tokenArrayList.add(token);
                } else {
                    if (Identifier) {
                        Token token = new Token(TokenType.IDENTIFIER, strToken, tokenNbr++);
                        tokenArrayList.add(token);
                    } else {
                        if (Invalid) {
                            Token token = new Token(TokenType.INVALID, strToken, tokenNbr++);
                            tokenArrayList.add(token);
                        }
                    }
                }
                Integer = false;
                Identifier = false;
                Invalid = false;

            }
        }


        Token token = new Token(TokenType.END, "$", tokenNbr);
        tokenArrayList.add(token);
        tokenArray = new Token[tokenArrayList.size()];
        tokenArray = tokenArrayList.toArray(tokenArray);
        System.out.println("Tokens Extraction Successful !");
        return tokenArray;
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '*' || c == '-' || c == '/';
    }

    private static Symbol getSymbol(char c) {
        if (isLetter(c)) return Symbol.Letter;
        else if (isDigit(c)) return Symbol.Digit;
        else if (isOperator(c)) return Symbol.Operator;
        else if (c == ')' || c == '(') return Symbol.Parenthesis;
        else if (c == ' ' || c == '\n' || c == '\t') return Symbol.Space;
        return Symbol.Invalid;
    }

    public static boolean errorFound(Token[] tokens){
        for (Token t : tokens) {
            if(t.getType().equals(TokenType.INVALID))return true;
        }
        return false;
    }
}