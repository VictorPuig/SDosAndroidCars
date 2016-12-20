package com.example.admin.sdosandroidcars.api.cars;


public class Car {

    private int id;
    private String name;
    private String color;
    private String maker;
    private String imgUrl;

    public Car(int id, String name, String color, String maker, String imgUrl) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.maker = maker;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getMaker() {
        return maker;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", maker='" + maker + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
