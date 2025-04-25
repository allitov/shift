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
        Timer timer = new Timer();
        RecordsManager recordsManager = new RecordsManager(timer);
        GameModel model = new GameModelImpl();
        GameView view = new GameViewImpl(model, timer, recordsManager);
        GameController controller = new GameControllerImpl(view, model, timer, recordsManager);
        controller.initGame();
        view.show();
    }
}
