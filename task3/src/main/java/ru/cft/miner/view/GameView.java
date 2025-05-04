package ru.cft.miner.view;

import ru.cft.miner.gameutils.record.RecordData;
import ru.cft.miner.view.listener.CellEventListener;
import ru.cft.miner.view.listener.GameTypeListener;
import ru.cft.miner.view.listener.RecordNameListener;

import java.awt.event.ActionListener;
import java.util.List;

public interface GameView {

    void setHighScoresMenuAction(ActionListener actionListener);

    void drawGameField(int rows, int cols, int minesCount);

    void show();

    void showHighScores(List<RecordData> allRecords);

    void setNameListener(RecordNameListener nameListener);

    void setNewGameListener(ActionListener newGameListener);

    void setExitGameListener(ActionListener exitGameListener);

    void setCellEventListener(CellEventListener cellEventListener);

    void setGameTypeListener(GameTypeListener gameTypeListener);
}
