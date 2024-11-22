package com.globant.models;

import com.globant.helpers.StatusMachine;

public class Machine {
    private int id;
    private String model;

    private String serialNumber;
    private StatusMachine status;

    public Machine() {
    }

    public Machine(int id, String model, String serialNumber, StatusMachine status) {
        this.id = id;
        this.model = model;
        this.serialNumber = serialNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getserialNumber() {
        return serialNumber;
    }

    public void setserialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public StatusMachine getStatus() {
        return status;
    }

    public void setStatus(StatusMachine status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
