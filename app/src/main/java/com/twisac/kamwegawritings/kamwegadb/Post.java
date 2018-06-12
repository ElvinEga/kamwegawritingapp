package com.twisac.kamwegawritings.kamwegadb;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by egafic on 2/24/2016.
 */

@Table(name = "ChildData")
public class Post extends Model {
    @Column(name = "title")
    private String title;
    @Column(name = "feature")
    private String feature;
    @Column(name = "excerpt")
    private String excerpt;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private String date;
    @Column(name = "link")
    private String link;
    @Column(name = "category")
    private String category;

    public  Post(){

    }
    public Post(String title,String feature,String excerpt,String content,String date,String link,String category){
        this.title=title;
        this.feature=feature;
        this.excerpt=excerpt;
        this.content=content;
        this.date=date;
        this.link=link;
        this.category=category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
