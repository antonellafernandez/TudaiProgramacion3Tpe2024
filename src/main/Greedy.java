package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Greedy {
    private int cantidadCandidatosConsiderados;

    public Greedy() {
        cantidadCandidatosConsiderados = 0;
    }

    public int getCantidadCandidatosConsiderados() {
        return cantidadCandidatosConsiderados;
    }

    // Complejidad: O(n * m) donde n es el número de tareas y m es el número de procesadores
    public Solucion resolver(int tiempoMaximoNoRefrigerado, List<Tarea> listaTareas, List<Procesador> listaProcesadores) {
        Map<Procesador, List<Tarea>> asignacion = new HashMap<>();
        Map<Procesador, Integer> tiemposProcesadores = new HashMap<>();

        // Inicializar las asignaciones y los tiempos de procesadores
        for (Procesador procesador : listaProcesadores) {
            asignacion.put(procesador, new ArrayList<>());
            tiemposProcesadores.put(procesador, 0);
        }

        Solucion s = new Solucion(asignacion, 0, 0); // Conjunto solución, inicialmente vacío

        // Iterar sobre cada tarea
        for (Tarea tarea : listaTareas) {
            Procesador mejorProcesador = null;
            int minTiempo = Integer.MAX_VALUE;

            // Evaluar cada procesador para la tarea actual
            for (Procesador procesador : listaProcesadores) {
                cantidadCandidatosConsiderados++;
                List<Tarea> tareasAsignadas = s.getAsignacion().get(procesador);

                // Verificar si se puede asignar la tarea al procesador actual
                if (puedeAsignar(tarea, procesador, tareasAsignadas, tiempoMaximoNoRefrigerado)) {
                    int tiempoProcesador = tiemposProcesadores.get(procesador);

                    // Encontrar el procesador con el mínimo tiempo acumulado
                    if (tiempoProcesador < minTiempo) {
                        minTiempo = tiempoProcesador;
                        mejorProcesador = procesador;
                    }
                }
            }

            // Asignar la tarea al mejor procesador encontrado
            if (mejorProcesador != null) {
                asignarTarea(tarea, mejorProcesador, s.getAsignacion());
                tiemposProcesadores.put(mejorProcesador, tiemposProcesadores.get(mejorProcesador) + tarea.getTiempo());
            } else {
                System.out.println("No se pudo asignar la tarea: " + tarea);
            }
        }

        // Calcular el tiempo máximo de ejecución final
        int totalTiempoFinal = 0;
        for (Procesador procesador : listaProcesadores) {
            totalTiempoFinal = Math.max(totalTiempoFinal, tiemposProcesadores.get(procesador));
        }

        s.setTiempoMaximo(totalTiempoFinal);
        s.setCostoSolucion(cantidadCandidatosConsiderados);

        return s;
    }

    // Complejidad: O(n) donde n es el número de tareas asignadas al procesador
    private boolean puedeAsignar(Tarea tarea, Procesador procesador, List<Tarea> tareasAsignadas, int tiempoMaximoNoRefrigerado) {
        // Verificar si el procesador ya tiene más de dos tareas críticas asignadas
        if (tieneMasDeDosTareasCriticas(tareasAsignadas)) {
            return false;
        }

        // Verificar si el procesador es refrigerado
        if (procesador.getRefrigerado()) {
            return true;
        } else {
            // Calcular el tiempo total de ejecución si se añade una nueva tarea
            int tiempoTotal = calcularTiempoTotal(tareasAsignadas) + tarea.getTiempo();
            return tiempoTotal <= tiempoMaximoNoRefrigerado; // Verificar si el tiempo total no excede el máximo permitido sin refrigeración
        }
    }

    // Complejidad: O(n) donde n es el número de tareas asignadas al procesador
    private boolean tieneMasDeDosTareasCriticas(List<Tarea> tareasAsignadas) {
        int cantidadTareasCriticas = 0;

        for (Tarea tarea : tareasAsignadas) {
            if (tarea.getCritica()) {
                cantidadTareasCriticas++;

                if (cantidadTareasCriticas >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    // Complejidad: O(n) donde n es el número de tareas
    private int calcularTiempoTotal(List<Tarea> tareas) {
        int tiempoTotal = 0;

        for (Tarea t : tareas) {
            tiempoTotal += t.getTiempo();
        }
        return tiempoTotal;
    }

    // Complejidad: O(1) ya que solo se agrega una tarea a la lista
    private void asignarTarea(Tarea tarea, Procesador procesador, Map<Procesador, List<Tarea>> asignacion) {
        List<Tarea> tareasAsignadas = asignacion.get(procesador);
        tareasAsignadas.add(tarea);
    }
}
