package com.tamagotchi.weather;

import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.impl.CommonServiceImpl;
import com.tamagotchi.services.impl.WeatherServiceImpl;
import javafx.scene.image.ImageView;

import java.util.TimerTask;

public class WeatherChanger extends TimerTask {
    private final ImageView background;
    private final CommonService commonService;

    public WeatherChanger(ImageView background) {
        this.background = background;
        this.commonService = CommonServiceImpl.instance;
    }

    @Override
    public void run() {
        if (background != null) {
            String image = commonService.parseUrl(background.getImage().getUrl());
            if (image.equals("background")) {
                background.setImage(WeatherServiceImpl.backgroundNight);
                GameParameters.instance.setWeather(Weather.NIGHT);
            } else {
                background.setImage(WeatherServiceImpl.backgroundDay);
                GameParameters.instance.setWeather(Weather.DAY);
            }
        }
    }
}