package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservas {
    private int capacidad;
    private int tamano;
    private Reserva[] coleccionReservas;
    public Reservas (int capacidad) throws IllegalArgumentException{
        if (capacidad<1)
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        coleccionReservas=new Reserva[capacidad];
    }
    public Reserva[] get(){
        coleccionReservas=copiaProfundaReservas();
        return coleccionReservas;
    }
    private Reserva[] copiaProfundaReservas(){
        tamano=getTamano();
        Reserva[] copiaProfundaReservas=new Reserva[getCapacidad()];
        for (int i=0 ; i<tamano ; i++){
            copiaProfundaReservas[i]=new Reserva(coleccionReservas[i]);
        }
        return copiaProfundaReservas;
    }

    public int getTamano() {
        for(int t=0 ; t < getCapacidad() ; t++){
            if (coleccionReservas[t]==null){
                return t;
            }
        }
        return getCapacidad();
    }

    public int getCapacidad() {
        capacidad=coleccionReservas.length;
        return capacidad;
    }
    public void insertar (Reserva reserva) throws OperationNotSupportedException, NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (buscarIndice(reserva)<getCapacidad()){
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        if (getTamano()<getCapacidad()){
            coleccionReservas[getTamano()]=new Reserva(reserva);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No se aceptan más reservas.");
        }
    }

    private int buscarIndice (Reserva reserva) throws NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede buscar sin indicar una reserva.");
        }
        for (int i=0 ; i < getTamano() ; i++){
            if (coleccionReservas[i]==null){
                return getCapacidad()+1;
            }
            else if (coleccionReservas[i].equals(reserva)) {
                return i;
            }
        }
        return getCapacidad()+1;
    }
    private Boolean tamanoSuperado (int indice) throws IllegalArgumentException{
        if (indice<0){
            throw new IllegalArgumentException("ERROR: Indice tamaño incorrecto");
        }
        else if (indice >0 && indice<getTamano()){
            return false;
        }
        return true;
    }
    private Boolean capacidadSuperada (int indice) throws IllegalArgumentException{
        if (indice<0){
            throw new IllegalArgumentException("ERROR: Indice capacidad incorrecto.");
        }
        else if (indice >0 && indice<getCapacidad()){
            return false;
        }
        return true;
    }
    public Reserva buscar (Reserva reserva) throws NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede buscar sin indicar una reserva.");
        }
        for (int i=0 ; i < getTamano() ; i++){
            if (coleccionReservas[i].equals(reserva)){
                return new Reserva(reserva);
            }
        }
        return null;
    }
    public void borrar (Reserva reserva) throws OperationNotSupportedException, NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        int indice = buscarIndice(reserva);
        if (indice<=getCapacidad()){
            coleccionReservas[indice]=null;
            desplazarUnaPosicionHaciaIzquierda(indice);
        }
        else throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
    }
    private void desplazarUnaPosicionHaciaIzquierda (int indice){
        for (int i=indice ; i<getCapacidad()-1 ; i++){
            if (coleccionReservas[i+1]!=null){
                coleccionReservas[i]=new Reserva(coleccionReservas[i+1]);
                coleccionReservas[i+1]=null;
            }
        }
    }
    public Reserva[] getReservas (Huesped huesped) throws NullPointerException{
        int j=0;
        tamano=getTamano();
        capacidad=getCapacidad();
        Reserva[] copiaProfundaHabitacionesHuesped = new Reserva[capacidad];
        for (int i=0 ; i < tamano ; i++){
            if (huesped==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");
            }
            if (coleccionReservas[i].getHuesped().equals(huesped)){
                copiaProfundaHabitacionesHuesped[j] = new Reserva(coleccionReservas[i]);
                j++;
            }
        }
        if (j==0){
            return null;
        }
        return copiaProfundaHabitacionesHuesped;
    }
    public Reserva[] getReservas (TipoHabitacion tipoHabitacion) throws NullPointerException{
        int j=0;
        tamano=getTamano();
        capacidad=getCapacidad();
        Reserva[] copiaProfundaHabitacionesHabitacion = new Reserva[capacidad];
        for (int i=0 ; i < tamano ; i++){
            if (tipoHabitacion==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
            }
            if (coleccionReservas[i].getHabitacion().getTipoHabitacion().equals(tipoHabitacion)){
                copiaProfundaHabitacionesHabitacion[j]=new Reserva(coleccionReservas[i]);
                j++;
            }
        }
        if (j==0){
            return null;
        }
        return copiaProfundaHabitacionesHabitacion;
    }
    public Reserva[] getReservasFuturas (Habitacion habitacion) throws NullPointerException {
        int j=0;
        tamano=getTamano();
        capacidad=getCapacidad();
        Reserva[] copiaProfundaHabitacionesReservasFuturas = new Reserva[capacidad];
        for (int i=0 ; i < tamano ; i++){
            if (habitacion==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
            }
            if (coleccionReservas[i].getHabitacion().equals(habitacion)){
                if (coleccionReservas[i].getFechaFinReserva().isAfter(LocalDate.now())){
                    copiaProfundaHabitacionesReservasFuturas[j]=new Reserva(coleccionReservas[i]);
                    j++;
                }
            }
        }
        return copiaProfundaHabitacionesReservasFuturas;
    }

    public void realizarCheckin (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException{
        if (reserva==null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        else if (fecha==null){
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }
        else if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("ERROR: La fecha no puede ser posterior a la actual.");
        }
        else if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay())){
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckIn en una fecha anterior a la reservada.");
        }
        else if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusDays(1))) {
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckIn en una fecha posterior al final de la reserva.");
        }
        for (int i = 0 ; i < coleccionReservas.length ; i++) {
            if (coleccionReservas[i]!=null){
                if (coleccionReservas[i].equals(reserva)) {
                    coleccionReservas[i].setCheckIn(fecha);
                    System.out.println("CheckIn añadido a la reserva.");
                }
            }
        }
    }

    public void realizarCheckout (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException{
        if (reserva==null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        else if (fecha==null){
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }
        else if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("ERROR: La fecha no puede ser posterior a la actual.");
        }
        else if (reserva.getCheckIn()==null || reserva.getCheckIn().isAfter(fecha)){
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut sin un CheckIn previo.");
        }
        else if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusDays(1))) {
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut en una fecha posterior al final de la reserva.");
        }
        for (int i = 0 ; i < coleccionReservas.length ; i++) {
            if (coleccionReservas[i]!=null){
                if (coleccionReservas[i].equals(reserva)) {
                    coleccionReservas[i].setCheckOut(fecha);
                    System.out.println("CheckOut añadido a la reserva.");
                }
            }
        }
    }

}
