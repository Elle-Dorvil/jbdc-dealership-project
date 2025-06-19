package com.pluralsight.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        String sqlAddQuery = "INSERT INTO car_dealership.inventory" +
                " (VIN, dealershipId)" +
                " Values (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAddQuery)){
            preparedStatement.setInt(1, dealershipId);
            preparedStatement.setString(2, vin);
            preparedStatement.executeUpdate();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeVehicleFromInventory(String vin) {
        try(Connection connection = dataSource.getConnection()){
            try(
                    PreparedStatement removeSales = connection.prepareStatement("DELETE FROM sales_contracts WHERE VIN = ?");
                    PreparedStatement removeLease = connection.prepareStatement("DELETE FROM lease_contracts WHERE VIN = ?");
                    PreparedStatement removeInventory = connection.prepareStatement("DELETE FROM inventory WHERE VIN = ?");
                    PreparedStatement removeVehicles = connection.prepareStatement("DELETE FROM vehicles WHERE VIN = ?");
                    ) {
                removeSales.setString(1, vin);
                removeSales.executeUpdate();
                removeLease.setString(1, vin);
                removeLease.executeUpdate();
                removeInventory.setString(1, vin);
                removeInventory.executeUpdate();
                removeVehicles.setString(1, vin);
                removeVehicles.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
