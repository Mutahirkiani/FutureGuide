package com.mutahir.futureguide;

/**
 * Created by raja m afzal on 2/24/2018.
 */

public class databaseReview {
    String description,name,userImage;
    String rating;

    public  databaseReview(){

    }
    public String getRating() {
        return rating;
    }



    public databaseReview(String rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getuserImage() {
        return userImage;
    }

    public void setuserImage(String userImage) {
        this.userImage = userImage;
    }

}
