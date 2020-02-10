package com.tamagotchi.monitoring;

import com.tamagotchi.entities.Pet;
import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.parameters.Mood;
import com.tamagotchi.parameters.PetState;
import com.tamagotchi.properties.CommonGameValues;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.PetService;
import com.tamagotchi.services.impl.CommonServiceImpl;
import com.tamagotchi.services.impl.PetServiceImpl;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.TimerTask;

public class ParameterMonitoring extends TimerTask {
    private PetService petService;
    private CommonService commonService;
    private GameParameters parameters;
    private Pane pane;
    private ImageView background;
    private ImageView pet;
    private Button eat;
    private Button play;
    private ImageView mood;
    private ImageView hungry;
    private boolean alreadyDead;

    public ParameterMonitoring(Pane pane, ImageView mood, ImageView hungry, ImageView pet, Button eat, Button play,
                               ImageView background) {
        this.petService = PetServiceImpl.instance;
        this.mood = mood;
        this.hungry = hungry;
        this.pane = pane;
        this.background = background;
        this.pet = pet;
        this.eat = eat;
        this.play = play;
        this.parameters = GameParameters.instance;
        this.commonService = CommonServiceImpl.instance;
        this.alreadyDead = false;
    }

    @Override
    public void run() {
        checkPetState();
        checkIsDeath();
        checkIsGrow();
    }

    private void checkIsGrow() {
        GameParameters parameters = GameParameters.instance;
        long now = System.currentTimeMillis();
        if ((now - parameters.getBirthDay()) / CommonGameValues.TIME_TO_GROW >= 1
                && !commonService.parseUrl(pet.getImage().getUrl()).contains("Walk")
                && parameters.getPet().getPetState().equals(PetState.YOUNG)) {
            parameters.getPet().setPetState(PetState.ADULT);
            petService.setSize(pet, background);
        }
    }

    private void checkIsDeath() {
        if (parameters.getLastTimeCheck() != 0) {
            long now = System.currentTimeMillis();
            if ((now - parameters.getLastTimeCheck()) / CommonGameValues.TIME_TO_LIVE_WITH_HUNGER >= 1
                    || alreadyDead) {
                parameters.setDead(true);
                petService.setDeadSprite(pane, pet, eat, play);
            }
        }
    }

    private void checkPetState() {
        Pet pet = parameters.getPet();
        long now = System.currentTimeMillis();
        if ((now - parameters.getLastTimeEat()) / CommonGameValues.TIME_WITHOUT >= 1 && !pet.isHungry()) {
            pet.setHungry(true);
            hungry.setImage(CommonServiceImpl.yesHungry);
            parameters.setLastTimeCheck(now);
        }
        if ((now - parameters.getLasTimePlay()) / CommonGameValues.TIME_WITHOUT >= 1
                && pet.getMood().equals(Mood.GOOD)) {
            pet.setMood(Mood.BAD);
            mood.setImage(CommonServiceImpl.sadMood);
            parameters.setLastTimeCheck(now);
        }
        if ((now - parameters.getLastTimeEat()) / CommonGameValues.TIME_TO_IMMEDIATELY_DEATH >= 1
                || (now - parameters.getLasTimePlay()) / CommonGameValues.TIME_TO_IMMEDIATELY_DEATH >= 1)
            alreadyDead = true;
    }
}
