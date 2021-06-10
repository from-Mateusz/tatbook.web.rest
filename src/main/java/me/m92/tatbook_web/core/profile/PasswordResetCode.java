package me.m92.tatbook_web.core.profile;

import java.util.Objects;

public class PasswordResetCode {

    private String original;

    private String hash;

    private PasswordResetCode(String original, String hash) {
        this.original = original;
        this.hash = hash;
    }

    public static PasswordResetCode create(String original, String hash) {
        return new PasswordResetCode(original, hash);
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetCode that = (PasswordResetCode) o;
        return Objects.equals(original, that.original) &&
                Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, hash);
    }

    @Override
    public String toString() {
        return "PasswordResetCode{" +
                "original='" + original + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
