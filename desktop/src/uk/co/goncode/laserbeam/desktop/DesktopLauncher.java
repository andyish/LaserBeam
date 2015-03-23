package uk.co.goncode.laserbeam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uk.co.goncode.laserbeam.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 600;
        config.width = 600;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
