package org.example.poker.util;

import org.example.poker.model.Carta;

import java.util.Stack;

public class GeneradorMazo {

    public static Stack<Carta> generarMazo(){
        Stack<Carta> mazo = new Stack<>();
        String[] palos = {"Corazones", "Diamantes", "Picas", "Treboles"};
        String[] valores = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        int[] numeroCartas = {1,2,3,4,5,6,7,8,9,10,11,12,13};
        for (String palo : palos) {
            for (int i = 0; i < valores.length; i++) {
                mazo.push(new Carta(palo, valores[i], numeroCartas[i]));
            }
        }
        return mazo;
    }

    public static Stack<Carta> generarMazoBarajado(){
        return barajarMazo(generarMazo());
    }

    public static Stack<Carta> barajarMazo(Stack<Carta> mazo){
        Stack<Carta> mazoBarajado = new Stack<>();
        while (!mazo.isEmpty()) {
            int randIndex = (int) (Math.random() * mazo.size());
            mazoBarajado.push(mazo.get(randIndex));
            mazo.remove(randIndex);
        }
        return mazoBarajado;
    }

}
