package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.GameIngenious;

//import com.game.GameMain;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ingenious";
		config.width = 1000;
		config.height = 800;
		new LwjglApplication(new GameIngenious(), config);
		//new LwjglApplication(new GameMain(), config);
	}
}




