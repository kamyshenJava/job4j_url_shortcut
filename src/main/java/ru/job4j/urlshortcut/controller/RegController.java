package ru.job4j.urlshortcut.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.model.Client;
import ru.job4j.urlshortcut.service.ClientService;

@RestController(value = "/registration")
public class RegController {

    private final ClientService clientService;
    public RegController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.register(client));
    }
}
