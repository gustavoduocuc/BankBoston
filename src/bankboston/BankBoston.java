/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bankboston;

import java.util.LinkedList;
import java.util.Scanner;
import bankboston.model.Account;
import bankboston.model.Person;


public class BankBoston {
    static Scanner scanner = new Scanner(System.in);
    static LinkedList<Account> accounts = new LinkedList<>();
   
    public static void main(String[] args) {        
         boolean running = true;
            while (running) {
            System.out.println("\n=== Banco De Boston ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver datos de cliente");
            System.out.println("3. Depositar");
            System.out.println("4. Girar");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int option = readInt();

            switch (option) {
                case 1 -> registerClient();
                case 2 -> viewClientData();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> viewBalance();
                case 6 -> {
                    System.out.println("Gracias por su visita.");
                    running = false;
                }
                default -> System.out.println("Opción no válida.");
            }
        }        
    }
    
    static void registerClient() {
        System.out.println("\n=== Ingrese los datos del cliente ===");
        String personRUT = getClientRUT();
        System.out.println("Ingrese Nombre: ");
        String name = readString();
        System.out.println("Ingrese apellido paterno: ");
        String paternalSurname = readString();
        System.out.println("Ingrese apellido materno: ");
        String maternalSurname = readString();
        System.out.println("Ingrese domicilio: ");
        String address = readString();
        System.out.println("Ingrese comuna: ");
        String municipality = readString();
        System.out.println("Ingrese teléfono: ");
        int phoneNumber = readInt();
        Person person = new Person(personRUT, name, maternalSurname, paternalSurname, address, municipality, phoneNumber);
        System.out.println("Ingrese numero de cuenta corriente: ");
        int accountNumber = getNewValidAccountNumber();
        double initialBalance = 0.0;
        Account newAccount = new Account(accountNumber, initialBalance, person);
        accounts.add(newAccount);
        System.out.println("Cliente registrado exitosamente!");
    };
    
    static void viewClientData() {
        System.out.println("Ingrese número de cuenta: ");
        int number = readInt();

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == number) {
                acc.getClientData();
                return;
            }
        }

        System.out.println("No se encontró una cuenta con ese número.");
    }
    
    static void deposit() {
        System.out.println("Ingrese número de cuenta: ");
        int number = readInt();

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == number) {
                System.out.println("Ingrese monto a depositar: ");
                double amount = readInt();
                acc.deposit(amount);
                return;
            }
        }

        System.out.println("No se encontró una cuenta con ese número.");
    }
    
   static void withdraw() {
        System.out.println("Ingrese número de cuenta: ");
        int number = readInt();

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == number) {
                System.out.println("Ingrese monto a girar: ");
                double amount = readInt();
                acc.withdrawMoney(amount);
                return;
            }
        }

        System.out.println("No se encontró una cuenta con ese número.");
    }
    
    static void viewBalance() {
        System.out.println("Ingrese número de cuenta: ");
        int number = readInt();

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == number) {
                acc.checkBalance();
                return;
            }
        }

        System.out.println("No se encontró una cuenta con ese número.");
    }
   
  
    
    static String getClientRUT() {
        int MIN_CHAR_LIMIT = 11;
        int MAX_CHAR_LIMIT = 12;
        Boolean isValidRUT = false;
        String validRUT = "";
  
        System.out.println("RUT: ");
        while (isValidRUT == false) {
             String rawInputRUT = scanner.nextLine();
             isValidRUT = hasValidNumberOfCharactersByLimits(rawInputRUT.length(), MIN_CHAR_LIMIT, MAX_CHAR_LIMIT);
             if (isValidRUT == false) {
                 System.out.println("RUT Invalido. Debe tener entre 11 y 12 digitos. Intente otra vez: ");
             } else {
                validRUT = rawInputRUT;
             }
        }
        return validRUT;
    }
    
    static int getNewValidAccountNumber() {
        int accountNumber = 0;
        int MAX_CHAR_LIMIT = 9;
        Boolean isValidAccount = false;
      
        System.out.println("Ingrese numero de cuenta corriente (9 carateres): ");
        while (isValidAccount == false)  {
           int rawAccountNumber = readInt();
           int characterCount = String.valueOf(rawAccountNumber).length();
           Boolean hasCorrectNumberOfCharacters = hasValidNumberOfCharactersByMinimumLimit(characterCount, MAX_CHAR_LIMIT);
           if (hasCorrectNumberOfCharacters && accountNumberIsAvailable(rawAccountNumber)) {
               isValidAccount = true;
               accountNumber = rawAccountNumber;
           } else {
               System.out.println("Numero de cuenta corriente existente o invalido. Intentelo otra vez:");
           }
        }
        return accountNumber;
    };
    
    static boolean accountNumberIsAvailable(int newAccountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == newAccountNumber) {
                return false;
            }
        }
        return true;
    }
    
    static boolean hasValidNumberOfCharactersByLimits(int characters, int minimum, int maximum) {
        return hasValidNumberOfCharactersByMaximumLimit(characters, maximum) && hasValidNumberOfCharactersByMinimumLimit(characters, minimum);
    }
    static boolean hasValidNumberOfCharactersByMaximumLimit(int characters, int limit) {
        return characters <= limit;
    }
    static boolean hasValidNumberOfCharactersByMinimumLimit(int characters, int minimum) {
        return characters >= minimum;
    }
    
    static String readString() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                Double.parseDouble(input);
                System.out.print("Entrada inválida. No se permiten números. Intente otra vez: ");
            } catch (NumberFormatException e) {
                return input;
            }
        }
    }
    
    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Entrada inválida, debe ingresar solo numeros. Intente otra vez: ");
            }
        }
    }
    
}
