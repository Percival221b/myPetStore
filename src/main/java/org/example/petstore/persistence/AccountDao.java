package org.example.petstore.persistence;

import org.example.petstore.domain.Account;

import java.sql.ResultSet;

public interface AccountDao {
    public Account getAccountByUsername(String username);
    public Account getAccountByUsernameAndPassword(String username, String password);
    public void updateAccount(Account account);
    public void insertAccount(Account account);
    public void updateProfile(Account account);
    public void insertProfile(Account account);
    public void updateSignon(Account account);
    public void insertSignon(Account account);
    public Account mapAccount(ResultSet rs);
}
