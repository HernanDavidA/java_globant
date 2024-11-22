package com.globant.models;

import java.time.LocalDate;

import com.globant.helpers.Status;

public class Rental {
    private int id;
    private Client clientId;
    private Machine machineId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;

    public Rental() {
    }

    public Rental(int id, Client clientId, Machine machineId, LocalDate startDate, LocalDate endDate, Status status) {
        this.id = id;
        this.clientId = clientId;
        this.machineId = machineId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Machine getMachineId() {
        return machineId;
    }

    public void setMachineId(Machine machineId) {
        this.machineId = machineId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Status getStatus() {
        return status;
    }   

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", machineId=" + machineId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status " + status + 
                '}';
    }
}
