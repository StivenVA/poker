package org.example.poker.controller;

import lombok.RequiredArgsConstructor;
import org.example.poker.controller.response.CustomResponse;
import org.example.poker.services.ServicioJuego;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Juego {

    private final ServicioJuego servicioJuego;

    @GetMapping("inicio")
    public ResponseEntity<?> inicio(@RequestParam String nombre) {
        return CustomResponse.ok(servicioJuego.iniciar(nombre));
    }

    @GetMapping("estado")
    public ResponseEntity<?> estado() {
        return CustomResponse.ok(servicioJuego.estado());
    }
}
