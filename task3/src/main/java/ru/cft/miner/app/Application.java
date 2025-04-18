package ru.cft.miner.app;

import ru.cft.miner.controller.GameControllerImpl;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameModelImpl;
import ru.cft.miner.view.GameView;

public class Application {

    public static void main(String[] args) {
        GameModel model = new GameModelImpl();
        GameView view = new GameView(model);
        GameControllerImpl controller = new GameControllerImpl(view, model);
        controller.initGame();
        view.show();
    }
}
