package ru.cft.miner.model.observer;

import ru.cft.miner.model.FlagDto;

public interface FlagChangeListener {

    void onFlagChange(FlagDto flag);
}
