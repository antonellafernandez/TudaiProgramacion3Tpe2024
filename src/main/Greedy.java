package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Greedy extends Solucion {
    private int cantidadCandidatosConsiderados;

    public Greedy(int tiempoMaximo, HashMap<String, Procesador> procesadores) {
        super(tiempoMaximo, procesadores);
        cantidadCandidatosConsiderados = 0;
    }

    @Override
    public Solucion resolver(int tiempoMaximoNoRefrigerado, HashMap<String, Procesador> procesadores,
                             ArrayList<Tarea> tareas) {

        return this;
    }

    @Override
    public String toString() {
        return "Greedy{" + super.toString() +
                "Costo de la solución (cantidad de candidatos considerados): " + cantidadCandidatosConsiderados +
                '}';
    }
}
