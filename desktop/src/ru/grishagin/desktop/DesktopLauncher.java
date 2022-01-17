package ru.grishagin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import ru.grishagin.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "King of the Dump";
		new LwjglApplication(new Game(), config);

		String inputDir = "tmp_sources";
		String outputDir = "ui/icons";
		String packFileName = "icons";
		//TexturePacker.process(inputDir, outputDir, packFileName);
	}
}
