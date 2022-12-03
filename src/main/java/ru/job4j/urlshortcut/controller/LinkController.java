package ru.job4j.urlshortcut.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.Statistics;
import ru.job4j.urlshortcut.model.Link;
import ru.job4j.urlshortcut.service.LinkService;

import java.security.Principal;
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
    public ResponseEntity<?> convert(@RequestBody Link link, Principal principal) {
        String generatedCode = linkService.generateUrlAndSave(link.getUrl(), principal.getName());
        return ResponseEntity.ok(Map.of(CODE, generatedCode));
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        String url = linkService.findByGeneratedCode(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add(REDIRECT_HEADER, url);
        return new ResponseEntity<>(headers, HttpStatus.valueOf(302));
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic(Principal principal) {
        String site = principal.getName();
        Statistics statistics = linkService.generateStatistics(site);
        return ResponseEntity.ok(statistics.getTraffic());
    }
}
