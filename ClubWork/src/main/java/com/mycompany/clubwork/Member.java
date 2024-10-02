/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clubwork;

import java.util.ArrayList;

/**
 *
 * @author ssuaz
 */
public class Member {
    protected int id; // ID del miembro
    protected String name; // Nombre del miembro
    protected double availableFunds; // Fondos disponibles del miembro
    protected String subscriptionType; // Tipo de suscripción: "VIP" o "Regular"
    protected ArrayList<Invoice> pendingInvoices; // Facturas pendientes del miembro
    protected ArrayList<AuthorizedAffiliate> authorizedPersons; // Afiliados autorizados del miembro

    // Constantes para inicializar y limitar los fondos
    private static final double INITIAL_REGULAR_FUNDS = 50000; // Fondos iniciales para miembros regulares
    private static final double INITIAL_VIP_FUNDS = 100000; // Fondos iniciales para miembros VIP
    private static final double REGULAR_FUNDS_LIMIT = 1000000; // Límite de fondos para miembros regulares
    private static final double VIP_FUNDS_LIMIT = 5000000; // Límite de fondos para miembros VIP
    protected static int vipMembers = 0; // Contador de miembros VIP
    
    //metodo para disminuir el valor del contador en esta clase 
    public static void decreaseVipCount() {
        if (vipMembers > 0) {
            vipMembers--;
        }
    }
    // Constructor de la clase
    public Member(int id, String name, String subscriptionType) {
        this.id = id; // Asigna la ID del miembro
        this.name = name; // Asigna el nombre del miembro
        this.subscriptionType = subscriptionType; // Asigna el tipo de suscripción
        this.pendingInvoices = new ArrayList<>(); // Inicializa la lista de facturas pendientes
        this.authorizedPersons = new ArrayList<>(); // Inicializa la lista de afiliados autorizados

        // Si el miembro es VIP
        if (subscriptionType.equalsIgnoreCase("VIP")) {
            // Verifica si ya hay 3 miembros VIP
            if (vipMembers >= 3) {
                throw new IllegalArgumentException("No se pueden inscribir mas de 3 miembros VIP.");
            }
            this.availableFunds = INITIAL_VIP_FUNDS; // Asigna fondos iniciales para VIP
            vipMembers++; // Incrementa el contador de miembros VIP
        } else {
            this.availableFunds = INITIAL_REGULAR_FUNDS; // Asigna fondos iniciales para regulares
        }
    }
    // Métodos para obtener información básica del miembro
    public int getId() {
        return id; // Retorna la ID del miembro
    }

    public String getName() {
        return name; // Retorna el nombre del miembro
    }

    public double getAvailableFunds() {
        return availableFunds; // Retorna los fondos disponibles
    }

    public String getSubscriptionType() {
        return subscriptionType; // Retorna el tipo de suscripción
    }

    public ArrayList<Invoice> getPendingInvoices() {
        return pendingInvoices; // Retorna las facturas pendientes
    }

    public ArrayList<AuthorizedAffiliate> getAuthorizedPersons() {
        return authorizedPersons; // Retorna la lista de afiliados autorizados
    }

    // Métodos para gestionar los fondos
    public void addFunds(double amount) {
        // Verifica el límite de fondos para miembros VIP
        if (subscriptionType.equalsIgnoreCase("VIP") && availableFunds + amount > VIP_FUNDS_LIMIT) {
            throw new IllegalArgumentException("El límite máximo de fondos para miembros VIP es $5,000,000.");
        } 
        // Verifica el límite de fondos para miembros regulares
        else if (subscriptionType.equalsIgnoreCase("Regular") && availableFunds + amount > REGULAR_FUNDS_LIMIT) {
            throw new IllegalArgumentException("El límite máximo de fondos para miembros regulares es $1,000,000.");
        }
        this.availableFunds += amount; // Agrega la cantidad a los fondos disponibles
    }

    // Métodos para gestionar afiliados autorizados
    public void addAuthorizedPerson(AuthorizedAffiliate affiliate, Club club) {
        // Verifica si ya tiene 10 afiliados autorizados
        if (authorizedPersons.size() >= 10) {
            throw new IllegalArgumentException("No se pueden agregar más de 10 afiliados autorizados.");
        }

        // Verifica que la ID del afiliado no sea la misma que la del miembro principal
        if (affiliate.getId() == this.id) {
            throw new IllegalArgumentException("La ID del afiliado no puede ser la misma que la del miembro principal.");
        }

        // Verifica si la ID del nuevo afiliado ya existe en el club
        for (Member member : club.getMembers()) {
            // Verifica si la ID coincide con algún socio
            if (member.getId() == affiliate.getId()) {
                throw new IllegalArgumentException("Ya existe un miembro con la misma ID en el club.");
            }

            // Verifica si algún afiliado autorizado tiene la misma ID
            for (AuthorizedAffiliate existingAffiliate : member.getAuthorizedPersons()) {
                if (existingAffiliate.getId() == affiliate.getId()) {
                    throw new IllegalArgumentException("Ya existe un afiliado autorizado con la misma ID en el club.");
                }
            }
        }

        // Si todo está bien, agrega el nuevo afiliado
        this.authorizedPersons.add(affiliate);
    }

    // Método para eliminar un afiliado autorizado
    public void removeAuthorizedPerson(AuthorizedAffiliate affiliate) {
        // Verifica si el afiliado tiene facturas pendientes
        if (!affiliate.getPendingInvoices().isEmpty()) {
            throw new IllegalArgumentException("El afiliado tiene facturas pendientes.");
        }
        this.authorizedPersons.remove(affiliate); // Elimina el afiliado de la lista
    }

    // Métodos para gestionar facturas
    public void generateInvoice(String concept, double amount) {
        // Verifica si hay suficientes fondos para generar la factura
        if (availableFunds < amount) {
            throw new IllegalArgumentException("Fondos insuficientes para generar la factura.");
        }
        Invoice newInvoice = new Invoice(concept, amount, this.name); // Crea una nueva factura
        this.pendingInvoices.add(newInvoice); // Agrega la factura a la lista de pendientes
        this.availableFunds -= amount; // Deduce el monto de los fondos disponibles
    }

    public void payInvoice(int invoiceIndex) {
        Invoice invoice = this.pendingInvoices.get(invoiceIndex); // Obtiene la factura por índice
        // Verifica si hay suficientes fondos para pagar la factura
        if (availableFunds < invoice.getAmount()) {
            throw new IllegalArgumentException("Fondos insuficientes para pagar la factura.");
        }
        this.availableFunds -= invoice.getAmount(); // Deduce el monto de los fondos
        this.pendingInvoices.remove(invoiceIndex); // Elimina la factura pagada de la lista
    }
    // metodo para verificar si un miembro es vip
    public boolean isVip() {
        return this.subscriptionType.equalsIgnoreCase(" VIP ");
    }  
}
