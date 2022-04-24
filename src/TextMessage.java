import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class TextMessage {
    Time time;
    Date date;

    public TextMessage() {
        this.date = Date.valueOf(LocalDate.now());
        this.time = Time.valueOf(LocalTime.now());
    }

    public abstract String getContent();
}