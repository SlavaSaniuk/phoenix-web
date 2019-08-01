package com.phoenix.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Post entity. Used to save, retrieve text posts in/from database.
 */
@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_ id", nullable = false, unique = true)
    private int post_id;

    @ManyToOne
    @JoinColumn(name = "post_owner", referencedColumnName = "user_id", nullable = false)
    private User post_owner;

    @Column(name = "post_text", nullable = false)
    private String post_text;

    @Column(name = "post_date", nullable = false)
    private LocalDate post_date;

    @Column(name = "post_time", nullable = false)
    private LocalTime post_time;

    //Default constructor
    public Post() {}

    //Getters
    public int getPostId() {        return post_id;    }
    public User getPostOwner() {        return post_owner;    }
    public String getPostText() {        return post_text;    }
    public LocalDate getPostDate() {        return post_date;    }
    public LocalTime getPostTime() {        return post_time;    }

    //Setters
    public void setPostId(int post_id) {        this.post_id = post_id;    }
    public void setPostOwner(User post_owner) {        this.post_owner = post_owner;    }
    public void setPostText(String post_text) {        this.post_text = post_text;    }
    public void setPostDate() {
        this.post_date = LocalDate.now();
    }
    public void setPostTime() {
        this.post_time = LocalTime.now();
    }


}
