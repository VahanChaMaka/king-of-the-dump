package ru.grishagin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import ru.grishagin.screens.MainScreen;
import ru.grishagin.view.View;

public class Game extends com.badlogic.gdx.Game {

	private View view;
	
	@Override
	public void create () {
		screen = new MainScreen();
		setScreen(screen);

		view = new View();
		//GameController.INSTANCE.setView(view);
	}

	@Override
	public void render() {
		view.draw();
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		//AssetManager.getInstance().dispose();
	}
}
