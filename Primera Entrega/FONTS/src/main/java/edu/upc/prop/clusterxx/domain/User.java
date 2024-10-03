package edu.upc.prop.clusterxx.domain;

public class User {
    private String name;
    private String password;
    private int points;

    public User() {
        this.name = "";
        this.password = "";
        this.points = 0;
    }

    public User(String name) {
        this.name = name;
        this.password = "";
        this.points = 0;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void updatePoints(int points) {
        this.points += points;
    }

    public boolean isRegistered() {
        return !name.isEmpty();
    }
}
