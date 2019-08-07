package com.phoenix.models.forms;

import com.phoenix.models.Post;
import java.time.LocalDate;
import java.time.LocalTime;

public class PostForm {

    private String postText;
    private LocalDate postDate;
    private LocalTime postTime;

    //Default constructor
    public PostForm() {    }

    public PostForm(Post post) {
        super();
        this.postText = post.getPostText();
        this.postDate = post.getPostDate();
        this.postTime = post.getPostTime();
    }

    public Post createPost() {
        Post post = new Post();
        post.setPostText(this.postText);
        post.setPostDate();
        post.setPostTime();
        return post;
    }

    /**
     * Method return time and date fields as string
     * @return - "HH:mm DD-MM-YYYY"
     */
    public String getFullTime() {
        return postTime.getHour() +":" +postTime.getMinute() +" " +postDate.toString();
    }

    //Getters
    public String getPostText() {
        return postText;
    }
    public LocalDate getPostDate() {        return postDate;    }
    public LocalTime getPostTime() {        return postTime;    }

    //Setters
    public void setPostText(String postText) {
        this.postText = postText;
    }
}
