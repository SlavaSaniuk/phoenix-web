package com.phoenix.repositories;

import com.phoenix.models.relation.users.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * Simple CRUD repository for User entity. Repository implement {@link CrudRepository} interface,
 * which contain simple CRUD operations. Spring automatically create implementation of this repository interface.
 * {@link AccountRepository} contains custom methods to retrieve {@link Account} entity from database.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.account_email = ?1")
    @Nullable Account findAccountByAccountEmail(@NonNull String account_email) throws IllegalArgumentException;
}
