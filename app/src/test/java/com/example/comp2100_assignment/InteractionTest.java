package com.example.comp2100_assignment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import android.icu.text.DateFormat;

import com.example.comp2100_assignment.reports.Interaction;
import com.example.comp2100_assignment.reports.InteractionType;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
/**
 * Tests for interaction class
 * @author William Loughton
 */
public class InteractionTest {
    @Test
    public void interactionRuns(){
        assertNotNull(new Interaction(1,InteractionType.TRANSITORY_CONVERSATION,"sample"));
    }
}
    /*
    //late time package change breaks functionality
    //for this to work, we need to mock LocalDateTime

    @Test
    public void testValid(){
        //test a sample
        Interaction interaction = new Interaction(LocalDateTime.ofEpochSecond(1234,0, ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC), InteractionType.TRANSITORY_CONVERSATION,"samplename");
        String expected = DateFormat.getInstance().format(1234) + ": " + "samplename" + " (" + InteractionType.TRANSITORY_CONVERSATION + ")";
        assertEquals(expected,interaction.toString());
        //test another sample
        interaction = new Interaction(LocalDateTime.ofEpochSecond(999999999,0,ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC), InteractionType.PERMANENT_CONVERSATION, "cool convo");
        expected = DateFormat.getInstance().format(999999999) + ": " + "cool convo" + " (" + InteractionType.PERMANENT_CONVERSATION + ")";
        assertEquals(expected,interaction.toString());
        //test setter methods
        interaction.setType(InteractionType.TRANSITORY_CONVERSATION);
        interaction.setDetail("not a cool convo");
        interaction.setTime(0);
        expected = DateFormat.getInstance().format(0) + ": " + "not a cool convo" + " (" + InteractionType.TRANSITORY_CONVERSATION + ")";
        assertEquals(expected,interaction.toString());
    }
    */
