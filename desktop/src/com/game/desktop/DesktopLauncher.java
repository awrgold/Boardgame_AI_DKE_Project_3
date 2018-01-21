package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.GameIngenious;
import org.lwjgl.opengl.Display;

import java.awt.*;


public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		config.title = "Ingenious";
	//	config.useGL30 = true;
		config.width = 1000;
		config.height = 800;
		new LwjglApplication(new GameIngenious(), config);


		//GameIngenious newGame = new GameIngenious();
		//newGame.create();

	}
}




