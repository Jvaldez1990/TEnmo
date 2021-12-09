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
    public BigDecimal getBalance(Long id) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        BigDecimal balance = null;

        if (results.next()) {
            String accountBalance = results.getString("balance");
            balance = new BigDecimal(accountBalance);
        }
        return balance;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if (result.next()) {
            account = mapResultsToAccount(result);
        }

        return account;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if (result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public void updateAccount(Account accountToUpdate, int accountId) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, accountToUpdate.getBalance(), accountToUpdate.getAccountId());

    }

    private Account mapResultsToAccount(SqlRowSet result) {
        int accountId = result.getInt("account_id");
        int userAccountId = result.getInt("user_id");
        String accountBalance = result.getString("balance");

        return new Account(accountId, userAccountId, new BigDecimal(accountBalance));


    }
}
