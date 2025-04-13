package ru.cft.miner.app;

import ru.cft.miner.controller.GameControllerImpl;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameModelImpl;
import ru.cft.miner.view.HighScoresWindow;
import ru.cft.miner.view.LoseWindow;
import ru.cft.miner.view.MainWindow;
import ru.cft.miner.view.RecordsWindow;
import ru.cft.miner.view.SettingsWindow;
import ru.cft.miner.view.WinWindow;

public class Application {

    public static void main(String[] args) {
        // view init
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
//        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
//        LoseWindow loseWindow = new LoseWindow(mainWindow);
//        WinWindow winWindow = new WinWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);

        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> System.exit(0));

        GameModel model = new GameModelImpl();
        GameControllerImpl controller = new GameControllerImpl(mainWindow, model);
        controller.initializeGame();
    }
}
