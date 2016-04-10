package com.example.dima.battlesheeps.UI.UIListeners;

public interface GameActivityRivalFieldListener {
    void onPlayerPlayed(int x, int y, String status, boolean isGameOver, boolean isPlayerWon, boolean isRivalWon, int[] shipCount);
}
