package urlshortcut.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String site;
    private String password;

    public static Client of(String site) {
        Client client = new Client();
        client.setSite(site);
        return client;
    }
    public static Client of(String site, String password) {
        Client client = new Client();
        client.setSite(site);
        client.setPassword(password);
        return client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return id == client.id && Objects.equals(site, client.site);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, site);
    }
}
