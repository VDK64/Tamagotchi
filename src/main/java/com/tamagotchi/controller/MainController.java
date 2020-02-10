package com.tamagotchi.controller;

import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.PetService;
import com.tamagotchi.services.impl.CommonServiceImpl;
import com.tamagotchi.services.impl.PetServiceImpl;
import com.tamagotchi.services.impl.WeatherServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainController {
    @FXML
    public ImageView background;
    private CommonService commonService;
    private PetService petService;
    @FXML
    private Pane pane;
    @FXML
    private ImageView mood;
    @FXML
    private ImageView hungry;
    @FXML
    private Button eat;
    @FXML
    private Button play;

    @FXML
    private void initialize() {
        this.commonService = CommonServiceImpl.instance;
        this.petService = PetServiceImpl.instance;
        WeatherServiceImpl.instance.defineAndSetWeather(background);
        ImageView pet = petService.createPet(pane, background);
        commonService.initMonitoringAndAnimations(pane, background, mood, hungry, pet, eat, play);
        commonService.setIndicators(mood, hungry);
    }

    @FXML
    void eat() {
        petService.eat(pane);
    }

    @FXML
    void play() {
      petService.play(pane);
    }


    public void stop() {
        commonService.stop();
    }
}
