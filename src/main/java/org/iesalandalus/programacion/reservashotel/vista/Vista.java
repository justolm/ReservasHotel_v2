package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) throws NullPointerException {
        if (controlador==null){
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar(){
        try{
            Opcion opcion = null;
            do{
                if (opcion!=null){
                    System.out.println("Presione ENTER para continuar...");
                    Entrada.cadena();
                }
                Consola.mostrarMenu();
                opcion=Consola.elegirOpcion();
                ejecutarOpcion(opcion);
            }while (opcion!=Opcion.SALIR);
            System.out.println("Hasta luego!!!!");
        } catch (NullPointerException | IllegalArgumentException | DateTimeException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void terminar(){
        System.out.println("Final de la ejecución.");
    }

    private void ejecutarOpcion(Opcion opcion) throws IllegalArgumentException, NullPointerException, OperationNotSupportedException {
        switch (opcion)
        {
            case INSERTAR_HUESPED:
                insertarHuesped();
                break;
            case BUSCAR_HUESPED:
                inicializarDatos(); // Método creado para generar datos válidos para realizar pruebas.
                buscarHuesped();
                break;
            case BORRAR_HUESPED:
                borrarHuesped();
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion();
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion();
                break;
            case BORRAR_HABITACION:
                borrarHabitacion();
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva();
                break;
            case LISTAR_RESERVA:
                int numOpcion;
                System.out.println("Introduzca '1' para buscar por huésped o '2' para buscar por tipo de habitación: ");
                numOpcion= Entrada.entero();
                Huesped huesped;
                TipoHabitacion tipoHabitacion1;
                if (numOpcion==1){
                    huesped=Consola.getHuespedPorDni();
                    listarReservas(huesped);
                }
                else if (numOpcion==2) {
                    tipoHabitacion1=Consola.leerTipoHabitacion();
                    listarReservas(tipoHabitacion1);
                }
                else throw new IllegalArgumentException("ERROR: Ha introducido una opción no válida.");
                break;
            case ANULAR_RESERVA:
                anularReserva();
                break;
            case MOSTRAR_RESERVAS:
                mostrarReservas();
                break;
            case CONSULTAR_DISPONIBILIDAD:
                TipoHabitacion tipoHabitacion;
                LocalDate fechaInicioReserva, fechaFinReserva;
                System.out.println("Introduzca el tipo de habitación: ");
                tipoHabitacion = Consola.leerTipoHabitacion();
                System.out.println("Introduzca la fecha de entrada (dd/mm/aa): ");
                fechaInicioReserva = Consola.leerFecha(Entrada.cadena());
                System.out.println("Introduzca la fecha de salida (dd/mm/aa): ");
                fechaFinReserva = Consola.leerFecha(Entrada.cadena());
                Habitacion habitacion1=consultarDisponibilidad(tipoHabitacion, fechaInicioReserva, fechaFinReserva);
                System.out.println(habitacion1);
                break;
            case REALIZAR_CHECKIN:
                realizarCheckin();
                break;
            case REALIZAR_CHECKOUT:
                realizarCheckout();
                break;
            case SALIR:
                break;
        }
    }
    private void insertarHuesped() {
        Huesped huesped;
        try {
            huesped=Consola.leerHuesped();
            controlador.insertar(huesped);
            System.out.println("Huésped insertado correctamente.");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private void buscarHuesped() {
        Huesped huesped, huespedEncontrado;
        try {
            huesped=Consola.getHuespedPorDni();
            huespedEncontrado= controlador.buscar(huesped);
            if (huespedEncontrado!=null){
                System.out.println("Huésped encontrado.");
                System.out.println(huespedEncontrado);
            }
            else {
                System.out.println("El huésped indicado no existe.");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private void borrarHuesped(){
        Huesped huesped;
        try {
            huesped=Consola.getHuespedPorDni();
            controlador.borrar(huesped);
            System.out.println("Huésped eliminado.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }
    private void mostrarHuespedes(){
        try {
            if (controlador.getHuespedes().length>0){
                for (int i=0 ; i< controlador.getHuespedes().length ; i++){
                    if (controlador.getHuespedes()[i]!=null){
                        System.out.println(controlador.getHuespedes()[i]);
                    }
                }
            }
            else {
                System.out.println("No hay huéspedes para mostrar.");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private void insertarHabitacion(){
        try {
            Habitacion habitacion;
            habitacion = Consola.leerHabitacion();
            controlador.insertar(habitacion);
            System.out.println("Habitación insertada correctamente.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private void buscarHabitacion(){
        Habitacion habitacion, habitacionEncontrada;
        try {
            habitacion = Consola.leerHabitacionPorIdentificador();
            habitacionEncontrada = controlador.buscar(habitacion);
            if (habitacionEncontrada!=null){
                System.out.println("Habitación encontrada.");
                System.out.println(habitacionEncontrada);
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private void borrarHabitacion(){
        Habitacion habitacion;
        try {
            habitacion = Consola.leerHabitacionPorIdentificador();
            controlador.borrar(habitacion);
            System.out.println("Habitación borrada.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private void mostrarHabitaciones(){
        try {
            if(!controlador.getHabitaciones().isEmpty()){
                for (Habitacion habitacion : controlador.getHabitaciones()){
                    if (habitacion!=null){
                        System.out.println(habitacion);
                    }
                }
            }
            else {
                System.out.println("No hay habitaciones para mostrar.");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private void insertarReserva() {
        Reserva reserva;
        Habitacion habitacion;
        try {
            reserva=Consola.leerReserva();
            habitacion = consultarDisponibilidad(reserva.getHabitacion().getTipoHabitacion(), reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());
            if (habitacion!=null){
                controlador.insertar(new Reserva(reserva.getHuesped(),habitacion,reserva.getRegimen(),reserva.getFechaInicioReserva(),reserva.getFechaFinReserva(),reserva.getNumeroPersonas()));
                System.out.println("Reserva insertada correctamente.");
            }
            else {
                System.out.println("ERROR: No hay habitaciones disponibles para esa fecha.");
            }
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }
    private void listarReservas (Huesped huesped){
        if (huesped==null){
            System.out.println("ERROR: No se pueden listar reservas de un huésped nulo.");
        }
        else {
            try {
                if (controlador.getReservas(huesped)!=null){
                    for(Reserva reserva : controlador.getReservas(huesped)){
                        if (reserva!=null){
                            System.out.println(reserva);
                        }
                    }
                }
                else {
                    System.out.println("No existen reservas para el huésped indicado.");
                }
            } catch (NullPointerException | IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private void listarReservas (TipoHabitacion tipoHabitacion){
        if (tipoHabitacion==null){
            System.out.println("ERROR: No se pueden listar reservas de un tipo de habitación nulo.");
        }
        else {
            try {
                if (controlador.getReservas(tipoHabitacion)!=null){
                    for (Reserva reserva : controlador.getReservas(tipoHabitacion)){
                        if (reserva!=null){
                            System.out.println(reserva);
                        }
                    }
                }
                else {
                    System.out.println("No existen reservas para el tipo de habitación indicado.");
                }
            } catch (NullPointerException | IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private Reserva[] getReservasAnulables(Reserva[] reservasAAnular) {
        if (reservasAAnular==null){
            return null;
            //throw new NullPointerException("ERROR: El listado de reservas está vacío.");
        }
        Reserva[] reservasAnulables=new Reserva[reservasAAnular.length];
        int reser = 0;
        for (int i=0 ; i<reservasAAnular.length ; i++){
            if (reservasAAnular[i]!=null){
                if (reservasAAnular[i].getFechaInicioReserva().isAfter(LocalDate.now())){
                    reservasAnulables[reser]=new Reserva(reservasAAnular[i]);
                    reser++;
                }
            }
        }
        return reservasAnulables;
    }
    private void anularReserva() throws NullPointerException, IllegalArgumentException {
        int reservasAnulables = 0;
        String eleccion;
        Huesped huesped = new Huesped(Consola.getHuespedPorDni());
        Reserva[] reservas1 = new Reserva[controlador.getReservas().length];
        reservas1 = getReservasAnulables(controlador.getReservas(huesped));
        if (reservas1==null){
            throw new NullPointerException("ERROR: No hay reservas anulables para ese cliente.");
        } else if (getNumElementosNoNulos(reservas1)==1) {
            do {
                System.out.println("¿Confirma que desea eliminar la reserva (S/N): " + reservas1[0].toString() + " ?");
                eleccion = Entrada.cadena();
                if (eleccion.equalsIgnoreCase("S")) {
                    try {
                        controlador.borrar(reservas1[0]);
                    } catch (OperationNotSupportedException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Reserva eliminada.");
                }
            } while (!eleccion.equalsIgnoreCase("s")&&!eleccion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija la que desea eliminar: ");
            for (Reserva reserva : reservas1){
                if (reserva!=null){
                    System.out.println(reservasAnulables + ": " + reserva);
                    reservasAnulables++;
                }
            }
            do {
                eleccion = Entrada.cadena();
            } while (Integer.parseInt(eleccion) > reservasAnulables || Integer.parseInt(eleccion) < 0);
            try {
                controlador.borrar(reservas1[Integer.parseInt(eleccion)]);
            } catch (OperationNotSupportedException | NullPointerException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Reserva eliminada.");
        }
    }
    private void mostrarReservas() throws NullPointerException {
        Reserva[] reservas1 = new Reserva[controlador.getReservas().length];
        reservas1 = controlador.getReservas();
        int numReservas = 0;
        if (reservas1==null){
            throw new NullPointerException("ERROR: No hay reservas almacenadas.");
        }
        for (Reserva reserva2 : reservas1){
            if (reserva2!=null){
                System.out.println(reserva2.toString());
                numReservas++;
            }
        }
        if (numReservas==0){
            System.out.println("No hay reservas para mostrar.");
        }
    }

    private Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        List<Habitacion> habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado[i]);
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que está disponible.
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado[i]);
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                    Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas están algún hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return habitacionDisponible;
    }

    private int getNumElementosNoNulos(Reserva[] reserva) throws NullPointerException, IllegalArgumentException {
        int contadorNoNulos = 0;
        if (reserva==null){
            throw new NullPointerException("ERROR: El listado de reservas está vacío.");
        }
        for (Reserva reserva1: reserva){
            if (reserva1!=null){
                contadorNoNulos++;
            }
        }
        /*if (reser==0){
            throw new IllegalArgumentException("ERROR: El listado de reservas no contiene reservas válidas.");
        }*/
        return contadorNoNulos;
    }

    private void realizarCheckin() throws NullPointerException, IllegalArgumentException {
        Reserva[] reservas;
        Reserva reserva = null;
        LocalDateTime fecha = null;
        Huesped huesped;
        String confirmacion;
        int numOpcion=0, contador=0;
        huesped=Consola.getHuespedPorDni();
        reservas=controlador.getReservas(huesped);
        if (reservas==null){
            throw new NullPointerException("ERROR: El cliente no tiene reservas.");
        }
        else if (getNumElementosNoNulos(reservas)==1) {
            do {
                System.out.println("¿Confirma que desea realizar el CheckIn para la reserva (S/N): " + reservas[0].toString() + " ?");
                confirmacion = Entrada.cadena();
                if (confirmacion.equalsIgnoreCase("S")) {
                    reserva=reservas[0];
                }
            } while (!confirmacion.equalsIgnoreCase("s")&&!confirmacion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija para la que quiere hacer el CheckIn: ");
            for (Reserva reservaFor : reservas) {
                if (reservaFor != null) {
                    System.out.println(contador + ": " + reservaFor);
                    contador++;
                }
            }
            do {
                numOpcion = Entrada.entero();
            } while (numOpcion < 0 || numOpcion > contador);
            reserva=reservas[numOpcion];
        }
        do{
            if (fecha!=null){
                System.out.print("Siga el patrón para la fecha. ");
            }
            System.out.println("Introduzca la fecha y hora de entrada (dd/MM/yy HH:mm): ");
            fecha=Consola.leerFechaHora(Entrada.cadena());
        } while (fecha.isBefore(LocalDate.now().atStartOfDay()));

        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede introducir una reserva nula.");
        }
        if (reserva.getCheckIn()!=null) {
            throw new IllegalArgumentException("ERROR: No se puede modificar un CheckIn.");
        }
        else {
            try {
                controlador.realizarCheckin(reserva, fecha);
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void realizarCheckout() throws NullPointerException, IllegalArgumentException {
        Reserva[] reservas;
        Reserva reserva = null;
        LocalDateTime fecha = null;
        Huesped huesped;
        String confirmacion;
        int numOpcion=0, contador=0;
        huesped=Consola.getHuespedPorDni();
        reservas=controlador.getReservas(huesped);
        if (reservas==null){
            throw new NullPointerException("ERROR: El cliente no tiene reservas.");
        }
        else if (getNumElementosNoNulos(reservas)==1) {
            do {
                System.out.println("¿Confirma que desea realizar el CheckOut para la reserva (S/N): " + reservas[0].toString() + " ?");
                confirmacion = Entrada.cadena();
                if (confirmacion.equalsIgnoreCase("S")) {
                    reserva=reservas[0];
                }
            } while (!confirmacion.equalsIgnoreCase("s")&&!confirmacion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija para la que quiere hacer el CheckOut: ");
            for (Reserva reservaFor : reservas) {
                if (reservaFor != null) {
                    System.out.println(contador + ": " + reservaFor);
                    contador++;
                }
            }
            do {
                numOpcion = Entrada.entero();
            } while (numOpcion < 0 || numOpcion > contador);
            reserva = reservas[numOpcion];
        }
        do{
            if (fecha!=null){
                System.out.print("Siga el patrón para la fecha. ");
            }
            System.out.println("Introduzca la fecha y hora de entrada (dd/MM/yy HH:mm): ");
            fecha=Consola.leerFechaHora(Entrada.cadena());
        } while (fecha.isBefore(LocalDate.now().atStartOfDay()));
        if(reserva==null){
            throw new NullPointerException("ERROR: No se puede introducir una reserva nula.");
        }
        try {
            controlador.realizarCheckout(reserva, fecha);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    private void inicializarDatos () throws NullPointerException, IllegalArgumentException, OperationNotSupportedException{
        Huesped huesped =  new Huesped("Justo Lopez", "45596798b", "justolm@gmail.com", "666619806", LocalDate.of(1980, 11, 19));
        Huesped huesped1 = new Huesped("Noe Lilla", "11111111h", "noe@lilla.es", "650476674", LocalDate.of(1982, 11, 7));
        Habitacion habitacion = new Habitacion(1,1,45.0, TipoHabitacion.DOBLE);
        Habitacion habitacion1 = new Habitacion(3,13,80,TipoHabitacion.SUITE);
        Reserva reserva = new Reserva(huesped, habitacion1, Regimen.MEDIA_PENSION, LocalDate.now(),LocalDate.now().plusDays(2),2);
        Reserva reserva1 = new Reserva(huesped, habitacion, Regimen.SOLO_ALOJAMIENTO, LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 15), 2);
        controlador.insertar(huesped);
        controlador.insertar(huesped1);
        controlador.insertar(habitacion);
        controlador.insertar(habitacion1);
        controlador.insertar(reserva);
        controlador.insertar(reserva1);
        System.out.println("Datos inicializados. ");
    }
}
