package com.growtogether.myrestaurant.pojo;

public class Option {

    private int optionImage;
    private String optionName;

    public Option(int optionImage, String optionName) {
        this.optionImage = optionImage;
        this.optionName = optionName;
    }

    public Option() {
    }

    public int getOptionImage() {
        return optionImage;
    }

    public void setOptionImage(int optionImage) {
        this.optionImage = optionImage;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
