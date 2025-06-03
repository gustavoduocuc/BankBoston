package bankboston.model;
import bankboston.interfaces.Displayable;

public abstract class Account implements Displayable {
    protected int accountNumber;
    protected double balance;
    private Person person;

    public Account(int accountNumber, double balance, Person person) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.person = person;
    }
    
    // Sobrecarga: balance parte en 0
    public Account(int accountNumber, Person person) {
        this(accountNumber, 0.0, person);
    }
        
    public Person getPerson() {
        return person;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    
    @Override
    public void displayClientInfo() {
        System.out.println("\n=== Información del Cliente ===");
        person.getClientData();
        System.out.println("Tipo de cuenta: " + getAccountTypeString());
        System.out.println("Número de cuenta: " + accountNumber);
        System.out.println("Saldo: $" + balance);
    }

    
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void checkBalance();
    public abstract String getAccountTypeString();
}
