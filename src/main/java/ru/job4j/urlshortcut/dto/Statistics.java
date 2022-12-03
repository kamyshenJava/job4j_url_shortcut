package ru.job4j.urlshortcut.dto;

import java.util.List;
import java.util.Map;

public class Statistics {

    private String site;
    private Map<String, Integer> traffic;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Map<String, Integer> getTraffic() {
        return traffic;
    }

    public void setTraffic(Map<String, Integer> traffic) {
        this.traffic = traffic;
    }
}
