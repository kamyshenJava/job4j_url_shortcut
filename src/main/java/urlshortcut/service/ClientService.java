package urlshortcut.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import urlshortcut.model.Client;
import urlshortcut.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Optional<Client> findBySite(String site) {
        return clientRepository.findBySite(site);
    }

    public Client save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }
}
