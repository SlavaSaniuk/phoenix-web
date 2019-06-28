package com.phoenix.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account", schema="phoenix")
public class Account {

    @Id
    @Column(name = "account_id", nullable = false)
    private int account_id;

    @Column(name = "account_email", nullable = false, unique = true)
    private String account_email;

    @Column(name = "account_password", nullable = false)
    private String account_password;
}
