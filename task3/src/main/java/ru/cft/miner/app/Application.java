package ru.cft.miner.app;

import ru.cft.miner.controller.GameControllerImpl;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameModelImpl;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.MainWindow;
import ru.cft.miner.view.SettingsWindow;

public class Application {

    public static void main(String[] args) {
        MainWindow view = new MainWindow();
        GameModel model = new GameModelImpl();
        GameControllerImpl controller = new GameControllerImpl(view, model, GameType.NOVICE);
        controller.initGame();
        view.setCellListener((x, y, buttonType) -> {
            switch (buttonType) {
                case LEFT_BUTTON -> controller.openCell(y, x);
                case RIGHT_BUTTON -> controller.setFlag(y, x);
                case MIDDLE_BUTTON -> {/* TODO: not implemented yet */}
            }
        });
        view.setExitMenuAction(e -> System.exit(0));
        view.setNewGameMenuAction(e -> controller.initGame());
        view.setVisible(true);
        SettingsWindow settingsWindow = new SettingsWindow(view);
        settingsWindow.setGameTypeListener(controller);
        view.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        view.setLocationRelativeTo(null);
    }
}
