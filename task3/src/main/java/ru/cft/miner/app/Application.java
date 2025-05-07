package ru.cft.miner.app;

import ru.cft.miner.controller.GameController;
import ru.cft.miner.controller.GameControllerImpl;
import ru.cft.miner.gameutils.record.RecordsManager;
import ru.cft.miner.gameutils.timer.Timer;
import ru.cft.miner.model.GameModel;
import ru.cft.miner.model.GameModelImpl;
import ru.cft.miner.view.GameView;
import ru.cft.miner.view.GameViewImpl;

public class Application {

    public static void main(String[] args) {
        GameModel model = new GameModelImpl();
        Timer timer = new Timer(model);
        RecordsManager recordsManager = new RecordsManager(model, timer);
        GameView view = new GameViewImpl(model, timer, recordsManager);
        GameController controller = new GameControllerImpl(view, model, recordsManager);
        controller.initGame();
        view.show();
    }
}
