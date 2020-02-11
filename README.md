# Tamagotchi
This app is a parody on famous game from the 90's. This guide help you to know basics things.
If you have a problems with start application - check following things:

1. You must have installed Maven.
2. If you are haven't gor an JavaFx on your computer - download it from: https://gluonhq.com/products/javafx 
and go to the VM options: Run -> Edit Configuration -> VM options
and paste this: --module-path/opt/javafx-sdk-11.0.2/lib--add-modules=javafx.controls,javafx.graphics,javafx.fxml,
javafx.base,javafx.media

Add your path to /lib directory into JavaFx on your computer instead '/opt/javafx-sdk-11.0.2/lib' in example.

-------------------------------------------------------------------------------------------------------------------
                                                About the game
When you download and run this app you will want to configure parameters of this game. You may do this in 
gameValues.properties (src/main/resources/GameValues.properties). There are two pet in this game. You must choose one when
starting. Then your are begin to look for the pet: give eat and play. If you forget about your pet - he will die. 
To prevent it watch on the control panel on the top of the monitor and remember about the time, because pet miss you 
even when application is stopped.
