package com.ewareza.shapegame.domain;

import android.graphics.Rect;
import com.ewareza.shapegame.domain.shape.AbstractShape;
import com.ewareza.shapegame.drawer.Drawer;

public class Splash extends AbstractShape {
    private static final String SPLASH = "splash";

    public Splash(Rect associatedRect, int color, Drawer drawer) {
        super(associatedRect, color, drawer);

    }

    @Override
    public String getName() {
        return SPLASH;
    }
}