package com.phoenix.models.forms;

import com.phoenix.models.Account;
import com.phoenix.models.UserDetail;
import com.phoenix.webmvc.validation.annotations.Char;
import com.phoenix.webmvc.validation.annotations.Password;
import com.phoenix.webmvc.validation.annotations.ValidLocalDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class RegistrationForm {

    //Registration form inputs
    @Email(message = "{invalid.Account.email}")
    private String email;

    @Password(message = "{invalid.Account.password}")
    private String password;

    @NotEmpty(message = "{not_empty.Account.fname}")
    @Size(min = 2, max = 255, message = "{invalid.Account.fname}")
    private String fName;

    @NotEmpty(message = "{not_empty.Account.lname}")
    @Size(min = 2, max = 255, message = "{invalid.Account.lname}")
    private String lName;

    @NotNull(message = "{not_null.Account.birthday}")
    @Past(message = "{past.Account.birthday}")
    @ValidLocalDate(message = "{localdate.Account.birthday}")
    private LocalDate birthDay;

    @Char(available = {'f','m'})
    private char sex;

    public Account createAccount() {

        //Map fields
        Account account = new Account();
        account.setAccountEmail(this.email);
        account.setAccountPassword(this.password);

        //Reset password
        this.password = null;

        return account;
    }

    public UserDetail createUserDetail() {
        UserDetail detail = new UserDetail();
        detail.setUserFname(this.fName);
        detail.setUserLname(this.lName);
        detail.setUserBirthday(this.birthDay);
        detail.setUserSex(this.sex);
        return detail;
    }

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
