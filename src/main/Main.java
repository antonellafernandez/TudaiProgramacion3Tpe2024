package main;

public class Main {
    public static void main(String[] args) {
        Servicios servicios = new Servicios("./src/datasets/Procesadores.csv", "./src/datasets/Tareas2.csv");
        Servicios2 servicios2 = new Servicios2("./src/datasets/Procesadores.csv", "./src/datasets/Tareas2.csv");

        // Servicio 1: Dado un identificador de tarea obtener toda la información de la tarea asociada.
        System.out.println("==================== Servicio 1 ====================");
        System.out.println(servicios.servicio1("T3"));
        System.out.println();

        // Servicio 2: Permitir que el usuario decida si quiere ver todas las tareas críticas
        // o no críticas y generar el listado apropiado resultante.
        System.out.println("==================== Servicio 2 ====================");
        System.out.println(servicios.servicio2(true));
        System.out.println();

        // Servicio 3: Obtener todas las tareas entre 2 niveles de prioridad indicados.
        System.out.println("==================== Servicio 3 ====================");
        System.out.println(servicios.servicio3(32,91));
        System.out.println();

        // Solución Backtracking
        System.out.println("==================== Solución Backtracking ====================");
        servicios2.backtracking(200);
        System.out.println();

        // Solución Greedy
        System.out.println("==================== Solución Greedy ====================");
        servicios2.greedy(200);
        System.out.println();
    }
}