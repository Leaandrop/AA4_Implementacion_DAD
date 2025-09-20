/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lavanderia;

import com.mycompany.model.Prenda;
import java.util.*;

/**
 * Cola prioritaria para prendas a lavandería. Prioridad mayor sale primero; a
 * igualdad, orden FIFO.
 */
public class LavanderiaQueue {

    private static class Nodo {

        final Prenda prenda;
        final int prioridad;
        final long ts; // para desempatar como FIFO

        Nodo(Prenda p, int prio, long ts) {
            this.prenda = p;
            this.prioridad = prio;
            this.ts = ts;
        }
    }

    private final PriorityQueue<Nodo> pq = new PriorityQueue<>(
            Comparator.<Nodo>comparingInt(n -> n.prioridad).reversed()
                    .thenComparingLong(n -> n.ts)
    );

    public void encolar(Prenda p, int prioridad) {
        pq.offer(new Nodo(p, prioridad, System.nanoTime()));
    }

    public List<Prenda> extraer(int cantidad) {
        List<Prenda> out = new ArrayList<>();
        for (int i = 0; i < cantidad && !pq.isEmpty(); i++) {
            out.add(pq.poll().prenda);
        }
        return out;
    }

    public List<String> asListDescripcion() {
        List<Nodo> copia = new ArrayList<>(pq);
        copia.sort(Comparator.<Nodo>comparingInt(n -> n.prioridad).reversed()
                .thenComparingLong(n -> n.ts));
        List<String> out = new ArrayList<>();
        for (Nodo n : copia) {
            out.add(n.prenda.getRef() + " (prio " + n.prioridad + ")");
        }
        return out;
    }
}
