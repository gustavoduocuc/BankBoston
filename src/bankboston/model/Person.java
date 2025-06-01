/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankboston.model;

/**
 *
 * @author gustavo.dominguez
 */

public class Person {
    private String RUT;
    private String firstName;
    private String maternalSurname;
    private String paternalSurname;
    private String address;
    private String municipality;
    private int phoneNumber;

    public Person(String RUT, String firstName, String maternalSurname, String paternalSurname, String address, String municipality, int phoneNumber) {
        this.RUT = RUT;
        this.firstName = firstName;
        this.maternalSurname = maternalSurname;
        this.paternalSurname = paternalSurname;
        this.address = address;
        this.municipality = municipality;
        this.phoneNumber = phoneNumber;
    }
     // TODO: Possibly delete
    public void getClientData() {
        System.out.println("\n=== Datos del cliente ===");
        System.out.println("RUT: " + this.RUT);
        System.out.println("Nombre: " + this.firstName);
        System.out.println("Apellido Paterno: " + this.paternalSurname);
        System.out.println("Apellido Materno: " + this.maternalSurname);
        System.out.println("Domicilio: " + this.address);
        System.out.println("Comuna: " + this.municipality);
        System.out.println("Numero de telefono: " + this.phoneNumber);
    }
}