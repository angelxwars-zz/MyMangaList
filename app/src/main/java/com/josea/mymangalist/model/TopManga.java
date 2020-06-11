package com.josea.mymangalist.model;


public class TopManga {

    private int id;
    private int rank;
    private float score;
    private String title;
    private String image_url;
    private String start_date;
    private String end_date;
    private int volumes;



    public TopManga(int id, int rank, float score, String title, String image_url, String start_date, String end_date,
                    int volumes) {
        this.id = id;
        this.rank = rank;
        this.score = score;

        this.title = title;
        this.image_url = image_url;
        this.start_date = start_date;
        this.end_date = end_date;
        this.volumes = volumes;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getVolumes() {
        return volumes;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
