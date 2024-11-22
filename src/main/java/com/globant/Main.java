package com.globant;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.globant.controllers.ClientController;
import com.globant.controllers.MachineController;
import com.globant.controllers.RentalController;
import com.globant.helpers.StatusMachine;
import com.globant.models.Client;

import com.globant.persistence.database.DAO.MachineDAO;
import com.globant.persistence.database.DAO.RentalDAO;

import java.util.List;
import java.util.Scanner;;


public class Main {
    public static void main(String[] args) {

    RentalDAO rentalDAO = new RentalDAO();
    MachineDAO machineDAO = new MachineDAO();

    MachineController objMachineController = new MachineController();
    ClientController objClientController = new ClientController();
    RentalController objRentalController = new RentalController(rentalDAO, machineDAO);

    try (Scanner sc = new Scanner(System.in)) {
        while (true) {
            try {
                System.out.println("""
                    Enter an option:
                    1. Clients
                    2. Machines
                    3. Rentals
                    4. Exit
                """);

                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1 -> handleClientsMenu(sc, objClientController);
                    case 2 -> handleMachinesMenu(sc, objMachineController);
                    case 3 -> handleRentalsMenu(sc, objRentalController);
                    case 4 -> {
                        System.out.println("Exiting the application...");
                        return; // Exit the application
                    }
                    default -> System.out.println("Invalid option, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}

private static void handleClientsMenu(Scanner sc, ClientController objClientController) {
    System.out.println("""
        Enter a client option:
        1. Add
        2. List
        3. Exit
    """);
    int clientOption = Integer.parseInt(sc.nextLine());
    switch (clientOption) {
        case 1 -> {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter phone: ");
            String phone = sc.nextLine();
            System.out.print("Enter address: ");
            String address = sc.nextLine();
            objClientController.addClient(name, email, phone, address);
            System.out.println("Client added successfully.");
        }
        case 2 -> {
            List<Client> clients = objClientController.getAllClients();
            clients.forEach(System.out::println);
        }
        case 3 -> System.out.println("Returning to the main menu...");
        default -> System.out.println("Invalid option.");
    }
}

private static void handleMachinesMenu(Scanner sc, MachineController objMachineController) {
    System.out.println("""
        Enter a machine option:
        1. Add
        2. List
        3. Exit
    """);
    int machineOption = Integer.parseInt(sc.nextLine());
    switch (machineOption) {
        case 1 -> {
            System.out.print("Enter model: ");
            String model = sc.nextLine();
            System.out.print("Enter serial number: ");
            String serialNumber = sc.nextLine();
            objMachineController.addMachine(model, serialNumber, StatusMachine.Available);
            System.out.println("Machine added successfully.");
        }
        case 2 -> {
            System.out.print("Enter offset: ");
            int offset = Integer.parseInt(sc.nextLine());
            objMachineController.getAllMachines(offset);
        }
        case 3 -> System.out.println("Returning to the main menu...");
        default -> System.out.println("Invalid option.");
    }
}

private static void handleRentalsMenu(Scanner sc, RentalController objRentalController) {
    System.out.println("""
        Enter a rental option:
        1. Make a rental
        2. Finish
        3. Exit
    """);
    int rentalOption = Integer.parseInt(sc.nextLine());
    switch (rentalOption) {
        case 1 -> {
            try {
                System.out.print("Enter client ID: ");
                int clientId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter machine ID: ");
                int machineId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter start date (YYYY-MM-DD): ");
                LocalDate startDate = LocalDate.parse(sc.nextLine());
                System.out.print("Enter end date (YYYY-MM-DD): ");
                LocalDate endDate = LocalDate.parse(sc.nextLine());
                objRentalController.addRental(clientId, machineId, startDate, endDate);
                System.out.println("Rental added successfully.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        case 2 -> {
            try {
                System.out.print("Enter machine ID: ");
                int machineId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter end date (YYYY-MM-DD): ");
                LocalDate endDate = LocalDate.parse(sc.nextLine());
                objRentalController.finishRental(machineId, endDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        case 3 -> System.out.println("Returning to the main menu...");
        default -> System.out.println("Invalid option.");
    }
}

}
