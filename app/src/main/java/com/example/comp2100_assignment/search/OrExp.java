package com.example.comp2100_assignment.search;

/***
 * @author Xingkun Chen
 */
public class OrExp extends Exp{
    private Exp term;
    private Exp exp;

    public OrExp(Exp term, Exp exp) {
        this.term = term;
        this.exp = exp;
    }

    @Override
    public String show() {
        return "( " + term.show() + " or " + exp.show() + " )";
    }
}
