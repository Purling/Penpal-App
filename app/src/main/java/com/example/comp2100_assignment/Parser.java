package com.example.comp2100_assignment;

public class Parser {
    public Parser(Tokenizer tokenizer) {
    }

    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    Tokenizer tokenizer;


    public Exp parseExp() {

        Exp term = parseTerm();

        if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.AND){
            tokenizer.next();
            Exp exp = parseExp();
            return new AndExp(term,exp);
        }else
        if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.OR){
            tokenizer.next();
            Exp exp = parseExp();
            return new OrExp(term,exp);
        }else
            return term;

    }
    public Exp parseTerm() {

        if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.NOT){
            tokenizer.next();
            Exp search = parseSearch();
            return new NotExp(search);
        }else
            return parseSearch();
    }
    public Exp parseSearch() {
        Exp content = new contentText(tokenizer.current().getToken());
        tokenizer.next();
        return content;
    }
}
