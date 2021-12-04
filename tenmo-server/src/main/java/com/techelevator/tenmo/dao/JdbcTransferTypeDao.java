package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferType getTransferTypeByTransferTypeId(int transferTypeId) {
        TransferType transferType = null;
        String sql = "SELECT * FROM transfer_types " +
//                "JOIN transfers ON transfers.transfer_type_id = transfer_statuses.transfer_type_id " +
                "WHERE transfer_type_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferTypeId);
        if (result.next()) {
            transferType = mapResultsToTransferType(result);
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeFromDescription(String desc) {
        TransferType transferType = null;
        String sql = "SELECT * FROM transfer_types WHERE transfer_type_desc = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, desc);
        if (result.next()) {
            transferType = mapResultsToTransferType(result);
        }
        return transferType;
    }

//    @Override
//    public List<TransferType> getAllTransferTypes() {
//        String sql = "SELECT * FROM transfer_types";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//        List<TransferType> transferTypes =  new ArrayList<>();
//
//        while (results.next()) {
//            transferTypes.add(mapResultsToTransferType(results));
//        }
//        return transferTypes;
//    }
//
//    @Override
//    public void createTransferTypes(TransferType transferType) {
//        String sql = "INSERT INTO transfers (transfer_id, transfer_type_desc) VALUES (?, ?)";
//        jdbcTemplate.update(sql, transferType.getTransfer_type_id(), transferType.getTransfer_type_desc());
//    }

    private TransferType mapResultsToTransferType(SqlRowSet result) {
        int transfer_type_id = result.getInt("transfer_type_id");
        String transfer_type_desc = result.getString("transfer_type_desc");

        TransferType transferType = new TransferType(transfer_type_id, transfer_type_desc);
        return transferType;
    }

}
