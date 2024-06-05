package org.example.poker.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Carta {

    private String palo;
    private String valor;
    private int numeroCarta;

    public boolean equals(Carta carta){
        return this.palo.equals(carta.palo) && this.valor.equals(carta.valor);
    }
}
