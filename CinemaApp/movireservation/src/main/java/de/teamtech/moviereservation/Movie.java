package de.teamtech.moviereservation;

import java.util.Date;

public class Movie {
    
    private String title;
    private String genre;
    private Date releaseDate;
    private Date playTime;
    private String ageRestriction;

    public void setTitle (String title){
        this.title = title;
    }

    public void setGenre ( String genre){
        this.genre = genre;
    }

    public void setReleaseDate(Date releaseDate){
        this.releaseDate = releaseDate;
    }

    public void setPlayTime(Date playTime){
        this.playTime = playTime;
    }

    public void setAgeRestriction(String ageRestriction){
        this.ageRestriction = ageRestriction;
    }

    public String getTitle(){
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Date getReleaseDate(){
        return releaseDate;
    }

    public Date getPlayTime(){
        return playTime;
    }

    public String getAgeRestriction(){
        return ageRestriction;
    }


}
