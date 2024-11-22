package com.globant.persistence.database.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.globant.helpers.Status;
import com.globant.helpers.StatusMachine;
import com.globant.models.Client;
import com.globant.models.Machine;
import com.globant.models.Rental;
import com.globant.persistence.database.DatabaseConnection;

public class MachineDAO {

    public void addMachine(Machine objMachine) {

        String sql = "INSERT INTO machines (model, serial_number, status) VALUES (?, ?, ?);";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement objPrepared = connection.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS) 
        ) {
           
            objPrepared.setString(1, objMachine.getModel());
            objPrepared.setString(2, objMachine.getserialNumber());
            objPrepared.setString(3, objMachine.getStatus().toString());

            objPrepared.executeUpdate();

      
            try (ResultSet objResultSet = objPrepared.getGeneratedKeys()) {
                if (objResultSet.next()) {
                    objMachine.setId(objResultSet.getInt(1)); 
                }
            }

            System.out.println("Machine added with ID: " + objMachine.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Machine> getAllMachines(int offset) {
        List<Machine> listMachines = new ArrayList<>();
        int limit = 5;

        String sql = "SELECT * FROM machines LIMIT ? OFFSET ?;";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement objPrepared = connection.prepareStatement(sql);
                ) {

                    objPrepared.setInt(1, limit);
                    objPrepared.setInt(2, offset);

                    try (ResultSet objResultSet = objPrepared.executeQuery()) {
                        while (objResultSet.next()) {
                            Machine objMachine = new Machine();
                            objMachine.setId(objResultSet.getInt("id"));
                            objMachine.setModel(objResultSet.getString("model"));
                            objMachine.setserialNumber(objResultSet.getString("serial_number"));
                            objMachine.setStatus(StatusMachine.valueOf(objResultSet.getString("status")));
                            listMachines.add(objMachine);
                        }
                    }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return listMachines;
    }

    public void updateMachineStatus(Machine machine) {
        String sql = "UPDATE machines SET status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement objPrepared = connection.prepareStatement(sql)) {
            objPrepared.setString(1, machine.getStatus().toString());
            objPrepared.setInt(2, machine.getId());
            objPrepared.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error actualizando estado de la máquina: " + e.getMessage());
        }
    }

    public StatusMachine getMachineStatus(int machineId) {
        String sql = "SELECT status FROM machines WHERE id = ?";
        StatusMachine status = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, machineId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String statusString = rs.getString("status");
                    status = StatusMachine.valueOf(statusString);  // Convertir el string a StatusMachine enum
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el estado de la máquina: " + e.getMessage());
        }
        return status;
    }

    
    
}