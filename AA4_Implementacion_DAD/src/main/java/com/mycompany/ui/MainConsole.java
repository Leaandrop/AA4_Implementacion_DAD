package com.mycompany.ui;

import com.mycompany.factory.PrendaFactory;
import com.mycompany.model.*;
import com.mycompany.service.NegocioAlquiler;
import com.mycompany.util.DBInit;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Menú por consola para probar todo rápido.
 */
public class MainConsole {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Asegurar que las tablas existen
        DBInit.init();

        try {
            NegocioAlquiler negocio = NegocioAlquiler.getInstancia();
            int op;
            do {
                menu();
                op = leerInt("Opción: ");
                switch (op) {
                    case 1 ->
                        registrarCliente(negocio);
                    case 2 ->
                        registrarEmpleado(negocio);
                    case 3 ->
                        registrarPrenda(negocio);
                    case 4 ->
                        crearServicio(negocio);
                    case 5 ->
                        consultarServiciosPorCliente(negocio);
                    case 6 ->
                        listarPrendasDisponibles(negocio);
                    case 7 ->
                        registrarLavanderia(negocio);
                    case 8 ->
                        mostrarLavanderia(negocio);
                    case 9 ->
                        enviarLavanderia(negocio);
                    case 0 ->
                        System.out.println("Saliendo...");
                    default ->
                        System.out.println("Opción inválida.");
                }
            } while (op != 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void menu() {
        System.out.println("\n=== LOS ATUENDOS ===");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Registrar Empleado");
        System.out.println("3. Registrar Prenda (Factory)");
        System.out.println("4. Crear Servicio de Alquiler");
        System.out.println("5. Consultar Servicios por Cliente");
        System.out.println("6. Listar Prendas Disponibles por Fecha");
        System.out.println("7. Lavandería: Registrar prenda (prio)");
        System.out.println("8. Lavandería: Mostrar cola");
        System.out.println("9. Lavandería: Enviar N");
        System.out.println("0. Salir");
    }

    // --- Acciones ---
    private static void registrarCliente(NegocioAlquiler negocio) throws Exception {
        String id = leer("ID cliente: ");
        String nombre = leer("Nombre: ");
        String dir = leer("Dirección: ");
        String tel = leer("Teléfono: ");
        String mail = leer("Mail: ");
        Cliente c = new Cliente(id, nombre, dir, tel, mail);
        System.out.println(negocio.registrarCliente(c) ? "Cliente guardado." : "No se guardó.");
    }

    private static void registrarEmpleado(NegocioAlquiler negocio) throws Exception {
        String id = leer("ID empleado: ");
        String nombre = leer("Nombre: ");
        String dir = leer("Dirección: ");
        String tel = leer("Teléfono: ");
        String cargo = leer("Cargo: ");
        Empleado e = new Empleado(id, nombre, dir, tel, cargo);
        System.out.println(negocio.registrarEmpleado(e) ? "Empleado guardado." : "No se guardó.");
    }

    private static void registrarPrenda(NegocioAlquiler negocio) throws Exception {
        String tipo = leer("Tipo (disfraz | trajecaballero | vestidodama): ").toLowerCase();
        String ref = leer("Ref: ");
        String color = leer("Color: ");
        String marca = leer("Marca: ");
        String talla = leer("Talla: ");
        double valor = leerDouble("Valor alquiler: ");
        Prenda p;

        switch (tipo) {
            case "disfraz" -> {
                String nombre = leer("Nombre del disfraz: ");
                p = PrendaFactory.crearPrenda("disfraz", ref, color, marca, talla, valor, nombre);
            }
            case "trajecaballero" -> {
                String t = leer("Tipo (frac/convencional/...): ");
                String ad = leer("Aderezo (corbata/corbatín/...): ");
                p = PrendaFactory.crearPrenda("trajecaballero", ref, color, marca, talla, valor, t, ad);
            }
            case "vestidodama" -> {
                boolean ped = leerBoolean("¿Pedrería? (s/n): ");
                String alt = leer("Altura (Corto/Medio/Largo): ");
                short piezas = (short) leerInt("Cant. piezas: ");
                p = PrendaFactory.crearPrenda("vestidodama", ref, color, marca, talla, valor, ped, alt, piezas);
            }
            default -> {
                System.out.println("Tipo no válido.");
                return;
            }
        }
        System.out.println(negocio.registrarPrenda(p) ? "Prenda guardada." : "No se guardó.");
    }

    private static void crearServicio(NegocioAlquiler negocio) throws Exception {
        int numero = leerInt("Número de servicio: ");
        String idCli = leer("ID Cliente: ");
        String idEmp = leer("ID Empleado: ");
        String refsStr = leer("Refs de prendas (separadas por coma): ");
        List<String> refs = Arrays.stream(refsStr.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        Date fecha = leerFecha("Fecha alquiler (YYYY-MM-DD): ");

        boolean ok = negocio.crearServicioAlquiler(numero, idCli, idEmp, refs, fecha);
        System.out.println(ok ? ("Servicio #" + numero + " creado.") : "No se pudo crear.");
    }

    private static void consultarServiciosPorCliente(NegocioAlquiler negocio) throws Exception {
        String idCli = leer("ID Cliente: ");
        List<ServicioAlquiler> list = negocio.consultarServicioPorCliente(idCli);
        if (list == null || list.isEmpty()) {
            System.out.println("Sin servicios.");
        } else {
            for (ServicioAlquiler s : list) {
                System.out.println("Servicio #" + s.getNumero()
                        + (s.getFechaAlqui() != null ? " - Fecha: " + s.getFechaAlqui() : ""));
            }
        }
    }

    private static void listarPrendasDisponibles(NegocioAlquiler negocio) throws Exception {
        Date fecha = leerFecha("Fecha (YYYY-MM-DD): ");
        List<Prenda> disp = negocio.listarPrendasDisponibles(fecha);
        if (disp == null || disp.isEmpty()) {
            System.out.println("No hay disponibles.");
        } else {
            System.out.println("Disponibles:");
            for (Prenda p : disp) {
                System.out.println(" - " + p.getRef() + " :: " + p.descripcion());
            }
        }
    }

    private static void registrarLavanderia(NegocioAlquiler negocio) throws Exception {
        String ref = leer("Ref prenda: ");
        int prio = leerInt("Prioridad (entero, mayor = sale primero): ");
        negocio.registrarLavanderia(ref, prio);
        System.out.println("Prenda encolada.");
    }

    private static void mostrarLavanderia(NegocioAlquiler negocio) {
        System.out.println("Cola: " + negocio.mostrarLavanderia());
    }

    private static void enviarLavanderia(NegocioAlquiler negocio) {
        int n = leerInt("Cantidad a enviar: ");
        List<Prenda> enviados = negocio.enviarLavanderia(n);
        System.out.println("Enviados: " + enviados.stream().map(Prenda::getRef).collect(Collectors.toList()));
    }

    // --- Helpers de entrada ---
    private static String leer(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static int leerInt(String msg) {
        return Integer.parseInt(leer(msg));
    }

    private static double leerDouble(String msg) {
        return Double.parseDouble(leer(msg));
    }

    private static boolean leerBoolean(String msg) {
        String v = leer(msg).toLowerCase();
        return v.startsWith("s") || v.equals("true") || v.equals("1");
    }

    private static Date leerFecha(String msg) {
        return Date.valueOf(LocalDate.parse(leer(msg)));
    }
}
