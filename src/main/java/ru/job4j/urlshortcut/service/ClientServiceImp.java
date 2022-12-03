package ru.job4j.urlshortcut.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.RegistrationResult;
import ru.job4j.urlshortcut.model.Client;
import ru.job4j.urlshortcut.repository.ClientRepository;
import ru.job4j.urlshortcut.util.RandomStringGenerator;
import ru.job4j.urlshortcut.util.StringGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringGenerator stringGenerator;
    @Value("${password_length}")
    private int passwordLength;

    public ClientServiceImp(ClientRepository clientRepository, PasswordEncoder passwordEncoder,
                            StringGenerator stringGenerator) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.stringGenerator = stringGenerator;
    }
    public Optional<Client> findBySite(String site) {
        return clientRepository.findBySite(site);
    }

    public Client save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    public RegistrationResult register(Client client) {
        String site = client.getSite();
        Optional<Client> dbClient = this.findBySite(site);
        RegistrationResult rr = new RegistrationResult();
        rr.setLogin(site);
        if (dbClient.isPresent()) {
            rr.setRegistered(false);
        } else {
            rr.setRegistered(true);
            String password = stringGenerator.generate(passwordLength);
            rr.setPassword(password);
            this.save(Client.of(site, password));
        }
        return rr;
    }
}
