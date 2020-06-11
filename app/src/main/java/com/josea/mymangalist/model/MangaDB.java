package com.josea.mymangalist.model;

import android.provider.BaseColumns;

import java.util.Date;

public class MangaDB {
    public static abstract class MangaEntry implements BaseColumns {
        public static final String TABLE_NAME ="manga";

        public static final String ID = "id";
        public static final String IMAGE_URL = "image_url";
        public static final String TITLE = "title";
        public static final String PUBLISHING = "publishing";
        public static final String SYNOPSIS = "synopsis";
        public static final String CHAPTERS = "chapters";
        public static final String RANK = "rank";
        public static final String SCORE = "score";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String VOLUMES = "volumes";

    }

    private int id;
    private String image_url;
    private String  title;
    private boolean publishing;
    private String synopsis;
    private int chapters;
    private int rank;
    private float score;
    private Date start_date;
    private Date end_date;

    public MangaDB(int id, String image_url, String title, boolean publishing, String synopsis,
                   int chapters, int rank, float score, Date start_date, Date end_date) {
        this.id = id;
        this.image_url = image_url;
        this.title = title;
        this.publishing = publishing;
        this.synopsis = synopsis;
        this.chapters = chapters;
        this.rank = rank;
        this.score = score;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPublishing() {
        return publishing;
    }

    public void setPublishing(boolean publishing) {
        this.publishing = publishing;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
