package com.yatochk.maneuvering;

import com.badlogic.gdx.graphics.Texture;

class Satellite extends CosmicBody {

    private Planet planet;

    Satellite(String textureWay, double orbitRadius, int Radius, int xSun, int ySun, double periodCirculation, Planet planet) {
        this.orbitRadius = (int)(orbitRadius * PX_A_E);
        this.radius = Radius;
        this.x = xSun;
        this.y = ySun;
        this.angleSpeed = Math.PI / (periodCirculation * EARTH_YEAR);
        this.planet = planet;
        this.angle = Math.PI;
        this.xStart = xSun;
        this.yStart = ySun;
        this.x = this.xStart + planet.orbitRadius + this.orbitRadius;
        this.y = this.yStart;
        this.texture = new Texture(textureWay);
    }

    @Override
    void move() {
        x = (int)((double)planet.x + (double)orbitRadius * Math.cos(this.angle));
        y = (int)((double)planet.y + (double)orbitRadius * Math.sin(this.angle));
        angle += angleSpeed;
    }
}
