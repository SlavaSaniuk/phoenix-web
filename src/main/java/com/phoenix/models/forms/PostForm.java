package com.phoenix.models.forms;

import com.phoenix.models.Post;

public class PostForm {

    private String postText;

    public Post createPost() {
        Post post = new Post();
        post.setPostText(this.postText);
        post.setPostDate();
        post.setPostTime();
        return post;
    }

    //Getters
    public String getPostText() {
        return postText;
    }

    //Setters
    public void setPostText(String postText) {
        this.postText = postText;
    }
}
