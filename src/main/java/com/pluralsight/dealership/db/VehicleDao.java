package com.pluralsight.dealership.db;

import com.pluralsight.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        String sqlAddQuery = "INSERT INTO car_dealership.vehicles" +
                " (vin, make, model, year, SOLD, color, vehicleType, odometer, price)" +
                " Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sqlAddQuery)){
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setBoolean(5, vehicle.isSold());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setString(7, vehicle.getVehicleType());
            preparedStatement.setInt(8, vehicle.getOdometer());
            preparedStatement.setDouble(9, vehicle.getPrice());
            preparedStatement.executeUpdate();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeVehicle(String VIN) {
        String deleteCar = "DELETE FROM vehicles WHERE VIN = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteCar)){
            deleteStatement.setString(1, VIN);
            deleteStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String searchByPrice = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(searchByPrice);){
            selectStatement.setDouble(1, minPrice);
            selectStatement.setDouble(2, maxPrice);
            try(ResultSet resultSet = selectStatement.executeQuery()){
                while (resultSet.next()){
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String searchByPrice = "SELECT * FROM vehicles WHERE make = ?  AND model = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(searchByPrice);){
            selectStatement.setString(1, make);
            selectStatement.setString(2, model);
            try(ResultSet resultSet = selectStatement.executeQuery()){
                while (resultSet.next()){
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String searchByPrice = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(searchByPrice);) {
            selectStatement.setInt(1, minYear);
            selectStatement.setInt(2, maxYear);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String searchByPrice = "SELECT * FROM vehicles WHERE color = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(searchByPrice);){
            selectStatement.setString(1, color);
            try(ResultSet resultSet = selectStatement.executeQuery()){
                while (resultSet.next()){
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        String searchByPrice = "SELECT * FROM vehicles WHERE mileage BETWEEN ? AND ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(searchByPrice);) {
            selectStatement.setInt(1, minMileage);
            selectStatement.setInt(2, maxMileage);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String searchByPrice = "SELECT * FROM vehicles WHERE type = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(searchByPrice);){
            selectStatement.setString(1, type);
            try(ResultSet resultSet = selectStatement.executeQuery()){
                while (resultSet.next()){
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}

