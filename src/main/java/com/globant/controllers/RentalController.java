package com.globant.controllers;

import java.time.LocalDate;
import java.util.List;

import com.globant.helpers.Status;
import com.globant.helpers.StatusMachine;
import com.globant.models.Client;
import com.globant.models.Machine;
import com.globant.models.Rental;
import com.globant.persistence.database.DAO.MachineDAO;
import com.globant.persistence.database.DAO.RentalDAO;

public class RentalController {

    private RentalDAO rentalDAO;
    private MachineDAO machineDAO;

    public RentalController(RentalDAO rentalDAO, MachineDAO machineDAO){
        this.rentalDAO = rentalDAO;
        this.machineDAO = machineDAO;
    }

    public void addRental(int clientId, int machineId, LocalDate startDate, LocalDate endDate){

    Client client = new Client();
    client.setId(clientId);

    Machine machine = new Machine();
    machine.setId(machineId);

    StatusMachine currentStatus = this.machineDAO.getMachineStatus(machineId);

    if (currentStatus == StatusMachine.Rented) {
        System.out.println("La máquina está rentada y no se puede reservar.");
        return; 
    }
    if (currentStatus == StatusMachine.Available) {
        machine.setStatus(StatusMachine.Rented);
        this.machineDAO.updateMachineStatus(machine);  
    }

 
    Rental objRental = new Rental();
    objRental.setClientId(client);
    objRental.setMachineId(machine);
    objRental.setStatus(Status.activo); 
    objRental.setStartDate(startDate);
    objRental.setEndDate(endDate);

 
    this.rentalDAO.addRental(objRental);

    System.out.println("Alquiler registrado con éxito.");
}


   public void finishRental( int machineId, LocalDate endDate) {


    Machine machine = new Machine();
    machine.setId(machineId);

    StatusMachine currentStatus = this.machineDAO.getMachineStatus(machineId);

    if (currentStatus == StatusMachine.Rented) {
        machine.setStatus(StatusMachine.Available);
        this.machineDAO.updateMachineStatus(machine);  

    
        List<Rental> activeRentals = this.rentalDAO.getActiveRentalByMachineId(machine);

        if (!activeRentals.isEmpty()) {
            Rental objRental = activeRentals.get(0); 

            objRental.setStatus(Status.desactivado);
            objRental.setEndDate(endDate);

            this.rentalDAO.updateRental(objRental);

            System.out.println("Alquiler finalizado con éxito.");
        } else {
            System.out.println("No hay alquiler activo con esta máquina.");
        }
    } else {
        System.out.println("La máquina no está alquilada.");
    }
}

    
}
