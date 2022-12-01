package ru.job4j.urlshortcut.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.model.Client;
import ru.job4j.urlshortcut.model.Link;
import ru.job4j.urlshortcut.repository.ClientRepository;
import ru.job4j.urlshortcut.repository.LinkRepository;
import ru.job4j.urlshortcut.util.RandomStringGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkService {
    public static final String URL = "url";
    public static final String TOTAL = "total";
    public static final String LINK_PREFIX = "http://";
    public static final String HTTP_PREFIX = "http";

    private final LinkRepository linkRepository;
    private final ClientRepository clientRepository;

    @Value("${url_length}")
    private int urlLength;


    public LinkService(LinkRepository linkRepository, ClientRepository clientRepository) {
        this.linkRepository = linkRepository;
        this.clientRepository = clientRepository;
    }

    public String generateUrlAndSave(String url, String site) {
        String generatedCode = RandomStringGenerator.generate(urlLength);
        Client client = clientRepository.findBySite(site).get();
        linkRepository.save(Link.of(url, generatedCode, client));
        return generatedCode;
    }

    public String findByGeneratedCode(String generatedCode) {
        Optional<Link> rsl = linkRepository.findByGeneratedCode(generatedCode);
        if (rsl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This link has not been found");
        }
        Link link = rsl.get();
        String url = link.getUrl();
        if (!url.startsWith(HTTP_PREFIX)) {
            url = LINK_PREFIX + url;
        }
        link.setCount(link.getCount() + 1);
        linkRepository.save(link);
        return url;
    }

    public List<Map<String, String>> generateStatistics(String site) {
        Client client = clientRepository.findBySite(site).get();
        List<Link> links = linkRepository.findAllByClient(client);
        if (links.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no saved links yet");
        }
        return links.stream()
                .map(link -> Map.of(String.format("url : %s", link.getUrl()),
                        String.format(("total : %d"), link.getCount())))
                .collect(Collectors.toList());

    }
}
