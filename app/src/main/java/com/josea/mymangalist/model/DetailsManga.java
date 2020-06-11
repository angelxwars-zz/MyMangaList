package com.josea.mymangalist.model;

import android.content.ContentValues;
import com.josea.mymangalist.model.MangaDB.MangaEntry;

import java.util.Date;

public class DetailsManga {

    private int id;
    private String image_url;
    private String title;
    private boolean publishing;
    private String synopsis;
    private int chapters;
    private int volumes;
    private float score;
    private Date start_date;
    private Date end_date;


    public DetailsManga(int id, String image_url, String title, boolean publishing, String synopsis, int chapters,
                        int volumes, float score, Date start_date, Date end_date){
        this.id = id;
        this.image_url = image_url;
        this.title = title;
        this.publishing = publishing;
        this.synopsis = synopsis;
        this.chapters = chapters;
        this.volumes = volumes;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public int getVolumes() {
        return volumes;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
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

    public ContentValues toContentValues(String rank) {
        ContentValues values = new ContentValues();
        values.put(MangaEntry.ID, id);
        values.put(MangaEntry.IMAGE_URL, getImage_url());
        values.put(MangaEntry.TITLE, getTitle());
        values.put(MangaEntry.PUBLISHING, isPublishing());
        values.put(MangaEntry.SYNOPSIS, getSynopsis());
        values.put(MangaEntry.CHAPTERS, getSynopsis());
        values.put(MangaEntry.VOLUMES, getSynopsis());
        values.put(MangaEntry.SCORE, getScore());
        values.put(MangaEntry.START_DATE, getStart_date()==null ? null : getStart_date().toString());
        values.put(MangaEntry.END_DATE, getEnd_date()==null ? null : getEnd_date().toString());
        values.put(MangaEntry.RANK, rank);

        return values;
    }
}
