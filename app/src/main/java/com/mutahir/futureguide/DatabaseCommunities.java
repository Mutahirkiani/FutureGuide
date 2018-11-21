package com.mutahir.futureguide;

/**
 * Created by raja m afzal on 11/29/2017.
 */

public class DatabaseCommunities {
    String name;
    String followers;
    String description;

    public DatabaseCommunities(){
    }

    public DatabaseCommunities(String name, String followers, String description){
        this.name=name;
        this.followers=followers;
        this.description=description;

    }


    public String getName() {
        return name;
    }

    public String getFollowers() {
        return followers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public void setName(String name) {
        this.name = name;
    }
}
