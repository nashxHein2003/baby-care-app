package com.buc.babycare;

public class Model {
    private String id;
    private String name;
    private String quantity;
    private String location;
    private String imageUri;
    private boolean isChecked;

    public Model(String id, String name, String quantity, String location, String imageUri, boolean isChecked) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
        this.imageUri = imageUri;
        this.isChecked = isChecked;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
