package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;

public class Huespedes {
    private int capacidad;
    private int tamano;
    private Huesped[] coleccionHuespedes;
    //private static List<Huespedes> huespedes;


    public Huespedes (int capacidad)throws IllegalArgumentException, NullPointerException {
        if (capacidad<1)
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        coleccionHuespedes = new Huesped[capacidad];
    }
    public Huesped[] get(){
        coleccionHuespedes=copiaProfundaHuespedes();
        return coleccionHuespedes;
    }
    private Huesped[] copiaProfundaHuespedes() throws NullPointerException, IllegalArgumentException{
        tamano=getTamano();
        if (tamano<1){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía");
        }
        if (coleccionHuespedes==null)
            throw new NullPointerException("ERROR: Colección vacía.");
        Huesped[] copiaProfundaHuespedes = new Huesped[getCapacidad()];
        for (int i = 0; i < tamano; i++) {
            copiaProfundaHuespedes[i] = new Huesped(coleccionHuespedes[i]);
        }
        return copiaProfundaHuespedes;
    }

    public int getTamano() throws NullPointerException {
        if (coleccionHuespedes==null)
            throw new NullPointerException("ERROR: Colección vacía.");
        for (int t = 0; t < getCapacidad() ; t++) {
            if (coleccionHuespedes[t] == null) {
                return t;
            }
        }
        return getCapacidad();
    }
    public int getCapacidad() throws NullPointerException {
        if (coleccionHuespedes==null)
            throw new NullPointerException("ERROR: Colección vacía.");
        capacidad=coleccionHuespedes.length;
        return capacidad;
    }
    public void insertar(Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        if (coleccionHuespedes==null)
            throw new NullPointerException("ERROR:Colección inexistente");
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        if (buscarIndice(huesped)<getCapacidad()){
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        if (getTamano()<getCapacidad()){
            coleccionHuespedes[getTamano()]=new Huesped(huesped);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No se aceptan más huéspedes.");
        }
    }
    private int buscarIndice(Huesped huesped) throws NullPointerException{
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        //if (coleccionHuespedes[0]==null)
        //    throw new NullPointerException("ERROR:Colección vacía1");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHuespedes[i]==null){
                return getCapacidad()+1;
            }
            else if (coleccionHuespedes[i].equals(huesped)){
                return i;
            }
        }
        return getCapacidad()+1;
    }
    private Boolean tamanoSuperado(int indice) throws IllegalArgumentException{
        if (indice<0){
            throw new IllegalArgumentException("ERROR: Indice tamaño incorrecto");
        }
        else if (indice >0 && indice<getTamano()){
            return false;
        }
        return true;
    }
    private Boolean capacidadSuperada(int indice) throws IllegalArgumentException{
        if (indice<0){
            throw new IllegalArgumentException("ERROR: Indice capacidad incorrecto");
        }
        else if (indice >0 && indice<getCapacidad()){
            return false;
        }
        return true;
    }
    public Huesped buscar(Huesped huesped) throws NullPointerException, IllegalArgumentException{
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        if (coleccionHuespedes==null)
            throw new NullPointerException("ERROR: Colección vacía.");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHuespedes[i].equals(huesped)){//huesped.getDni().equals(coleccionHuespedes[i].getDni())){
                return new Huesped(coleccionHuespedes[i]);
            }
        }
        return null;
    }
    public void borrar(Huesped huesped) throws OperationNotSupportedException, NullPointerException{
        if (huesped==null)
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        int indice = buscarIndice(huesped);
        if (indice<=getCapacidad()){
            coleccionHuespedes[indice]=null;
            desplazarUnaPosicionIzquierda(indice);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
    }
    private void desplazarUnaPosicionIzquierda(int indice){
        for (int i=indice ; i<getCapacidad()-1 ; i++){
            if (coleccionHuespedes[i+1]!=null){
                coleccionHuespedes[i]=new Huesped(coleccionHuespedes[i+1]);
                coleccionHuespedes[i+1]=null;
            }
            else return;
        }
    }

}
