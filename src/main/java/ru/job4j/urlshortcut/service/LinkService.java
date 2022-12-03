package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.Statistics;

public interface LinkService {
    String generateUrlAndSave(String url, String site);
    String findByGeneratedCode(String generatedCode);
    Statistics generateStatistics(String site);
}
