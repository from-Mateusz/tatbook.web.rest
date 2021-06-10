package me.m92.tatbook_web.communication.text;

public class Text {

    private String to;

    private String languageCode;

    private String content;

    private Text(String to, String languageCode, String content) {
        this.to = to;
        this.languageCode = languageCode;
        this.content = content;
    }

    public static Text createEmpty(String to, String languageCode) {
        return new Text(to, languageCode, "");
    }

    public void editContent(String newContent) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getContent() {
        return content;
    }
}
