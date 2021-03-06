package com.ewareza.shapegame.app.shapeColorGame;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import com.ewareza.shapegame.app.DisplayThread;
import com.ewareza.shapegame.app.utils.GameUtils;
import com.ewareza.shapegame.resources.DimenRes;
import com.ewareza.shapegame.resources.ImageResources;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ShapeGameDisplayThread extends DisplayThread {
    private final static Logger Log = Logger.getLogger(ShapeGameDisplayThread.class.getName());
    private AtomicInteger left;
    private AtomicInteger top;
    private AtomicInteger right;
    private AtomicInteger bottom;
    private Drawable gameOverImage;
    private AtomicBoolean gameOverImageSet = new AtomicBoolean(false);
    private int gameOverImageVerticalSpeed = 10;
    private int gameOverImageHorizontalSpeed = 3;

    public ShapeGameDisplayThread(SurfaceHolder surfaceHolder) {
        super(surfaceHolder);
        initGameOverImagePosition();
    }

    @Override
    protected void updatePhysics() {
        if (!ShapeColorGame.isGameOver())
            ShapeColorGame.updatePhysics();
    }

    @Override
    protected void drawUpdatedView(Canvas canvas) {
        clearScreen(canvas);

        if (ShapeColorGame.isGameOver()) {
            if (!gameOverImageSet.get()) {
                gameOverImage = GameOverImageFactory.getGameOverImage();
                gameOverImageSet.set(true);
            }

            gameOverImage.setBounds(left.addAndGet(gameOverImageHorizontalSpeed), top.addAndGet(-gameOverImageVerticalSpeed), right.addAndGet(gameOverImageHorizontalSpeed), bottom.addAndGet(-gameOverImageVerticalSpeed));
            gameOverImage.draw(canvas);
        } else {
            initGameOverImagePosition();
            gameOverImageSet.set(false);
            drawGameTitle(canvas);
            drawShapes(canvas);
        }
    }

    private void initGameOverImagePosition() {
        left = new AtomicInteger(DimenRes.getScreenWidth() / 2 - 400);
        top = new AtomicInteger(DimenRes.getScreenHeight());
        right = new AtomicInteger(DimenRes.getScreenWidth() / 2);
        bottom = new AtomicInteger(DimenRes.getScreenHeight() + 400);
    }

    private void drawShapes(Canvas canvas) {
        ShapeColorGame.getEngine().draw(canvas);
    }

    private void drawGameTitle(Canvas canvas) {
        int gameTitleHeight = DimenRes.getGameTitleHeight();
        int screenWidth = DimenRes.getScreenWidth();

        ShapeColorGame.drawGameTitleShape(canvas);
        canvas.drawLine(0, gameTitleHeight, screenWidth, gameTitleHeight + 1, GameUtils.getGameTitleLinePaint());
    }

    private void clearScreen(Canvas canvas) {
        Drawable drawable = ImageResources.getInstance().getGameBackground();
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
    }

    @Override
    protected void tryToSleep() {
        try {
            if (!ShapeColorGame.isGameOver())
                Thread.sleep(ShapeColorGame.getGameSpeedForCurrentGame());
        } catch (InterruptedException ex) {
            //TODO: Log
        }
    }
}
