package ru.cft.miner.model.observer;

import ru.cft.miner.model.field.FlagDto;

public interface FlagChangeListener {

    void onFlagChange(FlagDto flag);
}
