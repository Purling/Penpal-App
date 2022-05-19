package com.example.comp2100_assignment;

/***
 * @author Xingkun Chen
 */
public class contentText extends Exp{

    String content;

    public contentText(String content) {
        this.content = content;
    }

    @Override
    public String show() {
        return content;
    }
}
