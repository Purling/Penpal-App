public class SystemMessage extends TextMessage {
    String content; // How can we localise this into other languages?

    public SystemMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.date.toString() + this.time.toString() + " - " + content;
    }
}
