package com.phoenix.repositories;

import com.phoenix.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.account_email = ?1")
    @Nullable Account findAccountByAccountEmail(@NonNull String account_email) throws IllegalArgumentException;
}
