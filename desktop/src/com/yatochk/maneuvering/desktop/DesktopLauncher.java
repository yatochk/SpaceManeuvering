package com.yatochk.maneuvering.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.yatochk.maneuvering.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "seconduser");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGame(), config);
	}
}
