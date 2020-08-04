package de.mystarybreak.buildffa.utils;

import lombok.Getter;

public class PlayerStatistic {

    @Getter private int kills;
    @Getter private int deaths;
    @Getter private int points;

    public PlayerStatistic(int kills, int deaths, int points) {
        this.kills = kills;
        this.deaths = deaths;
        this.points = points;
    }

    public void addKills(int kills) {
        this.kills += kills;
    }

    public void addDeaths(int deaths) {
        this.deaths += deaths;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void removePoints(int points) {
        if(this.points >= points)
            this.points -= points;
        else
            this.points = 0;
    }

}
