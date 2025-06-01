/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankboston.model;

/**
 *
 * @author gustavo.dominguez
 */
public class CreditAccount extends Account {
   
    public CreditAccount(int accountNumber, Person person) {
       super(accountNumber, person);
    }
 
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Depósito realizado con éxito a la cuenta de credito. Saldo actual: $" + this.balance);
        } else {
            System.out.println("Monto inválido. Debe ser mayor que cero.");
        }
    }
    
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Monto inválido. Debe ser mayor que cero.");
        } else if (amount > this.balance) {
            System.out.println("Saldo insuficiente. No puede girar más de $" + this.balance);
        } else {
            this.balance -= amount;
            System.out.println("Giro realizado con éxito a su cuenta de credito. Saldo restante: $" + this.balance);
        }
    }
    
    @Override
    public void checkBalance() {
        System.out.println("Saldo actual en la cuenta de credito: $" + this.balance);
    }
    
}

