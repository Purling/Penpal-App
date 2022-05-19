package com.example.comp2100_assignment.reports;

import com.example.comp2100_assignment.conversations.Conversation;
import com.example.comp2100_assignment.database.ConversationDao;

import java.util.HashMap;

/**
 * @author Zane Gates
 * A factory for reports
 * Currently, reports are in string form, but a factory is useful
 * if we want to use a more graphical representation
 */
public class ReportFactory {
    public static String generateReport(HashMap<String, Interaction> interactions) {
        if (interactions == null) return "";

        StringBuilder output = new StringBuilder();
        boolean firstInteraction = true;
        for (Interaction interaction : interactions.values()) {
            // Add line breaks after each entry
            if (interaction == null) continue;
            if (!firstInteraction) {
                output.append("\n");
            }
            firstInteraction = false;

            output.append(interaction);
        }

        return output.toString();
    }
}
