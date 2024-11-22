
package com.globant.persistence.database.DAO;

import com.globant.models.Client;
import com.globant.persistence.database.DatabaseConnection;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientDAO {
    public void addClient(Client objClient) {
        String sql = "INSERT INTO clients (name, email, phone, address) VALUES (?, ?, ?, ?);";
    
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement objPrepared = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS) // Especificamos que queremos las claves generadas
        ) {
            // Configurar los parámetros de la consulta
            objPrepared.setString(1, objClient.getName());
            objPrepared.setString(2, objClient.getEmail());
            objPrepared.setString(3, objClient.getPhone());
            objPrepared.setString(4, objClient.getAddress());
    
            // Ejecutar la consulta
            objPrepared.executeUpdate();
    
            // Obtener la clave generada
            try (ResultSet objResultSet = objPrepared.getGeneratedKeys()) {
                if (objResultSet.next()) {
                    objClient.setId(objResultSet.getInt(1)); // Establecer el ID generado en el objeto cliente
                }
            }
    
            System.out.println("Client added with ID: " + objClient.getId());
        } catch (SQLException e) {
            System.out.println("Error >>> " + e.getMessage());
        }
    }
    

    public List<Client> getAllClients() {

        String sql = "SELECT * FROM clients;";

        List<Client> listClients = new java.util.ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement objPrepared = connection.createStatement();
                ResultSet objResultSet = objPrepared.executeQuery(sql);) {

            while (objResultSet.next()) {
                Client objClient = new Client();
                objClient.setId(objResultSet.getInt("id"));
                objClient.setName(objResultSet.getString("name"));
                objClient.setEmail(objResultSet.getString("email"));
                objClient.setPhone(objResultSet.getString("phone"));
                objClient.setAddress(objResultSet.getString("address"));
                listClients.add(objClient);
            }

        } catch (SQLException e) {
            System.out.println("Error >>> " + e.getMessage());
        }
        return listClients;
    }
}
