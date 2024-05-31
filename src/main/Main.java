package main;

public class Main {
    public static void main(String[] args) {
        Servicios servicios = new Servicios("./src/datasets/Tareas.csv","./src/datasets/Procesadores.csv");

        // Servicio 1  : Dado un identificador de tarea obtener  toda la información de la tarea asociada.
        System.out.println("Servicio 1");
        System.out.println(servicios.servicio1("T3"));
        System.out.println();

        // Servicio 2  : Permitir que el usuario decida si quiere  ver todas las tareas críticas
        // o no críticas y generar el listado apropiado resultante.
        System.out.println("Servicio 2");
        System.out.println(servicios.servicio2(true));
        System.out.println();

        // Servicio 3  : Obtener todas las tareas entre 2 niveles  de prioridad indicados.
        System.out.println("Servicio 3");
        System.out.println(servicios.servicio3(69,100));
        System.out.println();

        // Backtracking
        Solucion solucionB = servicios.ejecutarBacktracking(0, 50);

        // Imprimir la Solución Backtracking
        System.out.println(solucionB);

        // Greedy
        Solucion solucionG = servicios.ejecutarGreedy(0, 50);

        // Imprimir la Solución Greedy
        System.out.println(solucionG);
    }
}