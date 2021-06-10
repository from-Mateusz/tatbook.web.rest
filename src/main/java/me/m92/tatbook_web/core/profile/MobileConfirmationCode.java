package me.m92.tatbook_web.core.profile;

public class MobileConfirmationCode {

    private String original;

    private String hash;

    private MobileConfirmationCode(String original, String hash) {
        this.original = original;
        this.hash = hash;
    }

    public static MobileConfirmationCode create(String original, String hash) {
        return new MobileConfirmationCode(original, hash);
    }

    public String getOriginal() {
        return original;
    }

    public String getHash() {
        return hash;
    }
}
