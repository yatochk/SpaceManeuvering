package com.yatochk.maneuvering;

import com.badlogic.gdx.graphics.Texture;

class Spacecraft extends CosmicBody {

    private static final double M_IN_PIXEL = 1.0E9;
    private static final double G = 6.67E-11;
    private static final double UNKNOWN_FAULT = 3;
    double vX;
    double aX;
    private double xReal;
    private double yReal;
    double vY;
    double aY;
    boolean isSingularity;
    boolean onPlanet = true;
    private Planet planet;

    Spacecraft(String textureWay, int spacecraftRadius, Planet planet, double speed) {
        this.texture = new Texture(textureWay);
        this.radius = spacecraftRadius;
        this.angle = Math.PI;
        this.planet = planet;
        this.x = planet.x + planet.radius;
        this.y = planet.y + planet.radius;
        this.xReal = (double)this.x * M_IN_PIXEL;
        this.yReal = (double)this.y * M_IN_PIXEL;
        this.vY = speed * UNKNOWN_FAULT / Math.sqrt(2.0);
        this.vX = speed * UNKNOWN_FAULT / Math.sqrt(2.0);
    }

    @Override
    void move() {
        if (MyGdxGame.autoStart) {
            if (MyGdxGame.planets[3].timeToStart) {
                onPlanet = false;
                translate();
            }
            else {
                x = planet.x + planet.radius;
                y = planet.y + planet.radius;
                xReal = (double)x * 1.0E9;
                yReal = (double)y * 1.0E9;
            }
        }
        else {
            translate();
        }
    }

    private void translate() {
        aX = 0.0;
        aY = 0.0;
        int xLast = x;
        int yLast = y;

        for (Planet planet1 : MyGdxGame.planets) {
            double dXReal = (double)planet1.x * 1.0E9 - xReal;
            double dYReal = (double)planet1.y * 1.0E9 - yReal;
            double r = Math.sqrt(dXReal * dXReal + dYReal * dYReal);
            double aXNew = planet1.weight * G * dXReal / (r * r * r);
            double aYNew = planet1.weight * G * dYReal / (r * r * r);
            aX += aXNew;
            aY += aYNew;
            if (x <= planet1.x - planet1.radius || x >= planet1.x + planet1.radius || y <= planet1.y - planet1.radius || y >= planet1.y + planet1.radius) continue;
            this.isSingularity = true;
        }

        double t = 43200;
        vX += aX * t;
        vY += aY * t;
        xReal += vX * t + aX * t * t / 2.0;
        yReal += vY * t + aY * t * t / 2.0;
        x = (int)(xReal / M_IN_PIXEL);
        y = (int)(yReal / M_IN_PIXEL);
        dX = x - xLast;
        dY = y - yLast;
    }
}
