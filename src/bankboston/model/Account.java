package bankboston.model;

public class Account {
    private int accountNumber;
    private double balance;
    private Person person;

    public Account(int accountNumber, double balance, Person person) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.person = person;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public double getBalance() {
        return this.balance;
    }

    public void getClientData() {
        person.getClientData();
        System.out.println("Cuenta Corriente: " + this.accountNumber);
        System.out.println("Saldo: $" + this.balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Depósito realizado con éxito. Saldo actual: $" + this.balance);
        } else {
            System.out.println("Monto inválido. Debe ser mayor que cero.");
        }
    }

    public void withdrawMoney(double amount) {
        if (amount <= 0) {
            System.out.println("Monto inválido. Debe ser mayor que cero.");
        } else if (amount > this.balance) {
            System.out.println("Saldo insuficiente. No puede girar más de $" + this.balance);
        } else {
            this.balance -= amount;
            System.out.println("Giro realizado con éxito. Saldo restante: $" + this.balance);
        }
    }

    public void checkBalance() {
        System.out.println("Saldo actual: $" + this.balance);
    }

    public Person getPerson() {
        return this.person;
    }
}