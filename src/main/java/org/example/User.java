package org.example;

import java.util.Date;
import java.util.Objects;

public class User {
    String login;
    Date lastActivity = new Date();
    String status;

    public User(String login, String status) {
        this.login = login;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(lastActivity, user.lastActivity) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, lastActivity, status);
    }

    @Override
    public String toString() {
        return "User: login " + login + " | status: " + status;
    }
}
