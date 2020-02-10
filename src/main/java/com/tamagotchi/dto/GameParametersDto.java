package com.tamagotchi.dto;

import com.tamagotchi.entities.Pet;
import com.tamagotchi.weather.Weather;

public class GameParametersDto {
    private Pet pet;
    private long birthDay;
    private long lastTimeCheck;
    private long lastTimeEat;
    private long lasTimePlay;
    private Weather weather;
    private boolean dead;

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public long getLastTimeEat() {
        return lastTimeEat;
    }

    public void setLastTimeEat(long lastTimeEat) {
        this.lastTimeEat = lastTimeEat;
    }

    public long getLasTimePlay() {
        return lasTimePlay;
    }

    public void setLasTimePlay(long lasTimePlay) {
        this.lasTimePlay = lasTimePlay;
    }

    public Pet getPet() {
        return pet;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public long getLastTimeCheck() {
        return lastTimeCheck;
    }

    public void setLastTimeCheck(long lastTimeCheck) {
        this.lastTimeCheck = lastTimeCheck;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
