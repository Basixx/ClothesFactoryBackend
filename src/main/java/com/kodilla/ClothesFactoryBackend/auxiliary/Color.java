package com.kodilla.ClothesFactoryBackend.auxiliary;

public enum Color {
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow"),
    GREEN("green"),
    PINK("pink"),
    WHITE("white"),
    BLACK("black");


   private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}