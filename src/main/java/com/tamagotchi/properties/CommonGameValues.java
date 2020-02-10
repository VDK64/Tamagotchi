package com.tamagotchi.properties;

public class CommonGameValues {
    public static final long TIME_TO_GROW = PropertyLoader.TIME_TO_GROW;
    public static final long TIME_TO_LIVE_WITH_HUNGER = PropertyLoader.TIME_TO_LIVE_WITH_HUNGER;
    public static final long TIME_WITHOUT = PropertyLoader.TIME_WITHOUT;
    public static final long TIME_TO_IMMEDIATELY_DEATH = TIME_TO_LIVE_WITH_HUNGER + TIME_WITHOUT;
    public static final long WALK_DELAY = PropertyLoader.WALK_DELAY;
    public static final long CHANGE_WEATHER_DELAY = PropertyLoader.CHANGE_WEATHER_DELAY;
}
