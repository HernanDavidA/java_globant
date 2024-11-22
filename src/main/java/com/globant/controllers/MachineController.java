package com.globant.controllers;

import java.util.List;

import com.globant.helpers.StatusMachine;
import com.globant.models.Machine;
import com.globant.persistence.database.DAO.MachineDAO;

public class MachineController {

    private MachineDAO machineDAO;

    public MachineController(){
        this.machineDAO = new MachineDAO();
    }

    public void addMachine(String model, String serialNumber, StatusMachine status){
        Machine objMachine = new Machine();
        objMachine.setModel(model);
        objMachine.setserialNumber(serialNumber);
        objMachine.setStatus(status);
        this.machineDAO.addMachine(objMachine);
    }

    public void getAllMachines(int offset){
        List<Machine> machines = this.machineDAO.getAllMachines(offset);

        if(machines.isEmpty()){
            System.out.println("No hay máquinas a partir del offset " + offset);
        }
        else{
            System.out.println("Máquinas a partir del offset " + offset);
            for(Machine objMachine : machines){
                System.out.println("ID: " + objMachine.getId() + ", Model: " + objMachine.getModel() + ", Status: " + objMachine.getStatus() + ", Serial Number: " + objMachine.getserialNumber());
            }
        }
    }

}
