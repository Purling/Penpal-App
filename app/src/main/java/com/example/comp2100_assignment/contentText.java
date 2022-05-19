package com.example.comp2100_assignment;

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
