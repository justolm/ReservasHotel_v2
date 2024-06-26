package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reservas {
    private List<Reserva> coleccionReservas;

    public Reservas () {
        coleccionReservas=new ArrayList<>();
    }

    public List<Reserva> get() {
        coleccionReservas=copiaProfundaReservas();
        return coleccionReservas;
    }

    private List<Reserva> copiaProfundaReservas() {
        List<Reserva> copiaProfundaReservas=new ArrayList<>();
        for (int i=0 ; i<coleccionReservas.size() ; i++){
            copiaProfundaReservas.add(coleccionReservas.get(i));
        }
        return copiaProfundaReservas;
    }

    public int getTamano() {
        return coleccionReservas.size();
    }

    public void insertar (Reserva reserva) throws OperationNotSupportedException, NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)){
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.add(reserva);
    }

    public Reserva buscar (Reserva reserva) throws NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        for (int i=0 ; i < getTamano() ; i++){
            if (coleccionReservas.get(i).equals(reserva)){
                return coleccionReservas.get(i);
            }
        }
        return null;
    }

    public void borrar (Reserva reserva) throws OperationNotSupportedException, NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)){
            coleccionReservas.remove(reserva);
        }
        else throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
    }

    public List<Reserva> getReservas (Huesped huesped) throws NullPointerException{
        List<Reserva> copiaProfundaHabitacionesHuesped = new ArrayList<>();
        for (int i=0 ; i < getTamano() ; i++){
            if (huesped==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
            }
            if (coleccionReservas.get(i).getHuesped().equals(huesped)){
                copiaProfundaHabitacionesHuesped.add(new Reserva(coleccionReservas.get(i)));
            }
        }
        return copiaProfundaHabitacionesHuesped;
    }

    public List<Reserva> getReservas (TipoHabitacion tipoHabitacion) throws NullPointerException{
        List<Reserva> copiaProfundaHabitacionesHabitacion = new ArrayList<>();
        for (int i=0 ; i < getTamano() ; i++){
            if (tipoHabitacion==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
            }
            if (coleccionReservas.get(i).getHabitacion().getTipoHabitacion().equals(tipoHabitacion)){
                copiaProfundaHabitacionesHabitacion.add(new Reserva(coleccionReservas.get(i)));
            }
        }
        return copiaProfundaHabitacionesHabitacion;
    }

    public List<Reserva> getReservasFuturas (Habitacion habitacion) throws NullPointerException {
        List<Reserva> copiaProfundaHabitacionesReservasFuturas = new ArrayList<>();
        for (int i=0 ; i < getTamano() ; i++){
            if (habitacion==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
            }
            if (coleccionReservas.get(i).getHabitacion().equals(habitacion)){
                if (coleccionReservas.get(i).getFechaFinReserva().isAfter(LocalDate.now())){
                    copiaProfundaHabitacionesReservasFuturas.add(new Reserva(coleccionReservas.get(i)));
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
        for (int i = 0 ; i < coleccionReservas.size() ; i++) {
            if (coleccionReservas.get(i).equals(reserva)) {
                coleccionReservas.get(i).setCheckIn(fecha);
                System.out.println("CheckIn añadido a la reserva.");
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
        for (int i = 0 ; i < coleccionReservas.size() ; i++) {
            if (coleccionReservas.get(i).equals(reserva)) {
                coleccionReservas.get(i).setCheckOut(fecha);
                System.out.println("CheckOut añadido a la reserva.");
            }
        }
    }
}
