package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BigDecimal getBalance(String user) {
        String sql = "SELECT balance FROM accounts JOIN users ON accounts.user_id WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);
        Account account = null;

        if (results.next()) {
            String accountBalance = results.getString("balance");
            account.setBalance(new BigDecimal(accountBalance));
        }
        return account.getBalance();
    }

    @Override
    public Account getAccountByUserId(int userId) {
        return null;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        return null;
    }

    @Override
    public void updateAccount(Account accountToUpdate) {

    }

    private Account mapResultsToAccount(SqlRowSet result) {
        int accountId = result.getInt("account_id");
        int userAccountId = result.getInt("user_id");
        String accountBalance = result.getString("balance");

        return new Account(accountId, userAccountId, new BigDecimal(accountBalance));


    }
}
