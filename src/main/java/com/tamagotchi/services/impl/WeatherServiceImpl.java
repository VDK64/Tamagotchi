package com.tamagotchi.services.impl;

import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.properties.GameData;
import com.tamagotchi.services.WeatherService;
import com.tamagotchi.weather.Weather;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class WeatherServiceImpl implements WeatherService {
    public static final WeatherServiceImpl instance = new WeatherServiceImpl();
    public static final Image backgroundNight = new Image(
            new File(GameData.BACKGROUND_NIGHT).toURI().toString());
    public static final Image backgroundDay = new Image(
            new File(GameData.BACKGROUND_DAY).toURI().toString());

    @Override
    public void defineAndSetWeather(ImageView background) {
        if (GameParameters.instance.getWeather().equals(Weather.DAY)) {
            background.setImage(backgroundDay);
        } else {
            background.setImage(backgroundNight);
        }
    }
}
