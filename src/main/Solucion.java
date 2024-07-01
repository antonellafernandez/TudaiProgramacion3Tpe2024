package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solucion {
    private Map<Procesador, List<Tarea>> asignacion;
    private int tiempoMaximo;

    public Solucion() {
        this.tiempoMaximo = Integer.MAX_VALUE;
        this.asignacion = new HashMap<>(asignacion);
    }
    public Solucion(List<Procesador> procesadores){
        this.tiempoMaximo = Integer.MAX_VALUE;
        this.asignacion = new HashMap<>();

        for (Procesador procesador : procesadores){
            asignacion.put(procesador, new LinkedList<>());
        }
    }

    public int getTiempoMaximo() {
        return tiempoMaximo;
    }

    public void setTiempoMaximo(int tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    public Map<Procesador, List<Tarea>> getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Solucion solucion) {
        this.tiempoMaximo = solucion.tiempoMaximo;
        Map<Procesador, List<Tarea>> copia = new HashMap<>();
        for (Procesador procesador : solucion.asignacion.keySet()){
            copia.put(procesador, new LinkedList<>());
            for (Tarea tarea : solucion.asignacion.get(procesador)){
                copia.get(procesador).add(tarea);
            }
        }
        this.asignacion = copia;
    }


    // Se fija la cantidad de tareas asignadas de cada procesador para la solución
    public int getCantTareasAsignadas() {
        int totalTareas = 0;

        for (Procesador procesador : this.asignacion.keySet()){
            totalTareas += this.asignacion.get(procesador).size();
        }

        return totalTareas;
    }

    protected boolean puedeAsignar(Procesador procesador, Tarea tarea, int tiempoMaxNoRefrigerado) {
        if (!tarea.getCritica() && procesador.getRefrigerado()){
            return true;
        } else if(tarea.getCritica() && procesador.getRefrigerado()) {
            return tieneMenosDeDosTareasCriticas(procesador, tarea);
        } else if (tarea.getCritica() && !procesador.getRefrigerado()){
            return tieneMenosDeDosTareasCriticas(procesador, tarea) && calcularTiempoTotal(procesador, tarea, tiempoMaxNoRefrigerado);
        }
        // La tarea no es crítica y el procesador no es refrigerado
        return calcularTiempoTotal(procesador, tarea, tiempoMaxNoRefrigerado);
    }

    private boolean tieneMenosDeDosTareasCriticas(Procesador procesador, Tarea tarea) {
        int tareasCriticas = 0;

        for (Tarea tareaActual : this.asignacion.get(procesador)){
            if (tareaActual.getCritica())
                tareasCriticas++;
        }

        return tareasCriticas < 2;
    }

    private boolean calcularTiempoTotal(Procesador procesador, Tarea tarea, int tiempoMaxNoRefrigerado){
        int tiempoProcesador = 0;

        for (Tarea tareaActual : this.asignacion.get(procesador)){
            tiempoProcesador += tareaActual.getTiempo();
        }

        return ((tiempoProcesador + tarea.getTiempo()) <= tiempoMaxNoRefrigerado);
    }

    //Asigna una tarea al procesador
    protected void asignarTarea(Procesador procesador, Tarea tarea) {
        if (this.asignacion.containsKey(procesador) && !asignacion.get(procesador).contains(tarea)){
            List<Tarea> listaTareas = asignacion.get(procesador);
            listaTareas.add(tarea);
            this.asignacion.put(procesador,listaTareas);
            this.modificarTiempoMaximo();
        }
    }

    private void modificarTiempoMaximo(){
        int tiempoActual = 0;

        for (List<Tarea> list : asignacion.values()){
            int tiempoParcialTarea = 0;

            for (Tarea tarea : list){
                tiempoParcialTarea += tarea.getTiempo();
            }

            if (tiempoParcialTarea > tiempoActual){
                tiempoActual = tiempoParcialTarea;
            }
        }

        this.setTiempoMaximo(tiempoActual);
    }

    //Desasigna una tarea al procesador
    protected void desasignarTarea(Procesador procesador,Tarea tarea) {
        if (this.asignacion.containsKey(procesador)){
            asignacion.get(procesador).remove(tarea);
            this.modificarTiempoMaximo();
        }
    }

    public int getTiempoEjecucionTareas(){
        int tiempoTotal = 0;

        if (this.asignacion != null){
            for (Procesador procesador : this.asignacion.keySet()){
                if (this.calcularTiempoEjecucionTareas(procesador) > tiempoTotal){
                    tiempoTotal = this.calcularTiempoEjecucionTareas(procesador);
                }
            }
        }

        return tiempoTotal;
    }

    protected int calcularTiempoEjecucionTareas(Procesador procesador) {
        int tiempo = 0;
        List<Tarea> tareas = this.asignacion.get(procesador);

        for (Tarea tarea : tareas){
            tiempo += tarea.getTiempo();
        }

        return tiempo;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Procesador, List<Tarea>> entry : asignacion.entrySet()) {
            Procesador procesador = entry.getKey();
            List<Tarea> tareas = entry.getValue();

            sb.append(procesador.toString()).append(" ->"); // Información del procesador

            for (Tarea tarea : tareas) {
                sb.append(" " + tarea.toString()); // Información de cada tarea
            }

            sb.append("\n"); // Salto de línea entre procesadores
        }

        sb.append("\nTiempo máximo de ejecución: ").append(this.getTiempoEjecucionTareas());

        return sb.toString();
    }
}
