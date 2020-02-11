package com.tamagotchi.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    public static String AFTER_DEATH_ERROR;
    public static String EMPTY_NAME_ERROR;
    public static String SAVE_DATA_FILE_NAME;
    public static String START_MENU;
    public static String MAIN_SCENE;
    public static String MUSIC_FILE;
    public static long AFTER_DEATH_TIME;
    public static String DEAD_MESSAGE;
    public static String GOOD_MOOD;
    public static String SAD_MOOD;
    public static String YES_HUNGRY;
    public static String NO_HUNGRY;
    public static String CAT_WALK_LEFT;
    public static String CAT_WALK_RIGHT;
    public static String CAT_SIT_RIGHT;
    public static String CAT_SIT_LEFT;
    public static String CAT_PLAY;
    public static String CAT_DEAD;
    public static String DOG_SIT_LEFT;
    public static String DOG_SIT_RIGHT;
    public static String DOG_WALK_LEFT;
    public static String DOG_WALK_RIGHT;
    public static String DOG_DEAD;
    public static String CAT_EAT;
    public static String DOG_EAT;
    public static String DOG_PLAY;
    public static String BACKGROUND_DAY;
    public static String BACKGROUND_NIGHT;

    public static long TIME_TO_GROW;
    public static long TIME_TO_LIVE_WITH_HUNGER;
    public static long TIME_WITHOUT;
    public static long WALK_DELAY;
    public static long CHANGE_WEATHER_DELAY;


    private PropertyLoader() {
    }

    static {
        try {
            Properties property = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);

            BACKGROUND_DAY = property.getProperty("BACKGROUND_DAY");
            BACKGROUND_NIGHT = property.getProperty("BACKGROUND_NIGHT");
            AFTER_DEATH_ERROR = property.getProperty("AFTER_DEATH_ERROR");
            EMPTY_NAME_ERROR = property.getProperty("EMPTY_NAME_ERROR");
            SAVE_DATA_FILE_NAME = property.getProperty("SAVE_DATA_FILE_NAME");
            START_MENU = property.getProperty("START_MENU");
            MAIN_SCENE = property.getProperty("MAIN_SCENE");
            MUSIC_FILE = property.getProperty("MUSIC_FILE");
            DEAD_MESSAGE = property.getProperty("DEAD_MESSAGE");
            GOOD_MOOD = property.getProperty("GOOD_MOOD");
            SAD_MOOD = property.getProperty("SAD_MOOD");
            YES_HUNGRY = property.getProperty("YES_HUNGRY");
            NO_HUNGRY = property.getProperty("NO_HUNGRY");
            CAT_WALK_LEFT = property.getProperty("CAT_WALK_LEFT");
            CAT_WALK_RIGHT = property.getProperty("CAT_WALK_RIGHT");
            CAT_SIT_RIGHT = property.getProperty("CAT_SIT_RIGHT");
            CAT_SIT_LEFT = property.getProperty("CAT_SIT_LEFT");
            CAT_PLAY = property.getProperty("CAT_PLAY");
            CAT_DEAD = property.getProperty("CAT_DEAD");
            DOG_SIT_LEFT = property.getProperty("DOG_SIT_LEFT");
            DOG_SIT_RIGHT = property.getProperty("DOG_SIT_RIGHT");
            DOG_WALK_LEFT = property.getProperty("DOG_WALK_LEFT");
            DOG_WALK_RIGHT = property.getProperty("DOG_WALK_RIGHT");
            DOG_DEAD = property.getProperty("DOG_DEAD");
            CAT_EAT = property.getProperty("CAT_EAT");
            DOG_EAT = property.getProperty("DOG_EAT");
            DOG_PLAY = property.getProperty("DOG_PLAY");

            fis = new FileInputStream("src/main/resources/gameValues.properties");
            property.load(fis);

            TIME_TO_GROW = Long.parseLong(property.getProperty("TIME_TO_GROW"));
            TIME_TO_LIVE_WITH_HUNGER = Long.parseLong(property.getProperty("TIME_TO_LIVE_WITH_HUNGER"));
            TIME_WITHOUT = Long.parseLong(property.getProperty("TIME_WITHOUT"));
            WALK_DELAY = Long.parseLong(property.getProperty("WALK_DELAY"));
            CHANGE_WEATHER_DELAY = Long.parseLong(property.getProperty("CHANGE_WEATHER_DELAY"));
            AFTER_DEATH_TIME = Long.parseLong(property.getProperty("AFTER_DEATH_TIME"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

