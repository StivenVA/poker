package org.example.poker.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Jugador {

    private String nombre;
    private String id;
    private List<Carta> mano;
    private int cambios;
    private int valorMano;
}
