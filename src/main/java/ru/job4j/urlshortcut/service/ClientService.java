package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.RegistrationResult;
import ru.job4j.urlshortcut.model.Client;

import java.util.Map;
import java.util.Optional;

public interface ClientService {
    Optional<Client> findBySite(String site);
    Client save(Client client);
    RegistrationResult register(Client client);
}
