package com.tamagotchi.walk;

import com.tamagotchi.parameters.GameParameters;
import com.tamagotchi.services.CommonService;
import com.tamagotchi.services.PetService;
import com.tamagotchi.services.impl.CommonServiceImpl;
import com.tamagotchi.services.impl.PetServiceImpl;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.TimerTask;

public class Walking extends TimerTask {
    private final Pane pane;
    private Side side;
    private final Random random;
    private final CommonService commonService;
    private final PetService petService;


    public Walking(Pane pane) {
        this.side = Side.CENTER;
        random = new Random();
        this.commonService = CommonServiceImpl.instance;
        this.pane = pane;
        this.petService = PetServiceImpl.instance;
    }

    @Override
    public void run() {
        if (pane.getScene() != null) {
            ImageView pet = (ImageView) pane.getScene().lookup("#pet");
            if (!commonService.parseUrl(pet.getImage().getUrl()).contains("Eat")
                    && !commonService.parseUrl(pet.getImage().getUrl()).contains("Play")
                    && !GameParameters.instance.isDead()) {
                if (side.equals(Side.RIGHT)) {
                    this.side = petService.moveLeft(pane, pet, side);
                } else if (side.equals(Side.LEFT)) {
                    this.side = petService.moveRight(pane, pet, side);
                } else if (side.equals(Side.CENTER)) {
                    int i = random.nextInt(2);
                    if (i == 1)
                        this.side = petService.moveLeft(pane, pet, side);
                    else
                        this.side = petService.moveRight(pane, pet, side);
                }
            }
        }
    }
}
