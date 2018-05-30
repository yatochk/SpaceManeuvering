package com.yatochk.maneuvering;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean isPause = true;

	private boolean isTracking;
	private boolean isStarted;
	static boolean autoStart;

	static Planet[] planets;
	private Satellite[] satellites;
	private Spacecraft rocket;
	private BitmapFont font;

	public void create() {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 700.0F, 700.0F);

		font = new BitmapFont();
		font.setColor(Color.WHITE);

		// Set sun location
		int xSun = (int)camera.viewportWidth / 2 - 100;
		int ySun = (int)camera.viewportHeight / 2;

		// Set sun and planet
		planets = new Planet[] {
				new Planet("sun.png", 0.0D, 20, xSun, ySun, 1.0D, 1.989E30D),
				new Planet("mercury.png", 0.39D, 5, xSun, ySun, 0.241D, 3.2868E23D),
				new Planet("venus.png", 0.72D, 6, xSun, ySun, 0.616D, 4.81068E24D),
				new Planet("earth.png", 1.0D, 6, xSun, ySun, 1.0D, 5.976E24D),
				new Planet("mars.png", 1.52D, 5, xSun, ySun, 1.88D, 6.3345E23D),
				new Planet("jupiter.png", 5.2D, 18, xSun, ySun, 11.86D, 1.87664328E27D),
				new Planet("saturn.png", 9.54D, 15, xSun, ySun, 29.46D, 5.6180376E26D),
				new Planet("uranus.png", 19.22D, 10, xSun, ySun, 84.02D, 8.60544E25D),
				new Planet("neptune.png", 30.06D, 13, xSun, ySun, 164.78D, 1.01592E26D)
		};

		// Set satellites (moon)
		satellites = new Satellite[] {
				new Satellite("moon.jpg", 0.15D, 3, xSun, ySun, 0.075D, planets[3])
		};

		Gdx.input.setInputProcessor(new InputProcessor() {
			public boolean keyDown(int keycode) {
				switch (keycode) {
					case 62:
						isPause = (!isPause);
						break;
					case 52:
						isTracking = (!isTracking);
						break;
					case 47:
						if ((!isStarted) || (rocket.isSingularity)) {
							rocket = new Spacecraft("sun.png", 4, MyGdxGame.planets[3], 16700.0D);
							isStarted = true;
							isTracking = true;
						}
						break;
					case 51:
						MyGdxGame.autoStart = true;
				}

				return false;
			}

			public boolean keyUp(int keycode)
			{
				return false;
			}

			public boolean keyTyped(char character) {
				return false;
			}

			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				return false;
			}

			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				return false;
			}

			public boolean mouseMoved(int screenX, int screenY)
			{
				return false;
			}

			public boolean scrolled(int amount) {
				return false;
			}
		});
	}


	public void render() { handleInput();
		if ((isTracking) && (!rocket.isSingularity) && (!isPause)) {
			camera.translate(rocket.dX, rocket.dY);
		}

		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//Draw block
		batch.begin();

		for (Planet planet : planets) {
			planet.render(batch, isPause);
		}

		for (Satellite satellite : satellites) {
			satellite.render(batch, isPause);
		}

		if ((isStarted) && (!rocket.isSingularity)) {
			rocket.render(batch, isPause);

			int solarX = rocket.x - planets[0].x;
			int solarY = rocket.y - planets[0].y;
			int velocity = (int) Math.round(Math.sqrt(rocket.vX * rocket.vX + rocket.vY * rocket.vY));
			int acceleration = (int)(Math.sqrt(rocket.aX * rocket.aX + rocket.aY * rocket.aY) * 1000000.0D);

			font.draw(batch, "x: " + solarX + " * 10^6 m", rocket.x, rocket.y + 90);
			font.draw(batch, "y: " + solarY + " * 10^6 m", rocket.x, rocket.y + 70);
			font.draw(batch, "v: " + velocity + " m/s", rocket.x, rocket.y + 50);
			font.draw(batch, "a: " + acceleration + " * 10^-6 m/s^2", rocket.x, rocket.y + 30);
		}

		if ((isPause) && (!isStarted)) {
			font.draw(batch, "q - zoom +", planets[3].x, planets[3].y + 90);
			font.draw(batch, "a - zoom -", planets[3].x, planets[3].y + 70);
			font.draw(batch, "x - on/off camera movement", planets[3].x, planets[3].y + 50);
			font.draw(batch, "space - pause/play", planets[3].x, planets[3].y - 30);
			font.draw(batch, "s - start rocket", planets[3].x, planets[3].y - 50);
			font.draw(batch, "push w and s - use auto start", planets[3].x, planets[3].y - 70);
		}

		if ((autoStart) && (isStarted) && (rocket.onPlanet)) {
			font.draw(batch, "You have used auto start!", planets[3].x, planets[3].y - 90);
		}
		batch.end();
		//End draw block

	}

	private void handleInput() {
		if ((Gdx.input.isKeyPressed(45)) &&
				(camera.viewportWidth > 100.0F)) {
			camera.viewportHeight -= 40.0F;
			camera.viewportWidth -= 40.0F;
		}
		if (Gdx.input.isKeyPressed(29)) {
			camera.viewportHeight += 40.0F;
			camera.viewportWidth += 40.0F;
		}
		if (Gdx.input.isKeyPressed(54)) {
			camera.viewportHeight = 2000.0F;
			camera.viewportWidth = 2000.0F;
		}
		if (Gdx.input.isKeyPressed(21)) {
			camera.translate(-8.0F, 0.0F);
		}
		if (Gdx.input.isKeyPressed(22)) {
			camera.translate(8.0F, 0.0F);
		}
		if (Gdx.input.isKeyPressed(20)) {
			camera.translate(0.0F, -8.0F);
		}
		if (Gdx.input.isKeyPressed(19)) {
			camera.translate(0.0F, 8.0F);
		}
	}
}