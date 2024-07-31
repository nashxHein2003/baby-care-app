package com.buc.babycare;

public class Model {
    private String id;
    private String name;
    private String quantity;
    private String location;

    private byte[] image;
    private boolean isChecked;

    public Model(String id, String name, String quantity, String location, boolean isChecked, byte[] image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
        this.isChecked = isChecked;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
