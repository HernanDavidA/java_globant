package com.globant.controllers;

import java.util.List;

import com.globant.models.Client;
import com.globant.persistence.database.DAO.ClientDAO;

public class ClientController {

    private ClientDAO clientDAO;

    public ClientController(){
        this.clientDAO = new ClientDAO();
    }

    public void addClient(String name, String email, String phone, String address){
        Client objClient = new Client();
        objClient.setName(name);
        objClient.setEmail(email);
        objClient.setPhone(phone);
        objClient.setAddress(address);
        this.clientDAO.addClient(objClient);
    }
    public List<Client> getAllClients(){
        return this.clientDAO.getAllClients();
    }

}
