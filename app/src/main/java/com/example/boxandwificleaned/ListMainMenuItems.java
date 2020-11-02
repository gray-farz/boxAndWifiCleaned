package com.example.boxandwificleaned;
public class ListMainMenuItems {
    private int image;
    private Integer text;
    private String itemListStr;

    public ListMainMenuItems(int image, Integer text, String itemListStr) {
        this.image = image;
        this.text = text;
        this.itemListStr=itemListStr;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Integer getText() {
        return text;
    }

    public void setText(Integer text) {
        this.text = text;
    }

    public String getItemListStr() {
        return itemListStr;
    }

    public void setItemListStr(String itemListStr) {
        this.itemListStr = itemListStr;
    }
}

