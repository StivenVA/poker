package org.example.poker.services;

import lombok.RequiredArgsConstructor;
import org.example.poker.exceptions.JugadoresCompletosException;
import org.example.poker.exceptions.NotFoundException;
import org.example.poker.model.Carta;
import org.example.poker.model.Estado;
import org.example.poker.model.Jugador;
import org.example.poker.util.ComprobarGanador;
import org.example.poker.util.ExceptionHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServicioJuego {

    private final Jugador[] jugadores = new Jugador[4];
    private int jugadorActual = 0;
    private final Estado estado = new Estado(Arrays.asList(jugadores), "Jugadores incompletos");
    private final ServicioCartas servicioCartas;
    private final ExceptionHandler exceptionHandler;
    private int jugadoresRepartidos = 0;

    public String iniciar(String nombre) {

        if (nombre == null || nombre.isBlank())
            manageExceptions(new IllegalArgumentException("El nombre no puede estar vacío"));

        if (jugadorActual<4){
            Jugador jugador = generarJugador(nombre, String.valueOf(jugadorActual+1));
            jugadores[jugadorActual] = jugador;
            jugadorActual++;

            estado.setJugadores(Arrays.asList(jugadores));
            if (jugadorActual == 4) {
                estado.setMensaje("Hay que repartir cartas");
            }

            return "Id del jugador: "+jugador.getId();
        }
        manageExceptions(new JugadoresCompletosException("Ya no se pueden agregar más jugadores"));

        return null;
    }

    private Jugador generarJugador(String nombre,String id){

        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);
        jugador.setId(id);
        jugador.setCambios(2);
        return jugador;
    }

    public Estado estado() {

        comprobarCambioCartas();

        if (estado.getMensaje().equals("Resultado")){
            Jugador ganador = comprobarGanador();
            estado.setMensaje("ganador: "+ganador.getNombre());
        }

        return estado;
    }

    private void comprobarCambioCartas(){
        if (Arrays.stream(jugadores).anyMatch(Objects::isNull)) return;

        if(Arrays.stream(jugadores).allMatch(j -> j.getCambios() == 0)){
            estado.setMensaje("Resultado");
        }
    }

    private Jugador comprobarGanador(){
        return ComprobarGanador.comprobarGanador(jugadores);
    }


    public List<Carta> entregarCartasJugador(String id){
        Jugador jugador = comprobarExistenciaJugador(id);
        if (jugador != null){

            if (jugador.getMano()!=null)
                manageExceptions(new IllegalArgumentException("Ya se han entregado las cartas al jugador "+jugador.getNombre()));

            List<Carta> mano = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                mano.add(servicioCartas.sacarCarta());
            }

            jugador.setMano(mano);
            jugadoresRepartidos++;

            if (jugadoresRepartidos == 4){
                estado.setMensaje("Es tu turno de cambiar cartas");
            }

            return mano;
        }

        return null;
    }

    public List<Carta> cambiarCartas(String id, List<Carta> cartasACambiar){
        Jugador jugador = comprobarExistenciaJugador(id);
        if (jugador != null){

            manageCambioExceptions(jugador,cartasACambiar);

            if (cartasACambiar.isEmpty()){
                noCambiar(jugador);
                return jugador.getMano();
            }

            return realizarCambioCartas(jugador,cartasACambiar);
        }

        return null;
    }

    private List<Carta> realizarCambioCartas(Jugador jugador, List<Carta> cartasACambiar){
        List<Carta> mano = jugador.getMano();
        List<Carta> manoCopia = new ArrayList<>(mano);
        List<Carta> cartasSacadas = new ArrayList<>();
        int cartasCambiadas = 0;

        for (int i = 0; i < mano.size(); i++) {

            for (Carta value : cartasACambiar) {
                if (mano.get(i).equals(value)) {
                    Carta carta = servicioCartas.sacarCarta();

                    cartasSacadas.add(carta);

                    mano.set(i, carta);
                    cartasCambiadas++;
                }
            }
        }

        if (cartasCambiadas != cartasACambiar.size()){
            servicioCartas.ponerCartasEnBaraja(cartasSacadas);
            jugador.setMano(manoCopia);
            manageExceptions(new IllegalArgumentException("No se pueden cambiar cartas que no están en la mano"));
        }

        jugador.setMano(mano);
        jugador.setCambios(jugador.getCambios()-1);

        return mano;
    }

    private void manageCambioExceptions(Jugador jugador,List<Carta> cartasACambiar){
        if (jugador.getMano()==null)
            manageExceptions(new IllegalArgumentException("No se han entregado las cartas al jugador "+jugador.getNombre()));

        if (jugador.getCambios() == 0)
            manageExceptions(new IllegalArgumentException("Ya no se pueden hacer más cambios"));

        if (cartasACambiar.size() > 3)
            manageExceptions(new IllegalArgumentException("No se pueden cambiar más de 3 cartas"));
    }

    private void noCambiar(Jugador jugador){
        jugador.setCambios(jugador.getCambios()-1);
    }

    private Jugador comprobarExistenciaJugador(String id){
        Jugador jugador = Arrays.stream(jugadores).filter(j -> j.getId().equals(id)).findFirst().orElse(null);
        if (jugador == null){
            manageExceptions(new NotFoundException("Jugador no encontrado"));
        }
        return jugador;
    }

    private void manageExceptions(Exception e){
        try{
            throw e;
        }
        catch(Exception ex){
            exceptionHandler.handleException(ex);
        }
    }
}
