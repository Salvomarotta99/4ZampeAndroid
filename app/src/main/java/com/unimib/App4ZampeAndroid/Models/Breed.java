package com.unimib.App4ZampeAndroid.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "breeds")
public class Breed {




    @PrimaryKey(autoGenerate = true)
    private int breed_id;

    private String id;
    private String name;
    private String temperament;
    private String life_span;
    private String alt_names;
    private String wikipedia_url;
    private String origin;
    private String country_code;
    private BreedObject weight;
    private BreedObject height;
    private ImageBreed image;

    public Breed(String id, String name, String temperament, String life_span, String alt_names, String wikipedia_url, String origin, String country_code, BreedObject weight, BreedObject height) {
        this.id = id;
        this.name = name;
        this.temperament = temperament;
        this.life_span = life_span;
        this.alt_names = alt_names;
        this.wikipedia_url = wikipedia_url;
        this.origin = origin;
        this.country_code = country_code;
        this.weight = weight;
        this.height = height;
    }

    public int getBreed_id() {
        return breed_id;
    }
    public void setBreed_id(int breed_id) {
        this.breed_id = breed_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public String getAlt_names() {
        return alt_names;
    }

    public void setAlt_names(String alt_names) {
        this.alt_names = alt_names;
    }

    public String getWikipedia_url() {
        return wikipedia_url;
    }

    public void setWikipedia_url(String wikipedia_url) {
        this.wikipedia_url = wikipedia_url;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public BreedObject getWeight() {
        return weight;
    }

    public void setWeight(BreedObject weight) {
        this.weight = weight;
    }

    public BreedObject getHeight() {
        return height;
    }

    public void setHeight(BreedObject height) {

        this.height = height;
    }

    public ImageBreed getImage() {
        return image;
    }

    public void setImage(ImageBreed image) {
        this.image = image;
    }



}
