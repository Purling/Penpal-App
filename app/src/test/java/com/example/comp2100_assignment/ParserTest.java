package com.example.comp2100_assignment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Tests for Parser class
 * @author William Loughton
 */
public class ParserTest {


    //testing that parser correctly rejects invalid input
    @Test (expected = Parser.IllegalProductionException.class)
    public void testException1(){
        String invalid = "not not";
        Tokenizer tokenizer = new Tokenizer(invalid);
        Parser parser = new Parser(tokenizer);
        parser.parseExp();
    }
    @Test (expected = Parser.IllegalProductionException.class)
    public void testException2(){
        String invalid = "ENGLISH and not and";
        Tokenizer tokenizer = new Tokenizer(invalid);
        Parser parser = new Parser(tokenizer);
        parser.parseExp();
    }
    @Test (expected = Parser.IllegalProductionException.class)
    public void testException3(){
        String invalid = "FOOD or TRAVEL and ENGLISH or";
        Tokenizer tokenizer = new Tokenizer(invalid);
        Parser parser = new Parser(tokenizer);
        parser.parseExp();
    }
    @Test (expected = Parser.IllegalProductionException.class)
    public void testException4(){
        String invalid = "or ENGLISH and ENGLISH";
        Tokenizer tokenizer = new Tokenizer(invalid);
        Parser parser = new Parser(tokenizer);
        parser.parseExp();
    }

}
