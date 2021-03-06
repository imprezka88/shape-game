package com.ewareza.shapegame.app;

import android.graphics.Canvas;
import android.graphics.Point;
import com.ewareza.shapegame.app.shapeColorGame.ShapeColorGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.SingleGame;
import com.ewareza.shapegame.app.shapeColorGame.singleGame.generator.SingleGameFactory;
import com.ewareza.shapegame.resources.SoundResources;

import java.util.logging.Logger;

public class GameEngine {
    private final Object lock = new Object();
    private final SoundResources soundResources = SoundResources.INSTANCE;
    private Logger logger = Logger.getLogger(GameEngine.class.getName());
    private SingleGame currentSingleGame;
    private String gameType;

    public GameEngine(String gameType) {
        this.gameType = gameType;
    }

    public void generateNewGame() {
        synchronized (lock) {
            ShapeColorGame.incrementGameNumber();
            currentSingleGame = SingleGameFactory.createNewSingleGame(gameType);
        }
    }

    public void update() {
        synchronized (lock) {
            currentSingleGame.update();
        }
    }

    public void draw(Canvas canvas) {
        synchronized (lock) {
            currentSingleGame.draw(canvas);
        }
    }

    public void onScreenTouched(Point point) {
        synchronized (lock) {
            currentSingleGame.onScreenTouched(point);
        }
    }

    public int getNumberOfLookedForShapesOnScreen() {
        return currentSingleGame.getNumberOfLookedForShapesOnScreen();
    }

    public void playWonGame() {
        soundResources.playWonGame();
    }

    public void drawGameTitleShape(Canvas canvas) {
        currentSingleGame.getGameTitleShape().draw(canvas);
    }
}
