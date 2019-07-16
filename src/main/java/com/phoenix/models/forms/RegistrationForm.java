package com.phoenix.models.forms;

import com.phoenix.webmvc.validation.annotations.Password;

import java.time.LocalDate;

public class RegistrationForm {

    //Registration form inputs
    private String fName;
    private String lName;
    private LocalDate birthDay;
    private char sex;
    private String email;
    @Password(message = "{invalid.Account.password}")
    private String password;

    //Getters
    public String getfName() {        return fName;    }
    public String getlName() {        return lName;    }
    public LocalDate getBirthDay() {        return birthDay;    }
    public char getSex() {        return sex;    }
    public String getEmail() {        return email;    }
    public String getPassword() {        return password;    }

    //Setters
    public void setfName(String fName) {        this.fName = fName;    }
    public void setlName(String lName) {        this.lName = lName;    }
    public void setBirthDay(LocalDate birthDay) {        this.birthDay = birthDay;    }
    public void setSex(char sex) {        this.sex = sex;    }
    public void setEmail(String email) {        this.email = email;    }
    public void setPassword(String password) {        this.password = password;    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", birthDay=" + birthDay +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
