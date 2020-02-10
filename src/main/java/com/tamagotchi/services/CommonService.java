package com.tamagotchi.services;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public interface CommonService {

    String parseUrl(String url);

    void stop();

    boolean validateChoice(Label errMessage, TextField nameField);

    void toTheMainScene(Button dogButton) throws IOException;

    void initMonitoringAndAnimations(Pane pane, ImageView background, ImageView mood, ImageView hungry, ImageView pet,
                                     Button eat, Button play);

    void setIndicators(ImageView mood, ImageView hungry);

    void noteIfDead(Label errMessage);
}
