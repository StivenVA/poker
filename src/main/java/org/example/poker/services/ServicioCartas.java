package org.example.poker.services;

import org.example.poker.model.Carta;
import org.example.poker.util.GeneradorMazo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class ServicioCartas {
    public static Stack<Carta> baraja = GeneradorMazo.generarMazoBarajado();
    public static List<Carta> descartadas = new ArrayList<>();

    public Carta sacarCarta() {
        return baraja.pop();
    }

    public void barajar() {
        baraja = GeneradorMazo.barajarMazo(baraja);
    }

    public void descartar(Carta carta) {
        descartadas.add(carta);
    }

    public void ponerCartasEnBaraja(List<Carta> cartas) {
        baraja.addAll(cartas);
    }
}
