package com.phoenix.models;

import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.webmvc.formatters.LocalDateFormatter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * Hibernate user details entity. This entity holds users person private information such as first name,
 * last name, birthday, sex. When new user person register in application this details
 * associate with new registered user.
 */
@SuppressWarnings("unused")
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

    //Constructors

    /**
     * Default constructor create new empty UserDetails entity.
     */
    public UserDetail() {}

    /**
     * Create new UserDetails entity with fields values defined in {@link RegistrationForm}.
     * @param form - Filled {@link RegistrationForm} form.
     */
    public UserDetail(RegistrationForm form) {

        //Map fields
        this.user_fname = form.getfName();
        this.user_lname = form.getlName();
        this.setUserBirthday(form.getBirthDay());
        this.setUserSex(form.getSex());

    }

    //Getters
    public int getDetailId() {        return detail_id;    }
    public User getDetailOwner() {        return detail_owner;    }
    public String getUserFname() {        return user_fname;    }
    public String getUserLname() {        return user_lname;    }
    public LocalDate getUserBirthday() {        return user_birthday;    }
    public int getUserAge() {        return user_age;    }
    public char getUserSex() {        return user_sex;    }

    //Setters
    public void setDetailId(int detail_id) {        this.detail_id = detail_id;    }
    public void setDetailOwner(User detail_owner) {        this.detail_owner = detail_owner;    }
    public void setUserFname(String user_fname) {        this.user_fname = user_fname;    }
    public void setUserLname(String user_lname) {        this.user_lname = user_lname;    }
    public void setUserBirthday(LocalDate user_birthday) {
        //Validate
        if (user_birthday == null || user_birthday.equals(LocalDateFormatter.INVALID)) {
            this.user_birthday = LocalDateFormatter.INVALID;
            return;
        }

        //Set birthday and calculate age
        this.user_birthday = user_birthday;
        this.user_age = Period.between(user_birthday, LocalDate.now()).getYears();

    }
    public void setUserSex(char user_sex) {        this.user_sex = user_sex;    }
}
