package ru.job4j.urlshortcut.model;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;
    private String generatedCode;
    private int count;
    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    public static Link of(String url, String generatedCode, Client client) {
        Link link = new Link();
        link.setUrl(url);
        link.setGeneratedCode(generatedCode);
        link.setClient(client);
        return link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
