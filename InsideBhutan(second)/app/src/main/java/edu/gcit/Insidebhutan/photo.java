package edu.gcit.Insidebhutan;

public class photo {
    String photo, text;
    public photo(){}

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public photo(String photo, String des){
        this.photo = photo;
        this.text = des;
    }
}
