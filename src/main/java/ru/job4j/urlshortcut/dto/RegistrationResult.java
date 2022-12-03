package ru.job4j.urlshortcut.dto;

public class RegistrationResult {
    private boolean isRegistered;
    private String login;
    private String password;

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
