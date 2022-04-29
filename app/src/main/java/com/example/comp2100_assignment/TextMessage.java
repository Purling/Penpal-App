package com.example.comp2100_assignment;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class TextMessage {
    Time time;
    Date date;

    public TextMessage() {
        this.date = Date.valueOf(String.valueOf(LocalDate.now()));
        this.time = Time.valueOf(String.valueOf(LocalTime.now()));

    }

    public abstract String getContent();
}