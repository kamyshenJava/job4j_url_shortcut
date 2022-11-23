package urlshortcut.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import urlshortcut.model.Client;
import urlshortcut.service.ClientService;
import urlshortcut.util.RandomStringGenerator;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class RegController {

    private final ClientService clientService;
    @Value("${password_length}")
    private int passwordLength;

    public RegController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody Client client) {
        String site = client.getSite();
        Optional<Client> dbClient = clientService.findBySite(site);
        HashMap<String, String> map = new HashMap<>();
        map.put("login", site);
        if (dbClient.isPresent()) {
            map.put("registration", "false");
        } else {
            map.put("registration", "true");
            String password = RandomStringGenerator.generate(passwordLength);
            map.put("password", password);
            clientService.save(Client.of(site, password));
        }
        return ResponseEntity.ok(map);
    }
}
