package ru.cft.miner.model.listener;

import ru.cft.miner.model.field.FlagDto;

public interface FlagChangeListener {

    void onFlagChange(FlagDto flag);
}
