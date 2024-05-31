package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Solucion {
    private Map<Procesador, List<Tarea>> asignacion;
    private int tiempoMaximo;

    public Solucion(int tiempoMaximo, HashMap<String, Procesador> procesadores) {
        this.asignacion = new HashMap<>();
        this.tiempoMaximo = tiempoMaximo;

        // Inicializar asignación para cada procesador
        for (Procesador procesador : procesadores.values()) {
            this.getAsignacion().put(procesador, new ArrayList<>());
        }
    }

    public Map<Procesador, List<Tarea>> getAsignacion() {
        return asignacion;
    }

    public int getTiempoMaximo() {
        return tiempoMaximo;
    }

    public void setTiempoMaximo(int tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    public abstract Solucion resolver(int tiempoMaximoNoRefrigerado, HashMap<String,
            Procesador> procesadores, ArrayList<Tarea> tareas);

    @Override
    public String toString() {
        return "Cada procesador con las tareas asignadas: " + asignacion +
                ", Tiempo máximo de ejecución: " + tiempoMaximo;
    }
}