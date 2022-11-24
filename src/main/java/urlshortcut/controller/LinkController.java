package urlshortcut.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortcut.model.Link;
import urlshortcut.service.LinkService;

import java.util.Map;

@RestController
public class LinkController {

    private static final String CODE = "code";
    public static final String REDIRECT_HEADER = "Location";
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@RequestBody Link link) {
        String generatedCode = linkService.generateUrlAndSave(link.getUrl());
        return ResponseEntity.ok(Map.of(CODE, generatedCode));
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        Link linkDb = linkService.findByGeneratedCode(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add(REDIRECT_HEADER, linkDb.getUrl());
        return new ResponseEntity<>(headers, HttpStatus.valueOf(302));
    }
}
