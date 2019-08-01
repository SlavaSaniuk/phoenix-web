package com.phoenix.models.forms;

import com.phoenix.models.Post;

public class PostForm {

    //Form fields
    private String postText;

    //Default constructor
    public PostForm() {

    };

    public Post createPost() {
        Post post = new Post();
        post.setPostDate();
        post.setPostTime();
        return post;
    }

    //Getters
    public String getPostText() {
        return this.postText;
    }

    //Setters
    public void setPostText(String a_text) {
        this.postText = a_text;
    }


}
