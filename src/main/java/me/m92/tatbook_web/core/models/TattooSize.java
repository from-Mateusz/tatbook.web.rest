package me.m92.tatbook_web.core.models;

public class TattooSize {

    private Float width;

    private Float length;

    private TattooSize() {}

    private TattooSize(Float width, Float length) {
        this.width = width;
        this.length = length;
    }

    public static TattooSize create(Float width, Float length) {
        return new TattooSize(width, length);
    }

    public Float getWidth() {
        return width;
    }

    public Float getLength() {
        return length;
    }
}
