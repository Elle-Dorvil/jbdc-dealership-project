package com.pluralsight.dealership.db;

import com.pluralsight.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
        String sqlAddQuery = "INSERT INTO lease_contracts" +
                " (VIN, lease_start, lease_end, monthly_payment)" +
                " Values (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAddQuery, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, leaseContract.getVin());
            preparedStatement.setDate(2, java.sql.Date.valueOf(leaseContract.getLeaseStart()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(leaseContract.getLeaseEnd()));
            preparedStatement.setDouble(4, leaseContract.getMonthlyPayment());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Creating lease contract failed, no rows affected");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    int generateId = generatedKeys.getInt(1);
                    leaseContract.setContractId(generateId);
                } else {
                    throw new SQLException("Creating lease contract, no id obtained");
                }
            }

            }catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
