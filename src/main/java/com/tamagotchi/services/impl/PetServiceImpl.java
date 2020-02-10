package com.tamagotchi.services.impl;

import com.tamagotchi.entities.Pet;
import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.parameters.Mood;
import com.tamagotchi.parameters.PetState;
import com.tamagotchi.parameters.TypeEntity;
import com.tamagotchi.properties.CatSprites;
import com.tamagotchi.properties.DogSprites;
import com.tamagotchi.properties.Errors;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.PetService;
import com.tamagotchi.walk.Side;
import com.tamagotchi.weather.Weather;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;

public class PetServiceImpl implements PetService {
    public static final PetService instance = new PetServiceImpl();
    private final CommonService commonService;
    private boolean action;
    private final Image catWalkRight;
    private final Image catWalkLeft;
    private final Image catPlay;
    private final Image catSitRight;
    private final Image catSitLeft;
    private final Image catDead;
    private final Image dogSitLeft;
    private final Image dogSitRight;
    private final Image dogWalkLeft;
    private final Image dogWalkRight;
    private final Image dogDead;


    private PetServiceImpl() {
        this.action = false;
        this.commonService = CommonServiceImpl.instance;
        this.catWalkLeft = new Image(new File(CatSprites.CAT_WALK_LEFT)
                .toURI().toString());
        this.catWalkRight = new Image(new File(CatSprites.CAT_WALK_RIGHT)
                .toURI().toString());
        this.catSitRight = new Image(new File(CatSprites.CAT_SIT_RIGHT)
                .toURI().toString());
        this.catSitLeft = new Image(new File(CatSprites.CAT_SIT_LEFT)
                .toURI().toString());
        this.catPlay = new Image(new File(CatSprites.CAT_PLAY)
                .toURI().toString());
        this.catDead = new Image(new File(CatSprites.CAT_DEAD)
                .toURI().toString());
        this.dogSitLeft = new Image(new File(DogSprites.DOG_SIT_LEFT)
                .toURI().toString());
        this.dogSitRight = new Image(new File(DogSprites.DOG_SIT_RIGHT)
                .toURI().toString());
        this.dogWalkLeft = new Image(new File(DogSprites.DOG_WALK_LEFT)
                .toURI().toString());
        this.dogWalkRight = new Image(new File(DogSprites.DOG_WALK_RIGHT)
                .toURI().toString());
        this.dogDead = new Image(new File(DogSprites.DOG_DEAD)
                .toURI().toString());
    }

    @Override
    public void createPet(TypeEntity typeEntity, String petName) {
        GameParameters gameParameters = GameParameters.instance;
        Pet pet = Pet.getInstance();
        pet.setName(petName);
        pet.setHungry(false);
        pet.setMood(Mood.GOOD);
        pet.setPetState(PetState.YOUNG);
        pet.setTypeEntity(typeEntity);
        gameParameters.setPet(pet);
        gameParameters.setWeather(Weather.DAY);
        gameParameters.setBirthDay(System.currentTimeMillis());
        gameParameters.setLasTimePlay(System.currentTimeMillis());
        gameParameters.setLastTimeEat(System.currentTimeMillis());
        gameParameters.setDead(false);
    }

    @Override
    public ImageView createPet(Pane pane, ImageView background) {
        ImageView pet = definePet(pane, background);
        pet.setId("pet");
        pane.getChildren().add(pet);
        return pet;
    }

    @Override
    public void setSize(ImageView pet, ImageView background) {
        if (GameParameters.instance.getPet().getPetState().equals(PetState.YOUNG)) {
            pet.setX(background.getFitWidth() / 2 - 40);
            pet.setY(470);
            pet.setFitHeight(50);
            pet.setFitWidth(50);
        } else {
            pet.setX(background.getFitWidth() / 2 - 40);
            pet.setY(420);
            pet.setFitHeight(100);
            pet.setFitWidth(100);
        }
    }

    @Override
    public Side moveLeft(Pane pane, ImageView pet, Side side) {
        Image petWalkLeft = defineAnimation(Side.LEFT);
        setAnimationSizes(pet, true);
        pet.setImage(petWalkLeft);

        createTranslateTransition(pane, pet, -250f, Side.RIGHT);

        if (side.equals(Side.RIGHT))
            return Side.CENTER;
        else
            return Side.LEFT;
    }

    @Override
    public Side moveRight(Pane pane, ImageView pet, Side side) {
        Image walkRight = defineAnimation(Side.RIGHT);
        setAnimationSizes(pet, true);
        pet.setImage(walkRight);
        createTranslateTransition(pane, pet, 250f, Side.LEFT);
        if (side.equals(Side.LEFT))
            return Side.CENTER;
        else
            return Side.RIGHT;
    }

    @Override
    public void eat(Pane pane) {
        if (!action) {
            ImageView pet = (ImageView) pane.getScene().lookup("#pet");
            if (!commonService.parseUrl(pet.getImage().getUrl()).contains("Walk")) {
                this.action = true;
                Image image = defineAnimation("Eat");
                setAnimationSizes(pet, false);
                pet.setImage(image);
                endOfAnimation(pane, pet);
                GameParameters.instance.setLastTimeEat(System.currentTimeMillis());
                GameParameters.instance.getPet().setHungry(false);
                checkAllParametersOfPep(pane);
            }
        }
    }

    @Override
    public void play(Pane pane) {
        if (!action) {
            ImageView pet = (ImageView) pane.getScene().lookup("#pet");
            if (!commonService.parseUrl(pet.getImage().getUrl()).contains("Walk")) {
                this.action = true;
                Image image = defineAnimation("Play");
                setAnimationSizes(pet, false);
                pet.setImage(image);
                endOfAnimation(pane, pet);
                GameParameters.instance.setLasTimePlay(System.currentTimeMillis());
                GameParameters.instance.getPet().setMood(Mood.GOOD);
                checkAllParametersOfPep(pane);
            }
        }
    }

    @Override
    public void setDeadSprite(Pane pane, ImageView pet, Button eat, Button play) {
        Platform.runLater(() -> {
            Label label = new Label(Errors.DEAD_MESSAGE);
            label.setFont(new Font("System", 19));
            label.setLayoutX(14);
            label.setLayoutY(52);
            label.setTextFill(Color.RED);
            pane.getChildren().removeAll(eat, play);
            pane.getChildren().add(label);
        });
        if (!commonService.parseUrl(pet.getImage().getUrl()).contains("Walk")
                && !commonService.parseUrl(pet.getImage().getUrl()).contains("Play")
                && !commonService.parseUrl(pet.getImage().getUrl()).contains("Eat")) {
            if (GameParameters.instance.getPet().getTypeEntity().equals(TypeEntity.CAT))
                pet.setImage(catDead);
            else
                pet.setImage(dogDead);
        }
    }

    private void createTranslateTransition(Pane pane, ImageView pet, float v, Side side) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), pet);
        tt.setByX(v);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        tt.play();
        tt.setOnFinished(actionEvent -> setSpriteAfterMove(pane, pet, side));
    }

    private void setSpriteAfterMove(Pane pane, ImageView pet, Side side) {
        GameParameters parameters = GameParameters.instance;
        if (parameters.getPet().getTypeEntity().equals(TypeEntity.CAT)) {
            setSize(pet, (ImageView) pane.getScene().lookup("#background"));
            if (side.equals(Side.LEFT))
                pet.setImage(catSitLeft);
            else
                pet.setImage(catSitRight);
        } else {
            setSize(pet, (ImageView) pane.getScene().lookup("#background"));
            if (side.equals(Side.LEFT))
                pet.setImage(dogSitLeft);
            else
                pet.setImage(dogSitRight);
        }
    }

    private void setAnimationSizes(ImageView pet, boolean move) {
        if (!GameParameters.instance.getPet().getTypeEntity().equals(TypeEntity.DOG) || !move) {
            pet.setFitHeight(pet.getFitHeight() + 30);
            pet.setFitWidth(pet.getFitWidth() + 30);
            pet.setY(pet.getY() - 30);
        }
    }

    private Image defineAnimation(Side side) {
        GameParameters parameters = GameParameters.instance;
        TypeEntity typeEntity = parameters.getPet().getTypeEntity();
        if (typeEntity.equals(TypeEntity.CAT)) {
            if (side.equals(Side.LEFT))
                return catWalkLeft;
            else
                return catWalkRight;
        } else {
            if (side.equals(Side.LEFT))
                return dogWalkLeft;
            else
                return dogWalkRight;
        }
    }

    private void endOfAnimation(Pane pane, ImageView pet) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                setSpriteAfterMove(pane, pet, Side.RIGHT);
                this.action = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Image defineAnimation(String action) {
        GameParameters parameters = GameParameters.instance;
        TypeEntity typeEntity = parameters.getPet().getTypeEntity();
        if (typeEntity.equals(TypeEntity.CAT)) {
            if (action.equals("Eat"))
                return new Image(new File(CatSprites.CAT_EAT)
                        .toURI().toString());
            else
                return catPlay;
        } else {
            if (action.equals("Eat"))
                return new Image(new File(DogSprites.DOG_EAT)
                        .toURI().toString());
            else
                return new Image(new File(DogSprites.DOG_PLAY)
                        .toURI().toString());
        }
    }

    public ImageView definePet(Pane pane, ImageView background) {
        ImageView petSprite = new ImageView();
        Pet pet = GameParameters.instance.getPet();
        if (pet.getTypeEntity().equals(TypeEntity.CAT)) {
            petSprite.setImage(catSitRight);
        } else {
            petSprite.setImage(dogSitLeft);
        }
        setSize(petSprite, background);
        return petSprite;
    }

    private void checkAllParametersOfPep(Pane pane) {
        ImageView mood = (ImageView) pane.getScene().lookup("#mood");
        ImageView hungry = (ImageView) pane.getScene().lookup("#hungry");
        commonService.setIndicators(mood, hungry);
        GameParameters parameters = GameParameters.instance;
        if (parameters.getPet().getMood().equals(Mood.GOOD) && !parameters.getPet().isHungry()) {
            parameters.setLastTimeCheck(0L);
        }
    }
}
