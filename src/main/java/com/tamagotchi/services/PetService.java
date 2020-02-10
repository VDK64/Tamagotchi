package com.tamagotchi.services;

import com.tamagotchi.parameters.TypeEntity;
import com.tamagotchi.walk.Side;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public interface PetService {

    ImageView createPet(Pane pane, ImageView background);

    void createPet(TypeEntity typeEntity, String petName);

    void setSize(ImageView pet, ImageView background);

    Side moveLeft(Pane pane, ImageView pet, Side side);

    Side moveRight(Pane pane, ImageView pet, Side side);

    void eat(Pane pane);

    void play(Pane pane);

    void setDeadSprite(Pane pane, ImageView pet, Button eat, Button play);
}
