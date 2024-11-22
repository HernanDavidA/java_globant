package com.globant.persistence.database.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.globant.helpers.Status;
import com.globant.models.Client;
import com.globant.models.Machine;
import com.globant.models.Rental;
import com.globant.persistence.database.DatabaseConnection;

public class RentalDAO {

    String sql = "INSERT INTO rentals (client_id, machine_id, start_date, end_date, estado) VALUES (?, ?, ?, ?, ?);";

    public void addRental(Rental objRental) {

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement objPrepared = connection.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS) 
        ) {
       
            objPrepared.setObject(1, objRental.getClientId().getId());
            objPrepared.setObject(2, objRental.getMachineId().getId());
            objPrepared.setDate(3, Date.valueOf(objRental.getStartDate()));
            objPrepared.setDate(4, Date.valueOf(objRental.getEndDate()));
            objPrepared.setString(5, objRental.getStatus().toString());

            // Ejecutar la consulta
            objPrepared.executeUpdate();

            int id = 0;

            // Obtener la clave generada
            try (ResultSet objResultSet = objPrepared.getGeneratedKeys()) {
                if (objResultSet.next()) {
                    id = objResultSet.getInt(1); // Establecer el ID generado en el objeto machine
                }
            }

            System.out.println("Rental added with ID: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }   

    public void getAllRentals() {

        String sql = "SELECT * FROM rentals ;";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement objPrepared = connection.prepareStatement(sql);
                ResultSet objResultSet = objPrepared.executeQuery();) {
            while (objResultSet.next()) {
                int id = objResultSet.getInt("id");
                int clientId = objResultSet.getInt("client_id");
                int machineId = objResultSet.getInt("machine_id");
                String status = objResultSet.getString("status");
                System.out.println("ID: " + id + " Client ID: " + clientId + " Machine ID: " + machineId + " Status: " + status);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }   
     

    public void updateRental(Rental objRental) {

        if (objRental.getId() == 0  || objRental.getId() <= 0) {
            System.out.println("Error: El alquiler no tiene un ID válido.");
            return;
        }
    
        String sql = "UPDATE rentals SET end_date = ?, estado = ? WHERE id = ?";
    
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement objPrepared = connection.prepareStatement(sql)
        ) {
            objPrepared.setDate(1, Date.valueOf(objRental.getEndDate()));
            objPrepared.setString(2, objRental.getStatus().toString());
            objPrepared.setInt(3, objRental.getId());
    
            int rowsUpdated = objPrepared.executeUpdate();
    
            if (rowsUpdated > 0) {
                System.out.println("Alquiler actualizado correctamente.");
            } else {
                System.out.println("No se encontró el alquiler con ID: " + objRental.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el alquiler: " + e.getMessage());
        }
    }

public List<Rental> getActiveRentalByMachineId(Machine machine) {
        List<Rental> activeRentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals WHERE machine_id = ? AND estado = 'activo';";
    
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement objPrepared = connection.prepareStatement(sql)
        ) {
            objPrepared.setInt(1, machine.getId());
            try (ResultSet objResultSet = objPrepared.executeQuery()) {
                while (objResultSet.next()) {
                    int id = objResultSet.getInt("id");
                    int clientId = objResultSet.getInt("client_id");
                    String status = objResultSet.getString("estado");
                    LocalDate startDate = objResultSet.getDate("start_date").toLocalDate();
                    LocalDate endDate = objResultSet.getDate("end_date").toLocalDate();
    
                    Client client = new Client();
                    client.setId(clientId);
    
                    Rental rental = new Rental();
                    rental.setId(id);
                    rental.setClientId(client);
                    rental.setMachineId(machine);
                    rental.setStatus(Status.valueOf(status));
                    rental.setStartDate(startDate);
                    rental.setEndDate(endDate);
                    activeRentals.add(rental);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener alquileres activos: " + e.getMessage());
        }
    
        return activeRentals;
    }
}
