package org.example.poker.util;

import org.example.poker.model.Carta;
import org.example.poker.model.Jugador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComprobarGanador {
    public static Jugador comprobarGanador(Jugador[] jugadores) {

        Jugador ganador = jugadores[0];

        for (Jugador jugador : jugadores) {
            comprobarValorMano(jugador);
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getValorMano() > ganador.getValorMano()) {
                ganador = jugador;
            }
        }

        for (Jugador jugadore : jugadores) {
            if (jugadore.getId().equals(ganador.getId())) continue;
            if (jugadore.getValorMano() == ganador.getValorMano()) {
                List<Carta> manoJugador = manoAuxiliar(jugadore.getMano());
                List<Carta> manoGanador = manoAuxiliar(ganador.getMano());

                if (manoJugador.get(4).getNumeroCarta() > manoGanador.get(4).getNumeroCarta()) {
                    ganador = jugadore;
                }
            }
        }

        return ganador;
    }

    public static void comprobarValorMano(Jugador jugador) {

        List<Carta> mano = jugador.getMano();

        String paloPrimerCarta = mano.get(0).getPalo();

        boolean mismoPalo = mano.stream().allMatch(carta -> carta.getPalo().equals(paloPrimerCarta));

        if (mismoPalo) {
            jugador.setValorMano(manosColor(mano));
        } else {
            if (comprobarPoker(mano)) {
                jugador.setValorMano(Manos.POKER);
            }
            if (comprobarEscalera(mano)) {
                jugador.setValorMano(Manos.ESCALERA);
            }
            if (comprobarFull(mano)) {
                jugador.setValorMano(Manos.FULL);
            }
            if (comprobarTrio(mano)) {
                jugador.setValorMano(Manos.TRIO);
            }
            if (comprobarDoblePar(mano)) {
                jugador.setValorMano(Manos.DOBLE_PAR);
            }
            if (comprobarPar(mano)) {
                jugador.setValorMano(Manos.PAR);
            }
            else jugador.setValorMano(Manos.CARTA_ALTA);

        }

    }

    private static int manosColor(List<Carta> mano) {


        if (comprobarEscaleraReal(mano)) {
            return Manos.ESCALERA_REAL;
        }

        if (comprobarEscalera(mano)) {

            return Manos.ESCALERA_COLOR;
        }

        return Manos.COLOR;
    }


    public static boolean comprobarEscaleraReal(List<Carta> mano) {

        List<Carta> manoAuxiliar = manoAuxiliar(mano);

        return manoAuxiliar.get(0).getValor().equals("10") &&
            manoAuxiliar.get(1).getValor().equals("J") &&
            manoAuxiliar.get(2).getValor().equals("Q") &&
            manoAuxiliar.get(3).getValor().equals("K") &&
            manoAuxiliar.get(4).getValor().equals("A");

    }

    public static boolean comprobarEscalera(List<Carta> mano) {

        List<Carta> manoAuxiliar = manoAuxiliar(mano);

        return manoAuxiliar.get(0).getNumeroCarta() == manoAuxiliar.get(1).getNumeroCarta() - 1 &&
            manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() - 1 &&
            manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() - 1 &&
            manoAuxiliar.get(3).getNumeroCarta() == manoAuxiliar.get(4).getNumeroCarta() - 1;
    }

    public static boolean comprobarPoker(List<Carta> mano){

        List<Carta> manoAuxiliar = manoAuxiliar(mano);

        return manoAuxiliar.get(0).getNumeroCarta() == manoAuxiliar.get(1).getNumeroCarta() &&
            manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() &&
            manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() ||
            manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() &&
            manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() &&
            manoAuxiliar.get(3).getNumeroCarta() == manoAuxiliar.get(4).getNumeroCarta();
    }

    public static boolean comprobarFull(List<Carta> mano){
        return comprobarPar(mano) && comprobarTrio(mano);
    }

    public static boolean comprobarDoblePar(List<Carta> mano) {
        List<Carta> manoAuxiliar = manoAuxiliar(mano);

        return manoAuxiliar.get(0).getNumeroCarta() == manoAuxiliar.get(1).getNumeroCarta() &&
                manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() ||
                manoAuxiliar.get(0).getNumeroCarta() == manoAuxiliar.get(1).getNumeroCarta() &&
                        manoAuxiliar.get(3).getNumeroCarta() == manoAuxiliar.get(4).getNumeroCarta() ||
                manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() &&
                        manoAuxiliar.get(3).getNumeroCarta() == manoAuxiliar.get(4).getNumeroCarta();
    }

    public static boolean comprobarTrio(List<Carta> mano){
        List<Carta> manoAuxiliar = manoAuxiliar(mano);

        return manoAuxiliar.get(0).getNumeroCarta() == manoAuxiliar.get(1).getNumeroCarta() &&
            manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() ||
            manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() &&
            manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() ||
            manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() &&
            manoAuxiliar.get(3).getNumeroCarta() == manoAuxiliar.get(4).getNumeroCarta();
    }

    public static boolean comprobarPar(List<Carta> mano){
        List<Carta> manoAuxiliar = manoAuxiliar(mano);

        return manoAuxiliar.get(0).getNumeroCarta() == manoAuxiliar.get(1).getNumeroCarta() ||
            manoAuxiliar.get(1).getNumeroCarta() == manoAuxiliar.get(2).getNumeroCarta() ||
            manoAuxiliar.get(2).getNumeroCarta() == manoAuxiliar.get(3).getNumeroCarta() ||
            manoAuxiliar.get(3).getNumeroCarta() == manoAuxiliar.get(4).getNumeroCarta();
    }

    private static List<Carta> manoAuxiliar(List<Carta> mano){
        List<Carta> manoAuxiliar = new ArrayList<>(mano);
        manoAuxiliar.sort(Comparator.comparing(Carta::getNumeroCarta));
        return manoAuxiliar;
    }
}
