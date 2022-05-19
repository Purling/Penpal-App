package com.example.comp2100_assignment.search;

/***
 * @author Xingkun Chen
 */
public class NotExp extends Exp{
    private Exp search;

    public NotExp(Exp search) {
        this.search = search;
    }

    @Override
    public String show() {
        return ",not " + search.show() + " ,";
    }
}
