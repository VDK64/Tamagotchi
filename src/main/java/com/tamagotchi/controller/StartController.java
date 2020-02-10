package com.tamagotchi.controller;

import com.tamagotchi.parameters.TypeEntity;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.PetService;
import com.tamagotchi.services.impl.CommonServiceImpl;
import com.tamagotchi.services.impl.PetServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class StartController {
    private CommonService commonService;
    private PetService petService;
    @FXML
    private Button dogButton;

    @FXML
    private Label errMessage;

    @FXML
    private TextField nameField;

    @FXML
    public void initialize() {
        this.commonService = CommonServiceImpl.instance;
        this.petService = PetServiceImpl.instance;
        commonService.noteIfDead(errMessage);
    }

    @FXML
    void chooseCat() throws IOException {
        if (commonService.validateChoice(errMessage, nameField))
            return;
        petService.createPet(TypeEntity.CAT, nameField.getText());
        commonService.toTheMainScene(dogButton);
    }

    @FXML
    void chooseDog() throws IOException {
        if (commonService.validateChoice(errMessage, nameField))
            return;
        petService.createPet(TypeEntity.DOG, nameField.getText());
        commonService.toTheMainScene(dogButton);
    }
}