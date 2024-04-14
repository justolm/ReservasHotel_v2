package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Huespedes {
    private List<Huesped> coleccionHuespedes;

    public Huespedes () {
        coleccionHuespedes = new ArrayList<>();
    }

    public List<Huesped> get() {
        coleccionHuespedes=copiaProfundaHuespedes();
        return coleccionHuespedes;
    }

    private List<Huesped> copiaProfundaHuespedes() throws NullPointerException {
        if (coleccionHuespedes==null)
            throw new NullPointerException("ERROR: No se puede copiar una colección vacía.");
        List<Huesped> copiaProfundaHuespedes = new ArrayList<>();
        for (int i = 0; i < getTamano(); i++) {
            copiaProfundaHuespedes.add(new Huesped(coleccionHuespedes.get(i)));
        }
        return copiaProfundaHuespedes;
    }

    public int getTamano() throws NullPointerException {
        return coleccionHuespedes.size();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        if (coleccionHuespedes.contains(huesped)){
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(huesped);
    }

    public Huesped buscar(Huesped huesped) throws NullPointerException, IllegalArgumentException{
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHuespedes.get(i).equals(huesped)){
                return coleccionHuespedes.get(i);
            }
        }
        return null;
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException, NullPointerException{
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        if (coleccionHuespedes.contains(huesped)){
            coleccionHuespedes.remove(huesped);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
    }
}
