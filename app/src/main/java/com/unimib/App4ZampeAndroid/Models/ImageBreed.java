package com.unimib.App4ZampeAndroid.Models;

import java.util.List;

public class ImageBreed {

    private String id;
    private String url;
    private List<Breed> breeds;


    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }


    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
