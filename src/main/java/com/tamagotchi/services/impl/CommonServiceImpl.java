package com.tamagotchi.services.impl;

import com.tamagotchi.Main;
import com.tamagotchi.entities.Pet;
import com.tamagotchi.monitoring.ParameterMonitoring;
import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.parameters.Mood;
import com.tamagotchi.properties.CommonGameValues;
import com.tamagotchi.properties.Errors;
import com.tamagotchi.properties.GameData;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.walk.Walking;
import com.tamagotchi.weather.WeatherChanger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class CommonServiceImpl implements CommonService {
    public static final CommonService instance = new CommonServiceImpl();
    private final Timer timer;
    private final Timer timer1;
    public static final Image goodMood = new Image(new File(GameData.GOOD_MOOD)
            .toURI().toString());
    public static final Image sadMood = new Image(new File(GameData.SAD_MOOD)
            .toURI().toString());
    public static final Image yesHungry = new Image(new File(GameData.YES_HUNGRY)
            .toURI().toString());
    public static final Image noHungry = new Image(new File(GameData.NO_HUNGRY)
            .toURI().toString());

    private CommonServiceImpl() {
        this.timer = new Timer();
        this.timer1 = new Timer();
    }

    @Override
    public void setIndicators(ImageView mood, ImageView hungry) {
        Pet pet = GameParameters.instance.getPet();
        if (pet.getMood().equals(Mood.GOOD))
            mood.setImage(goodMood);
        else
            mood.setImage(sadMood);
        if (pet.isHungry())
            hungry.setImage(yesHungry);
        else
            hungry.setImage(noHungry);
    }

    @Override
    public void noteIfDead(Label errMessage) {
        if (GameParameters.instance.isDead())
            errMessage.setText(Errors.DEAD_MESSAGE);
    }

    @Override
    public void initMonitoringAndAnimations(Pane pane, ImageView background, ImageView mood, ImageView hungry,
                                            ImageView pet, Button eat, Button play) {
        Walking walking = new Walking(pane);
        WeatherChanger weatherChanger = new WeatherChanger(background);
        ParameterMonitoring monitoring = new ParameterMonitoring(pane, mood, hungry, pet, eat, play, background);
        timer.schedule(weatherChanger, 0, CommonGameValues.CHANGE_WEATHER_DELAY);
        timer.schedule(walking, 0, CommonGameValues.WALK_DELAY);
        timer1.schedule(monitoring, 0, 1000);
    }

    @Override
    public void toTheMainScene(Button dogButton) throws IOException {
        Stage stage = (Stage) dogButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(GameData.MAIN_SCENE));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        if (Main.getMainController() == null)
            Main.setMainController(loader.getController());
        stage.show();
    }

    @Override
    public boolean validateChoice(Label errMessage, TextField nameField) {
        GameParameters instance = GameParameters.instance;
        if (instance.isDead()) {
            if (System.currentTimeMillis() - instance.getLastTimeCheck() <= GameData.AFTER_DEATH_TIME) {
                errMessage.setText(Errors.AFTER_DEATH_ERROR);
                return true;
            }
        }
        if (nameField.getText().trim().equals("")) {
            errMessage.setText(Errors.EMPTY_NAME_ERROR);
            return true;
        }
        return false;
    }

    @Override
    public String parseUrl(String url) {
        String[] UrlArray = url.split("/");
        String[] imageName = UrlArray[UrlArray.length - 1].split("\\.");
        return imageName[0];
    }

    @Override
    public void stop() {
        timer.cancel();
        timer1.cancel();
        timer.purge();
        timer1.purge();
    }
}
