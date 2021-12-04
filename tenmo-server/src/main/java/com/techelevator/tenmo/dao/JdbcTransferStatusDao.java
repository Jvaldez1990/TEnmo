package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferStatus getTransferStatusByTransferStatusId(int transfer_status_id) {
        TransferStatus transferStatus = null;
        String sql = "SELECT * FROM transfer_statuses " +
                "JOIN transfers ON transfers.transfer_status_id = transfer_statuses.transfer_status_id " +
                "WHERE transfer_status_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_status_id);
        if (results.next()) {
            transferStatus = mapResultsToTransferStatus(results);
        }

        return transferStatus;
    }

    @Override
    public List<TransferStatus> getAllTransferStatuses() {
        String sql = "SELECT * FROM transfer_statuses";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        List<TransferStatus> transfers =  new ArrayList<>();

        while (results.next()) {
            transfers.add(mapResultsToTransferStatus(results));
        }
        return transfers;
    }

    @Override
    public void createTransferStatus(TransferStatus transferStatus) {
        String sql = "INSERT INTO transfers (transfer_id, transfer_status_desc) VALUES (?, ?)";
        jdbcTemplate.update(sql, transferStatus.getTransfer_status_id(), transferStatus.getTransfer_status_desc());

    }


    private TransferStatus mapResultsToTransferStatus(SqlRowSet result) {
        int transfer_status_id = result.getInt("transfer_status_id");
        String transfer_status_desc = result.getString("transfer_status_desc");

        TransferStatus transferStatus = new TransferStatus(transfer_status_id, transfer_status_desc);
        return transferStatus;
    }
}
