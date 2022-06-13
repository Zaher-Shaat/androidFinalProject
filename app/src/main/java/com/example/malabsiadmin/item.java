package com.example.malabsiadmin;

public class item {
    private String id;
    private String code;
    private String price;
    private String image;
    private String desc;

    public item() {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public item(String id, String code, String price, String image, String desc) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.image = image;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "item{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", description='" + desc +
                '}';
    }
}
