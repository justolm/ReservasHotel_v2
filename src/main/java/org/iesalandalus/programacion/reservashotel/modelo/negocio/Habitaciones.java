package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones {
    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones() {
        coleccionHabitaciones = new ArrayList<>();
    }

    public List<Habitacion> get(){
        coleccionHabitaciones=copiaProfundaHabitaciones();
        return coleccionHabitaciones;
    }

    private List<Habitacion> copiaProfundaHabitaciones() throws NullPointerException{
        if (coleccionHabitaciones.isEmpty()){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía");
        }
        List<Habitacion> copiaProfundaHabitaciones = new ArrayList<>();
        for (int i = 0; i < coleccionHabitaciones.size(); i++) {
            copiaProfundaHabitaciones.add(coleccionHabitaciones.get(i));
        }
        return copiaProfundaHabitaciones;
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) throws NullPointerException{
        if (tipoHabitacion==null){
            throw new NullPointerException("ERROR: El tipo de habitación no puede estar vacío.");
        }
        int j=0;
        if (coleccionHabitaciones.isEmpty()){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía.");
        }
        List<Habitacion> copiaProfundaHabitacionesFiltro = new ArrayList<>();
        for (int i = 0; i < getTamano(); i++) {
            if (coleccionHabitaciones.get(i).getTipoHabitacion().equals(tipoHabitacion)){
                copiaProfundaHabitacionesFiltro.add(new Habitacion(coleccionHabitaciones.get(i)));
            }
        }
        return copiaProfundaHabitacionesFiltro;
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        if (coleccionHabitaciones.contains(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) throws NullPointerException{
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHabitaciones.get(i).equals(habitacion)){
                return coleccionHabitaciones.get(i);
            }
        }
        return null;
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (coleccionHabitaciones.contains(habitacion)){
            coleccionHabitaciones.remove(habitacion);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }
}
