package com.tamagotchi.entities;

import com.tamagotchi.parameters.Mood;
import com.tamagotchi.parameters.PetState;
import com.tamagotchi.parameters.TypeEntity;

public class Pet {
    private static Pet instance;
    private String name;
    private Mood mood;
    private TypeEntity typeEntity;
    private boolean hungry;
    private PetState petState;

    private Pet() {
    }

    public synchronized static Pet getInstance() {
        if (instance == null) {
            instance = new Pet();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public TypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(TypeEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    public PetState getPetState() {
        return petState;
    }

    public void setPetState(PetState petState) {
        this.petState = petState;
    }
}
