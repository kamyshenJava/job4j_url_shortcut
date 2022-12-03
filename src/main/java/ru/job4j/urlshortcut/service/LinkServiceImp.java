package ru.job4j.urlshortcut.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.Statistics;
import ru.job4j.urlshortcut.model.Client;
import ru.job4j.urlshortcut.model.Link;
import ru.job4j.urlshortcut.repository.ClientRepository;
import ru.job4j.urlshortcut.repository.LinkRepository;
import ru.job4j.urlshortcut.util.RandomStringGenerator;
import ru.job4j.urlshortcut.util.StringGenerator;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkServiceImp implements LinkService {
    public static final String URL = "url";
    public static final String TOTAL = "total";
    public static final String LINK_PREFIX = "http://";
    public static final String HTTP_PREFIX = "http";

    private final LinkRepository linkRepository;
    private final ClientRepository clientRepository;
    private final StringGenerator stringGenerator;

    @Value("${url_length}")
    private int urlLength;


    public LinkServiceImp(LinkRepository linkRepository, ClientRepository clientRepository,
                          StringGenerator stringGenerator) {
        this.linkRepository = linkRepository;
        this.clientRepository = clientRepository;
        this.stringGenerator = stringGenerator;
    }

    public String generateUrlAndSave(String url, String site) {
        String generatedCode = stringGenerator.generate(urlLength);
        Optional<Client> clientOptional = clientRepository.findBySite(site);
        if (clientOptional.isEmpty()) {
            throw new NoSuchElementException("A client with this site has not been found");
        }
        Client client = clientOptional.get();
        linkRepository.save(Link.of(url, generatedCode, client));
        return generatedCode;
    }

    public String findByGeneratedCode(String generatedCode) {
        Optional<Link> rsl = linkRepository.findByGeneratedCode(generatedCode);
        if (rsl.isEmpty()) {
            throw new NoSuchElementException("This link has not been found");
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

    public Statistics generateStatistics(String site) {
        Optional<Client> clientOptional = clientRepository.findBySite(site);
        if (clientOptional.isEmpty()) {
            throw new NoSuchElementException("A client with this site has not been found");
        }
        Client client = clientOptional.get();
        List<Link> links = linkRepository.findAllByClient(client);
        if (links.size() == 0) {
            throw new NoSuchElementException("There are no saved links yet");
        }
        Statistics statistics = new Statistics();
        statistics.setSite(client.getSite());
        statistics.setTraffic(links.stream()
                .collect(Collectors.toMap(Link::getUrl, Link::getCount)));
        return statistics;
    }
}
