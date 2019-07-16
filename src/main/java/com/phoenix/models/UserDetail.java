package com.phoenix.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "UserDetail")
@Table(name = "user_detail")
public class UserDetail {

    @Id
    private int detail_id;

    @OneToOne(optional = false)
    @JoinColumn(name = "detail_id", nullable = false, unique = true,updatable = false)
    @MapsId
    private User detail_owner;

    @Column(name = "detail_fname", nullable = false)
    private String user_fname;

    @Column(name = "detail_lname", nullable = false)
    private String user_lname;

    @Column(name = "detail_birth_date", nullable = false)
    private LocalDate user_birthday;

    @Column(name = "detail_age")
    private int user_age;

    @Column(name = "detail_sex")
    private char user_sex;

}
