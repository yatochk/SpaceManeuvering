package com.yatochk.maneuvering;

import com.badlogic.gdx.graphics.Texture;

class Planet extends CosmicBody {

    double weight;
    boolean timeToStart;

    Planet(String textureWay, double orbitRadius, int planetRadius, int xSun, int ySun, double periodCirculation, double weight) {
        this.orbitRadius = (int)(orbitRadius * PX_A_E);
        this.radius = planetRadius;
        this.x = xSun;
        this.y = ySun;
        this.angleSpeed = Math.PI / (periodCirculation * EARTH_YEAR);
        this.weight = weight;
        this.angle = 0;
        this.xStart = xSun;
        this.yStart = ySun;
        this.x = this.xStart + this.orbitRadius;
        this.y = this.yStart;
        this.texture = new Texture(textureWay);
    }

    @Override
    void move() {
        x = (int)((double)this.xStart + (double)this.orbitRadius * Math.cos(this.angle));
        y = (int)((double)this.yStart + (double)this.orbitRadius * Math.sin(this.angle));

        angle += angleSpeed;

        if (angle >= Math.PI * 1.1f) {
            timeToStart = true;
        }
    }
}
