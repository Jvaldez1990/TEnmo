package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferStatus getTransferStatusById(int transferStatusId) {
        TransferStatus transferStatus = null;
        String sql = "SELECT * FROM transfer_statuses " +
                "WHERE transfer_status_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferStatusId);
        if (result.next()) {
            transferStatus = mapResultsToTransferStatus(result);
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusByDesc(String description) {
        String sql = "SELECT * FROM transfer_statuses WHERE transfer_status_desc = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, description);
        TransferStatus transferStatus = null;
        if (result.next()) {
            transferStatus = mapResultsToTransferStatus(result);
        }
        return transferStatus;
    }


    private TransferStatus mapResultsToTransferStatus(SqlRowSet result) {
        int transfer_status_id = result.getInt("transfer_status_id");
        String transfer_status_desc = result.getString("transfer_status_desc");

        return new TransferStatus(transfer_status_id, transfer_status_desc);
    }
}
