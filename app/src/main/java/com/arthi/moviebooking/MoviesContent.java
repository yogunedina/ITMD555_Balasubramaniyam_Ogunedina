package com.arthi.moviebooking;

import java.io.Serializable;

public class MoviesContent implements Serializable {
    //d82820e04f8c9e368c7b38e2fb0caf71 - key
    // image url = https://image.tmdb.org/t/p/w500/imagePath
    // trailer url =  https://www.youtube.com/watch?v=

    String poster_path;
    String original_title;
    String overview;
    String releasedate;
    String vote_average;
    String genres_ids;
    int movie_id;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    String video_url;
    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getGenres_ids() {
        return genres_ids;
    }

    public void setGenres_ids(String genres_ids) {
        this.genres_ids = genres_ids;
    }


}
