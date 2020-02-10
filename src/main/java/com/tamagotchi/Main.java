package com.tamagotchi;

import com.google.gson.Gson;
import com.tamagotchi.controller.MainController;
import com.tamagotchi.dto.GameParametersDto;
import com.tamagotchi.entities.Pet;
import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.properties.GameData;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.impl.CommonServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    private static MediaPlayer mediaPlayer;
    private static final Gson gson = new Gson();
    private static MainController mainController;
    private CommonService commonService;

    {
        this.commonService = CommonServiceImpl.instance;
        checkGameDataFile();
        GameParametersDto gameParametersDto = readFile();
        GameParameters gameParameters = GameParameters.instance;
        if (gameParametersDto != null) {
            configurePet(gameParametersDto);
            gameParameters.setBirthDay(gameParametersDto.getBirthDay());
            gameParameters.setLastTimeCheck(gameParametersDto.getLastTimeCheck());
            gameParameters.setLastTimeEat(gameParametersDto.getLastTimeEat());
            gameParameters.setLasTimePlay(gameParametersDto.getLasTimePlay());
            gameParameters.setWeather(gameParametersDto.getWeather());
            gameParameters.setDead(gameParametersDto.isDead());
        }
    }

    private void configurePet(GameParametersDto gameParametersDto) {
        if (gameParametersDto.getPet() != null) {
            Pet pet = Pet.getInstance();
            pet.setName(gameParametersDto.getPet().getName());
            pet.setMood(gameParametersDto.getPet().getMood());
            pet.setTypeEntity(gameParametersDto.getPet().getTypeEntity());
            pet.setHungry(gameParametersDto.getPet().isHungry());
            pet.setPetState(gameParametersDto.getPet().getPetState());
            GameParameters.instance.setPet(pet);
        }
    }

    private GameParametersDto readFile() {
        try (BufferedReader bf = new BufferedReader(new FileReader(GameData.SAVE_DATA_FILE_NAME))) {
            String DBInfo = bf.readLine();
            return gson.fromJson(DBInfo, GameParametersDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkGameDataFile() {
        File file = new File(GameData.SAVE_DATA_FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeFile(GameParametersDto gameParametersDto) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(GameData.SAVE_DATA_FILE_NAME))) {
            String data = gson.toJson(gameParametersDto);
            br.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Media sound = new Media(new File(GameData.MUSIC_FILE).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        FXMLLoader loader = new FXMLLoader();
        String resource = defineResource();
        loader.setLocation(getClass().getResource(resource));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        if (resource.equals(GameData.MAIN_SCENE))
            mainController = loader.getController();
        primaryStage.show();
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        commonService.stop();
        mediaPlayer.stop();
        formDataFileAndWrite();
    }

    private void formDataFileAndWrite() {
        GameParameters gameParameters = GameParameters.instance;
        GameParametersDto gameParametersDto = new GameParametersDto();
        gameParametersDto.setPet(gameParameters.getPet());
        gameParametersDto.setBirthDay(gameParameters.getBirthDay());
        gameParametersDto.setLastTimeCheck(gameParameters.getLastTimeCheck());
        gameParametersDto.setLasTimePlay(gameParameters.getLasTimePlay());
        gameParametersDto.setLastTimeEat(gameParameters.getLastTimeEat());
        gameParametersDto.setWeather(gameParameters.getWeather());
        gameParametersDto.setDead(gameParameters.isDead());
        writeFile(gameParametersDto);
    }

    private String defineResource() {
        if (GameParameters.instance.getPet() != null) {
            if (GameParameters.instance.isDead()) {
                return GameData.START_MENU;
            }
            return GameData.MAIN_SCENE;
        }
        return GameData.START_MENU;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        Main.mainController = mainController;
    }

    public static void main(String[] args) {
        launch();
    }
}