package com.example.comp2100_assignment;

/***
 * A parser class which parses a specific grammar.
 * Grammar developed with the help of Ziling Ouyang
 *
 * @author Xingkun Chen
 */
public class Parser {
    Tokenizer tokenizer;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Exp parseExp() {

        Exp term = parseTerm();

        if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.AND) {
            tokenizer.next();
            Exp exp = parseExp();
            if (exp == null){
                throw new IllegalProductionException("Wrong insert");
            }else
                return new AndExp(term, exp);
        } else if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.OR) {
            tokenizer.next();
            Exp exp = parseExp();
            if (exp == null){
                throw new IllegalProductionException("Wrong insert");
            }else
                return new OrExp(term, exp);
        } else
            return term;
    }

    public Exp parseTerm() {

        if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.NOT) {
            tokenizer.next();
            Exp search = parseSearch();
            return new NotExp(search);
        } else
            return parseSearch();
    }

    public Exp parseSearch() {
        Exp content = new contentText(tokenizer.current().getToken());
        if (tokenizer.current().getType() == Token.Type.NOT ||
                tokenizer.current().getType() == Token.Type.AND ||
                tokenizer.current().getType() == Token.Type.OR) {
            throw new IllegalProductionException("Wrong insert");
        } else {
            tokenizer.next();
            return content;
        }
    }

    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }
}
