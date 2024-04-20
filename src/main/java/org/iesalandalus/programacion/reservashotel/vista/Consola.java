package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consola {
    private Consola(){}

    public static void mostrarMenu(){
        System.out.println("* - * - MENÚ - * - *");
        for (Opcion opcion1 : Opcion.values()){
            System.out.print(opcion1);
        }
    }

    public static Opcion elegirOpcion(){
        Opcion elegido;
        int elegidoInt, numOpciones;
        numOpciones = Opcion.values().length-1;
        do{
            System.out.print("Elija la opción deseada (0 - "+numOpciones+"): ");
            elegidoInt=Entrada.entero();
        }while (elegidoInt<0 || elegidoInt>numOpciones);
        elegido = Opcion.values()[elegidoInt];
        return elegido;
    }

    public static Huesped leerHuesped() throws NullPointerException, IllegalArgumentException {
        String nombre, dni, correo, telefono;
        LocalDate fechaNacimiento;
        System.out.println("Introduzca los datos del huésped. ");
        System.out.print("Nombre: ");
        nombre=Entrada.cadena();
        System.out.print("DNI: ");
        dni=Entrada.cadena();
        System.out.print("Correo electrónico: ");
        correo=Entrada.cadena();
        System.out.print("Teléfono: ");
        telefono=Entrada.cadena();
        System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
        fechaNacimiento= LocalDate.parse(Entrada.cadena());
        return (new Huesped(nombre, dni, correo, telefono, fechaNacimiento));
    }

    public static Huesped getHuespedPorDni() throws NullPointerException, IllegalArgumentException {
        String dni;
        System.out.println("Introduzca el DNI del huésped: ");
        dni=Entrada.cadena();
        dni=dni.toUpperCase();
        if (dni==null){
            throw new NullPointerException("ERROR: No se puede buscar un DNI vacío.");
        }
        return new Huesped("nombre",dni,"correo@falso.es","950000000", LocalDate.of(2000,1,1));
    }

    public static LocalDate leerFecha(String mensaje) throws NullPointerException{
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA);
        if (mensaje == null) {
            throw new NullPointerException("ERROR: La fecha está vacía.");
        }
        try {
            return LocalDate.parse(mensaje, formatoFecha);
        } catch (DateTimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static LocalDateTime leerFechaHora (String mensaje) throws IllegalArgumentException {
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA);
        if (mensaje.isEmpty()){
            throw new IllegalArgumentException("ERROR: La fecha/hora está vacía.");
        }
        try {
            return LocalDateTime.parse(mensaje, formatoFechaHora);
        } catch (DateTimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Habitacion leerHabitacion(){
        int planta=1, puerta=0, precio=0;
        TipoHabitacion tipoHabitacion;
        Habitacion habitacion;
        System.out.println("Introduzca los datos de la habitación. ");
        do{
            if (planta != 1){
                System.out.print("La planta tiene que ser un número entre "+ Habitacion.MIN_NUMERO_PLANTA +" y " + Habitacion.MAX_NUMERO_PLANTA + ". ");
            }
            System.out.print("Planta ("+ Habitacion.MIN_NUMERO_PLANTA + '-' + Habitacion.MAX_NUMERO_PLANTA +"): ");
            planta=Entrada.entero();
        } while (planta<Habitacion.MIN_NUMERO_PLANTA || planta > Habitacion.MAX_NUMERO_PLANTA);
        do{
            if (puerta != 0){
                System.out.print("La puerta tiene que ser un número entre "+ Habitacion.MIN_NUMERO_PUERTA +" y " + Habitacion.MAX_NUMERO_PUERTA + ". ");
            }
            System.out.print("Puerta (" + Habitacion.MIN_NUMERO_PUERTA + '-' + Habitacion.MAX_NUMERO_PUERTA + "): ");
            puerta=Entrada.entero();
        } while (puerta < Habitacion.MIN_NUMERO_PUERTA || puerta > Habitacion.MAX_NUMERO_PUERTA);
        do {
            if (precio != 0){
                System.out.print("El precio tiene que ser un número entero entre " + Habitacion.MIN_PRECIO_HABITACION + " y " + Habitacion.MAX_PRECIO_HABITACION + ". ");
            }
            System.out.print("Introduzca el precio (" + Habitacion.MIN_PRECIO_HABITACION + " - " + Habitacion.MAX_PRECIO_HABITACION + "): ");
            precio=Entrada.entero();
        } while (precio < Habitacion.MIN_PRECIO_HABITACION || precio > Habitacion.MAX_PRECIO_HABITACION);
        tipoHabitacion=leerTipoHabitacion();
        habitacion=new Habitacion(planta, puerta, precio, tipoHabitacion);
        return habitacion;
    }

    public static Habitacion leerHabitacionPorIdentificador() throws IllegalArgumentException {
        int planta, puerta;
        Habitacion habitacion=null;
        System.out.println("Introduzca los datos de la habitación. ");
        System.out.print("Planta (" + Habitacion.MIN_NUMERO_PLANTA + " - " + Habitacion.MAX_NUMERO_PLANTA + "): ");
        planta=Entrada.entero();
        if (planta<Habitacion.MIN_NUMERO_PLANTA || planta>Habitacion.MAX_NUMERO_PLANTA){
            throw new IllegalArgumentException("ERROR: El número de planta es incorrecto.");
        }
        System.out.print("Puerta (" + Habitacion.MIN_NUMERO_PUERTA + " - " + Habitacion.MAX_NUMERO_PUERTA + "): ");
        puerta=Entrada.entero();
        if (puerta<Habitacion.MIN_NUMERO_PUERTA || puerta>Habitacion.MAX_NUMERO_PUERTA){
            throw new IllegalArgumentException("ERROR: El número de puerta es incorrecto.");
        }
        habitacion = new Habitacion(planta,puerta,40,TipoHabitacion.SIMPLE);
        return habitacion;
    }

    public static TipoHabitacion leerTipoHabitacion() {
        int tipoHab=0;
        TipoHabitacion tipoHabitacion;
        do {
            if (tipoHab != 0){
                System.out.print("Escriba la opción deseada (número entre 0 y " + TipoHabitacion.values().length);
            }
            System.out.println("Elija el tipo de habitación: ");
            for (TipoHabitacion tipoHabitacion1 : TipoHabitacion.values()){
                System.out.println(tipoHabitacion1.ordinal() + " - " + tipoHabitacion1.name() + " (máximo " + tipoHabitacion1.getNumeroMaximoPersonas() + " personas).");
            }
            tipoHab=Entrada.entero();
        } while (tipoHab<0 || tipoHab >=TipoHabitacion.values().length);
        tipoHabitacion=TipoHabitacion.values()[tipoHab];
        return tipoHabitacion;
    }

    public static Regimen leerRegimen() {
        Regimen regimen;
        int numReg = 0;
        do {
            if (numReg != 0){
                System.out.print("Escriba la opción deseada (número entre 0 y " + Regimen.values().length);
            }
            System.out.println("Elija el régimen deseado: ");
            for (Regimen regimen1 : Regimen.values()){
                System.out.println(regimen1.ordinal() + " - " + regimen1.name() + " .");
            }
            numReg=Entrada.entero();
        } while (numReg<0 || numReg>Regimen.values().length);
        regimen=Regimen.values()[numReg];
        return regimen;
    }

    public static Reserva leerReserva() throws IllegalArgumentException, NullPointerException, OperationNotSupportedException {
        Reserva reserva;
        Huesped huesped;
        Regimen regimen;
        TipoHabitacion tipoHabitacion;
        Habitacion habitacion;
        LocalDate fechaInicioReserva, fechaFinReserva;
        String entrada;
        int numeroPersonas,maximoPersonas;
        huesped = new Huesped(getHuespedPorDni());
        tipoHabitacion = leerTipoHabitacion();
        habitacion= new Habitacion(3,14,50,tipoHabitacion);
        maximoPersonas = tipoHabitacion.getNumeroMaximoPersonas();
        do{
            System.out.print("Introduzca el número de personas: ");
            numeroPersonas=Entrada.entero();
        }while (numeroPersonas<0 || numeroPersonas>maximoPersonas);
        regimen = leerRegimen();
        System.out.print("Introduzca la fecha de inicio de reserva (dd/mm/aa): ");
        entrada = Entrada.cadena();
        fechaInicioReserva = leerFecha(entrada);
        System.out.print("Introduzca la fecha de fin de reserva (dd/mm/aa): ");
        entrada = Entrada.cadena();
        fechaFinReserva = leerFecha(entrada);
        reserva=new Reserva(huesped,habitacion,regimen,fechaInicioReserva,fechaFinReserva,numeroPersonas);
        return reserva;
    }
}
