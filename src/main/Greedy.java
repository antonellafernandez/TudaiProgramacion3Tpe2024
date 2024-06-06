package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Greedy extends Solucion {
    private int cantidadCandidatosConsiderados;

    public Greedy(int tiempoMaximo, HashMap<String, Procesador> procesadores) {
        super(tiempoMaximo, procesadores);
        cantidadCandidatosConsiderados = 0;
    }

    @Override
    public Solucion resolver(int tiempoMaximoNoRefrigerado, HashMap<String, Procesador> procesadores,
                             ArrayList<Tarea> tareas) {
        ArrayList<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());

        for (Tarea tarea : tareas) {
            Procesador mejorProcesador = null;
            int minTiempo = Integer.MAX_VALUE;

            for (Procesador procesador : listaProcesadores) {
                if (puedeAsignar(tarea, procesador, tiempoMaximoNoRefrigerado)) {
                    int tiempoProcesador = getTiempoProcesador(procesador);
                    if (tiempoProcesador < minTiempo) {
                        minTiempo = tiempoProcesador;
                        mejorProcesador = procesador;
                    }
                }
            }

            if (mejorProcesador != null) {
                asignarTarea(tarea, mejorProcesador);
            } else {
                System.out.println("No se pudo asignar la tarea: " + tarea);
            }
        }

        // Calcular el tiempo máximo de ejecución final
        int maxTiempoFinal = 0;

        for (Procesador procesador : listaProcesadores) {
            maxTiempoFinal = Math.max(maxTiempoFinal, getTiempoProcesador(procesador));
        }

        this.setTiempoMaximo(maxTiempoFinal);

        return this;
    }

    private boolean puedeAsignar(Tarea tarea, Procesador procesador, int tiempoMaximoNoRefrigerado) {
        // Verificar si el procesador no refrigerado no excede el tiempo míximo permitido
        if (!procesador.getRefrigerado() && getTiempoProcesador(procesador) + tarea.getTiempo() > tiempoMaximoNoRefrigerado) {
            return false;
        }

        // Verificar si la última tarea asignada al procesador es crítica
        // y si esta nueva tarea es crítica tambiín
        if (tarea.getCritica() && ultimaTareaAsignadaEsCritica(procesador)) {
            return false;
        }

        return true;
    }

    private boolean ultimaTareaAsignadaEsCritica(Procesador procesador) {
        List<Tarea> tareasAsignadas = this.getAsignacion().get(procesador);

        if (!tareasAsignadas.isEmpty()) {
            Tarea ultimaTareaAsignada = tareasAsignadas.get(tareasAsignadas.size() - 1);
            return ultimaTareaAsignada.getCritica();
        }

        return false;
    }

    private int getTiempoProcesador(Procesador procesador) {
        int tiempoTotal = 0;

        for (Tarea tarea : this.getAsignacion().get(procesador)) {
            tiempoTotal += tarea.getTiempo();
        }

        return tiempoTotal;
    }

    private void asignarTarea(Tarea tarea, Procesador procesador) {
        List<Tarea> tareasAsignadas = this.getAsignacion().get(procesador);
        tareasAsignadas.add(tarea);
    }

    @Override
    public String toString() {
        return "Greedy{" + super.toString() +
                "Costo de la solución (cantidad de candidatos considerados): " + cantidadCandidatosConsiderados +
                '}';
    }
}
