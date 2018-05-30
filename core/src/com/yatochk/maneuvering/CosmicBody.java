package com.yatochk.maneuvering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract class CosmicBody {
    Texture texture;
    int orbitRadius;
    int xStart;
    int yStart;
    double angle;
    double angleSpeed;
    int radius;
    int y;
    int x;
    int dX;
    int dY;
    static final double PX_A_E = 150.0;
    static final int EARTH_YEAR = 365;

    abstract void move();

    void render(SpriteBatch batch, boolean isPause) {
        if (!isPause) {
            this.move();
        }
        batch.draw(texture, (float)(x - radius), (float)(y - radius), (float)(radius * 2), (float)(radius * 2));
    }
}
