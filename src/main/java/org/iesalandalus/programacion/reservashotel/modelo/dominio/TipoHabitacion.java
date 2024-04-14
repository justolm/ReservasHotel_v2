package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SIMPLE("SIMPLE",1),
    DOBLE("DOBLE",2),
    TRIPLE("TRIPLE",3),
    SUITE("SUITE",4);

    private final String cadenaAMostrar;
    private final int numeroMaximoPersonas;

    @Override
    public String toString() {
        return "TipoHabitacion{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                ", numeroMaximoPersonas=" + numeroMaximoPersonas +
                '}';
    }

    public int getNumeroMaximoPersonas() {
        return numeroMaximoPersonas;
    }

    TipoHabitacion(String cadenaAMostrar, int numeroMaximoPersonas){
        this.cadenaAMostrar = cadenaAMostrar;
        this.numeroMaximoPersonas = numeroMaximoPersonas;
    }

}
