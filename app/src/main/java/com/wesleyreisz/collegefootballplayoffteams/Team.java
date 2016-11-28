package com.wesleyreisz.collegefootballplayoffteams;

/**
 * Created by wesleyreisz on 11/27/16.
 */
public class Team {
    private long id;
    private long loses;
    private String mascot;
    private String name;
    private String picture;
    private long wins;

    public Team(){}

    public Team(long teamId, long loses, String mascotName, String teamName, String picture, long wins) {
        this.id = teamId;
        this.loses = loses;
        this.mascot = mascotName;
        this.name = teamName;
        this.picture = picture;
        this.wins = wins;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLoses() {
        return loses;
    }

    public void setLoses(long loses) {
        this.loses = loses;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", loses=" + loses +
                ", mascot='" + mascot + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", wins=" + wins +
                '}';
    }
}
