package bankboston;

import java.util.LinkedList;
import java.util.Scanner;
import bankboston.model.*;
import bankboston.util.InputValidator;

public class BankBoston {
    static Scanner scanner = new Scanner(System.in);
    static LinkedList<Account> accounts = new LinkedList<>();

    public static void main(String[] args) {
        mainMenuLoop();
    }

    static void mainMenuLoop() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int option = InputValidator.readValidInt(scanner);
            handleMenuOption(option);
            if (option == 6) running = false;
        }
    }

    static void displayMainMenu() {
        System.out.println("\n=== Banco De Boston ===");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Ver datos de cliente");
        System.out.println("3. Depositar");
        System.out.println("4. Girar");
        System.out.println("5. Consultar saldo");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    static void handleMenuOption(int option) {
        switch (option) {
            case 1 -> registerClient();
            case 2 -> viewClientData();
            case 3 -> deposit();
            case 4 -> withdraw();
            case 5 -> viewBalance();
            case 6 -> System.out.println("Gracias por su visita.");
            default -> System.out.println("Opción no válida.");
        }
    }

    static void registerClient() {
        System.out.println("\n=== Ingrese los datos del cliente ===");
        String personRUT = getClientRut();
        String name = readStringWithPrompt("Ingrese nombre: ");
        String paternal = readStringWithPrompt("Ingrese apellido paterno: ");
        String maternal = readStringWithPrompt("Ingrese apellido materno: ");
        String address = readStringWithPrompt("Ingrese domicilio: ");
        String municipality = readStringWithPrompt("Ingrese comuna: ");
        int phone = readIntWithPrompt("Ingrese teléfono: ");

        Person person = new Person(personRUT, name, maternal, paternal, address, municipality, phone);
        int accountType = getAccountType();
        int accountNumber = getNewValidAccountNumber();
        createAccountByType(accountType, accountNumber, person);

        System.out.println("Cliente registrado exitosamente!");
    }

    static String readStringWithPrompt(String prompt) {
        System.out.print(prompt);
        return InputValidator.readValidString(scanner);
    }

    static int readIntWithPrompt(String prompt) {
        System.out.print(prompt);
        return InputValidator.readValidInt(scanner);
    }

    static int getAccountType() {
      System.out.println("Seleccione el tipo de cuenta:");
      System.out.println("1. Cuenta Corriente");
      System.out.println("2. Cuenta de Ahorro");
      System.out.println("3. Cuenta de Crédito");

      while (true) {
          int option = InputValidator.readValidInt(scanner);

          if (option >= 1 && option <= 3) {
              return option;
          }

          System.out.println("Opción inválida. Ingrese 1, 2 o 3 según el tipo de cuenta. Intente nuevamente:");
      }
    }

    static void createAccountByType(int accountType, int accountNumber, Person person) {
        Account account = switch (accountType) {
            case 2 -> new SavingAccount(accountNumber, person);
            case 3 -> new CreditAccount(accountNumber, person);
            default -> new CheckingAccount(accountNumber, person);
        };
        accounts.add(account);
    }

    static void viewClientData() {
        int number = readIntWithPrompt("Ingrese número de cuenta: ");
        findAccountAndExecute(number, Account::displayClientInfo);
    }

    static void deposit() {
        int number = readIntWithPrompt("Ingrese número de cuenta: ");
        findAccountAndExecute(number, acc -> {
            double amount = readIntWithPrompt("Ingrese monto a depositar: ");
            if (isValidAmount(amount)) acc.deposit(amount);
        });
    }

    static void withdraw() {
        int number = readIntWithPrompt("Ingrese número de cuenta: ");
        findAccountAndExecute(number, acc -> {
            double amount = readIntWithPrompt("Ingrese monto a girar: ");
            if (isValidAmount(amount)) acc.withdraw(amount);
        });
    }

    static void viewBalance() {
        int number = readIntWithPrompt("Ingrese número de cuenta: ");
        findAccountAndExecute(number, Account::checkBalance);
    }

    static void findAccountAndExecute(int accountNumber, java.util.function.Consumer<Account> action) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                action.accept(acc);
                return;
            }
        }
        System.out.println("No se encontró una cuenta con ese número.");
    }

    static boolean isValidAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Monto inválido. Debe ser mayor que cero.");
            return false;
        }
        return true;
    }

    static String getClientRut() {
        while (true) {
            System.out.print("Ingrese RUT (formato: 12.345.678-K): ");
            String rut = scanner.nextLine().trim();
            if (validateRutInput(rut)) return rut;
            System.out.println("RUT inválido. Formato esperado: 12.345.678-K. Intente nuevamente.");
        }
    }

    static boolean validateRutInput(String rut) {
        final int MIN_LENGTH = 11, MAX_LENGTH = 12;
        boolean isEmpty = rut.isBlank();
        boolean validLength = rut.length() >= MIN_LENGTH && rut.length() <= MAX_LENGTH;
        boolean validCheckDigit = InputValidator.isValidChileanRUT(rut);
        return !isEmpty && validLength && validCheckDigit;
    }

    static int getNewValidAccountNumber() {
       final int REQUIRED_LENGTH = 9;
       while (true) {
           int accountNumber = readIntWithPrompt("Ingrese número de cuenta (9 caracteres): ");
           String strNumber = String.valueOf(accountNumber);
           if (strNumber.length() == REQUIRED_LENGTH && accountNumberIsAvailable(accountNumber)) {
               return accountNumber;
           }
           System.out.println("Número de cuenta inválido o ya registrado. Debe contener exactamente 9 dígitos.");
       }
   }

    static boolean accountNumberIsAvailable(int number) {
        return accounts.stream().noneMatch(a -> a.getAccountNumber() == number);
    }
}