/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bankboston;

import java.util.LinkedList;
import java.util.Scanner;
import bankboston.model.Account;
import bankboston.model.CheckingAccount;
import bankboston.model.SavingAccount;
import bankboston.model.CreditAccount;
import bankboston.model.Person;


public class BankBoston {
    static Scanner scanner = new Scanner(System.in);
    static LinkedList<Account> accounts = new LinkedList<>();
   
    public static void main(String[] args) {        
        showMenu();     
    }

    static void showMenu() {
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
        String personRUT = getClientRut();
    
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
        
        System.out.println("Seleccione el tipo de cuenta");
        int accountType = getAccountType();
        
        System.out.println("Ingrese numero de cuenta corriente: ");
        int accountNumber = getNewValidAccountNumber();
        
        createAccountByType(accountType, accountNumber, person);

        System.out.println("Cliente registrado exitosamente!");
    };
    
    static void createAccountByType(int accountType, int accountNumber, Person person) {
         Account newAccount;
         switch (accountType) {
            case 1 -> newAccount = new CheckingAccount(accountNumber, person);
            case 2 -> newAccount = new SavingAccount(accountNumber, person);
            case 3 -> newAccount = new CreditAccount (accountNumber, person);
            default -> newAccount = new CheckingAccount(accountNumber, person);
        }
        accounts.add(newAccount);
    }
    
    
     static int getAccountType() {
       System.out.println("\n=== Seleccione el tipo de cuenta ===");
       System.out.println("1. Cuenta Corriente");
       System.out.println("2. Cuenta De Ahorro");
       System.out.println("3. Cuenta De Credito");
    
       int option = readInt();
       int accountType = 1;
       
       switch (option) {
                case 1 -> accountType = 1;
                case 2 -> accountType = 2;
                case 3 -> accountType = 3;
                default -> System.out.println("Opción no válida. Se asignará una Cuenta Corriente");
            }
    
       return accountType;
    };
    
   
    static void viewClientData() {
        System.out.println("Ingrese número de cuenta: ");
        int number = readInt();

        for (Account acc : accounts) {
            if (acc.getAccountNumber() == number) {
                acc.displayClientInfo();
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
                acc.withdraw(amount);
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
   
  
    static String getClientRut() {
         System.out.println("Ingrese RUT: ");
         String rut = scanner.nextLine();
         Boolean isValidRut = false;
         isValidRut = isValidRut(rut);
         if (!isValidRut) {
             System.out.println("Rut invalido. Inténtelo nuevamente");
             getClientRut();
         }
         return rut;
    };
    
    static Boolean isValidRut(String rut) {
        final int MIN_LENGTH = 11;
        final int MAX_LENGTH = 12;

        boolean isEmpty = stringInputIsEmpty(rut);
        boolean hasValidLength = hasValidNumberOfCharactersByLimits(rut.length(), MIN_LENGTH, MAX_LENGTH);
        boolean hasValidDV = isValidChileanRUT(rut);

        boolean isValid = !isEmpty && hasValidLength && hasValidDV;

        return isValid;
    }

    static Boolean stringInputIsEmpty(String input) {
        return input.isBlank();
    }
    
    static boolean isValidChileanRUT(String rut) {
        rut = rut.replace(".", "").replace("-", "").toUpperCase();

        if (rut.length() < 2) return false;

        String numberPart = rut.substring(0, rut.length() - 1);
        char givenDigitVerificator = rut.charAt(rut.length() - 1);

        try {
            int rutNumber = Integer.parseInt(numberPart);
            char expectedDigitVerificator = calculateRUTCheckDigit(rutNumber);

            return expectedDigitVerificator == givenDigitVerificator;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static char calculateRUTCheckDigit(int rut) {
        int sum = 0;
        int multiplier = 2;

        while (rut > 0) {
            int digit = rut % 10;
            sum += digit * multiplier;
            rut /= 10;
            multiplier = (multiplier == 7) ? 2 : multiplier + 1;
        }

        int remainder = 11 - (sum % 11);
        if (remainder == 11) return '0';
        if (remainder == 10) return 'K';
        return (char) (remainder + '0');
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
