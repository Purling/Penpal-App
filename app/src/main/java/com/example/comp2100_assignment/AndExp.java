package com.example.comp2100_assignment;

/***
 * @author Xingkun Chen
 */
public class AndExp extends Exp{
    private Exp term;
    private Exp exp;

    public AndExp(Exp term, Exp exp) {
        this.term = term;
        this.exp = exp;
    }

    @Override
    public String show() {
        return "," + term.show() + " , " + exp.show() + " ,";
    }
}

