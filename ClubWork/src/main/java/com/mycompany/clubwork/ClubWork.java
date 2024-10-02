/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.clubwork;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ssuaz
 */
public class ClubWork {

    public static void main(String[] args) {
        // Creamos una nueva instancia del Club
        Club club = new Club();
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                // Mostrar el menu de opciones
                System.out.println("\n--- Menu del Club Social ---");
                System.out.println("1. Inscribir Miembro");
                System.out.println("2. Registrar Persona Autorizada");
                System.out.println("3. Pagar Factura");
                System.out.println("4. Registrar Consumo");
                System.out.println("5. Aumentar Fondos");
                System.out.println("6. Eliminar Miembro");
                System.out.println("7. Mostrar Miembros");
                System.out.println("8. Mostrar Personas Autorizadas y sus Miembros");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                option = scanner.nextInt();  // Leer opcion del usuario

                switch (option) {
                    case 1-> {
                        // Inscribir un nuevo miembro
                        int id = 0;
                        while (true) {
                            System.out.print("Ingrese la cedula del miembro: ");
                            try {
                                id = scanner.nextInt();
                                scanner.nextLine();  // Limpiar el buffer
                                break; // Salir del bucle si la entrada es válida
                            } catch (InputMismatchException e) {
                                System.out.println("Error: La cedula debe ser un numero. Intentelo de nuevo.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        }
                        
                        System.out.print("Ingrese el nombre del miembro: ");
                        String name = scanner.nextLine().toLowerCase();
                          // Limpiar el buffer del scanner
                        
                        System.out.print("Ingrese el tipo de suscripcion (VIP o Regular): ");
                        String subscriptionType = scanner.next();
                        scanner.nextLine();  // Limpiar el buffer del scanner
                        
                        try {
                            // Crear un nuevo miembro con los datos proporcionados
                            Member newMember = new Member(id, name, subscriptionType);
                            // Añadir el nuevo miembro al club
                            club.addMember(newMember);
                            System.out.println("Miembro inscrito exitosamente.");
                        } catch (Exception e) {
                            // Mostrar error si algo salio mal
                            System.out.println("Error: " + e.getMessage());
                            
                        }
                        break;
                    }


                    case 2 -> {
                        // Registrar una persona autorizada para un miembro
                        int id = 0;
                        boolean validInput = false;

                        while (!validInput) {
                            try {
                                System.out.print("Ingrese la cedula del miembro: ");
                                id = scanner.nextInt(); // Leer cedula del miembro
                                validInput = true; // Si se ingresa correctamente un número, cambiamos la bandera
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Ingrese un numero valido.");
                                scanner.next(); // Limpiar el valor incorrecto del buffer
                            }
                        }

                        Member member = club.findMember(id);  // Buscar el miembro en el club
                        if (member == null) {
                            System.out.println("No se encontro ningun miembro con esa cedula.");
                        } else {
                            int authorizedId = 0;
                            validInput = false;

                            while (!validInput) {
                                try {
                                    System.out.print("Ingrese la cedula de la persona autorizada: ");
                                    authorizedId = scanner.nextInt();  // Leer cedula de la persona autorizada
                                    validInput = true; // Si se ingresa correctamente un número, cambiamos la bandera
                                } catch (InputMismatchException e) {
                                    System.out.println("Error: Ingrese un numero valido.");
                                    scanner.next(); // Limpiar el valor incorrecto del buffer
                                }
                            }

                            scanner.nextLine();  // Limpiar el buffer del scanner
                            System.out.print("Ingrese el nombre de la persona autorizada: ");
                            String authorizedName = scanner.next();  // Leer nombre de la persona autorizada
                            scanner.nextLine();  // Limpiar el buffer del scanner

                            try {
                                // Crear una nueva persona autorizada
                                AuthorizedAffiliate newAuthorized = new AuthorizedAffiliate(authorizedId, authorizedName);
                                // Añadir la persona autorizada al miembro, pasando también el club para validar
                                member.addAuthorizedPerson(newAuthorized, club);
                                System.out.println("Persona autorizada registrada exitosamente.");
                            } catch (Exception e) {
                                // Mostrar error si algo salió mal
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    }

                    case 3 -> {
                        // Pagar una factura pendiente
                        System.out.print("Ingrese la cedula del miembro: ");
                        int id = scanner.nextInt();
                        Member member = club.findMember(id); // Buscar el miembro
                        if (member == null) {
                            System.out.println("No se encontro ningun miembro con esa cedula.");
                        } else if (member.getPendingInvoices().isEmpty()) {
                            // Si no tiene facturas pendientes
                            System.out.println("Este miembro no tiene facturas pendientes.");
                        } else {
                            // Mostrar facturas pendientes
                            System.out.println("Facturas pendientes:");
                            for (int i = 0; i < member.getPendingInvoices().size(); i++) {
                                Invoice invoice = member.getPendingInvoices().get(i);
                                System.out.println((i + 1) + ". Concepto: " 
                                        + invoice.getConcept() + ", Monto: " 
                                        + invoice.getAmount());
                            }
                            System.out.print("Seleccione el numero de la factura que desea pagar: ");
                            int invoiceNumber = scanner.nextInt();  // Leer numero de factura
                            try {
                                // Pagar la factura seleccionada
                                member.payInvoice(invoiceNumber - 1);  // -1 porque las listas empiezan en 0
                                System.out.println("Factura pagada exitosamente.");
                            } catch (Exception e) {
                                // Mostrar error si algo salio mal
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    }

                    case 4 -> {
                        // Registrar un consumo
                        System.out.print("Ingrese la cedula del miembro: ");
                        int id = scanner.nextInt();
                        Member member = club.findMember(id); // Buscar el miembro
                        if (member == null) {
                            System.out.println("No se encontro ningun miembro con esa cedula.");
                        } else {
                            // Registrar un nuevo consumo
                            System.out.print("Ingrese el concepto del consumo: ");
                            String concept = scanner.next();
                            System.out.print("Ingrese el monto del consumo: ");
                            double amount = scanner.nextDouble();
                            try {
                                // Generar la factura por el consumo registrado
                                member.generateInvoice(concept, amount);
                                System.out.println("Consumo registrado exitosamente.");
                            } catch (Exception e) {
                                // Mostrar error si algo salio mal
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    }

                    case 5 -> {
                        // Añadir fondos a la cuenta de un miembro
                        System.out.print("Ingrese la cedula del miembro: ");
                        int id = scanner.nextInt();
                        Member member = club.findMember(id); // Buscar el miembro
                        if (member == null) {
                            System.out.println("No se encontro ningun miembro con esa cedula.");
                        } else {
                            // Añadir fondos al miembro
                            System.out.print("Ingrese la cantidad de fondos a añadir: ");
                            double amountToAdd = scanner.nextDouble();
                            try {
                                // Añadir los fondos
                                member.addFunds(amountToAdd);
                                System.out.println("Fondos añadidos exitosamente.");
                            } catch (Exception e) {
                                // Mostrar error si algo salio mal
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    }

                    case 6 -> {
                        // Eliminar un miembro
                        System.out.print("Ingrese la cedula del miembro que desea eliminar: ");
                        int id = scanner.nextInt();
                        try {
                            // Eliminar el miembro del club
                            club.removeMember(id);
                            System.out.println("Miembro eliminado exitosamente.");
                        } catch (Exception e) {
                            // Mostrar error si algo salio mal
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    case 7 -> {// Mostrar la lista de miembros
                        club.showMembers();
                        System.out.println("hay " + club.getVipMembersCount() + " miembros vip");
                        System.out.println("hay " + Member.vipMembers + " miembros vip");
                    }
                    case 8 -> // Mostrar las personas autorizadas y sus miembros correspondientes
                        club.showAuthorizedPersons();

                    case 0 -> // Salir del programa
                        System.out.println("Saliendo...");

                    default -> // Opcion no valida
                        System.out.println("Opcion invalida.");
                }
            } while (option != 0); // Seguir mostrando el menu hasta que el usuario seleccione la opcion 0 (salir)
        }
    }
}
