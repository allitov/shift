package ru.cft.miner.app;

import ru.cft.miner.controller.GameController;
import ru.cft.miner.controller.GameControllerImpl;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameModelImpl;
import ru.cft.miner.view.ButtonType;
import ru.cft.miner.view.CellEventListener;
import ru.cft.miner.view.GameType;
import ru.cft.miner.view.MainWindow;

public class Application {

    public static void main(String[] args) {
        MainWindow view = new MainWindow();
        GameModel model = new GameModelImpl();
        GameControllerImpl controller = new GameControllerImpl(view, model);
        controller.initGame(GameType.NOVICE);
        view.setCellListener((x, y, buttonType) -> {
            switch (buttonType) {
                case LEFT_BUTTON -> controller.openCell(x, y);
                case RIGHT_BUTTON -> controller.setFlag(x, y);
                case MIDDLE_BUTTON -> {/* TODO: not implemented yet */}
            }
        });
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }
}
