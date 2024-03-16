package com.example.movieapp;

public class Movie {
    public String name;
    public String image;
    public String certification;
    public String description;
    public String[] starring;
    public int running_time_mins;
    public int seats_remaining;
    public int seats_selected;

    public Movie(String name, String image, String certification, String description, String[] starring, int running_time_mins, int seats_remaining, int seats_selected) {
        this.name = name;
        this.image = image;
        this.certification = certification;
        this.description = description;
        this.starring = starring;
        this.running_time_mins = running_time_mins;
        this.seats_remaining = seats_remaining;
        this.seats_selected = seats_selected;
    }
}
