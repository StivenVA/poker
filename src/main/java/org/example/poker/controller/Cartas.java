package org.example.poker.controller;

import lombok.RequiredArgsConstructor;
import org.example.poker.controller.response.CustomResponse;
import org.example.poker.model.Carta;
import org.example.poker.services.ServicioJuego;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Cartas {

    private final ServicioJuego servicioJuego;

    @PostMapping("cambio")
    public ResponseEntity<?> cambio(@RequestParam String id, @RequestBody List<Carta> cartas) {
        return CustomResponse.ok(servicioJuego.cambiarCartas(id, cartas));
    }

    @GetMapping("cartas")
    public ResponseEntity<?> cartas(@RequestParam String id) {
        return CustomResponse.ok(servicioJuego.entregarCartasJugador(id));
    }
}
