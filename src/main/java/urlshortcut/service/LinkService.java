package urlshortcut.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import urlshortcut.model.Link;
import urlshortcut.repository.LinkRepository;
import urlshortcut.util.RandomStringGenerator;

import java.util.Optional;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    @Value("${url_length}")
    private int urlLength;


    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String generateUrlAndSave(String url) {
        String generatedCode = RandomStringGenerator.generate(urlLength);
        linkRepository.save(Link.of(url, generatedCode));
        return generatedCode;
    }

    public Link findByGeneratedCode(String generatedCode) {
        Optional<Link> rsl = linkRepository.findByGeneratedCode(generatedCode);
        if (rsl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This link has not been found");
        }
        return rsl.get();
    }
}
