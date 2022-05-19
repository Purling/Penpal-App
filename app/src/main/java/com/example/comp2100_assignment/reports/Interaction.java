package com.example.comp2100_assignment.reports;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Stores details about a user's interaction to list in the report
 * @author Zane Gates
 */
public class Interaction implements Serializable {
    long time;
    InteractionType type;
    String detail;

    public Interaction() {
        // Default constructor required for Firebase serialization
    }

    public Interaction(long time, InteractionType interactionType, String interactionName) {
        this.time = time;
        this.type = interactionType;
        this.detail = interactionName;
    }

    public String toString() {
        return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC) + ": " + detail + " (" + type + ")";
    }
}
