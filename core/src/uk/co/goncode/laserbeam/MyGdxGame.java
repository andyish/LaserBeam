package uk.co.goncode.laserbeam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class MyGdxGame extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
	SpriteBatch batch;

    TextureRegion startBackground;
    TextureRegion startOverlay;
    TextureRegion midBackground;
    TextureRegion midOverlay;
    TextureRegion endBackground;
    TextureRegion endOverlay;
    TextureRegion animation;

    Vector2 middle;
    float rotation;
    private OrthographicCamera camera;
    private ScalingViewport viewport;

    private float distance;
    private float minDistance;

    @Override
	public void create () {
        shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
        startBackground = new TextureRegion(new Texture("start/background.png"));
        startOverlay = new TextureRegion(new Texture("start/overlay.png"));

        midBackground = new TextureRegion(new Texture("middle/background.png"));
        midOverlay = new TextureRegion(new Texture("middle/overlay.png"));

        endBackground = new TextureRegion(new Texture("end/background.png"));
        endOverlay = new TextureRegion(new Texture("end/overlay.png"));

        animation = new TextureRegion(new Texture("overlay-animation.png"));

        Gdx.input.setInputProcessor(inputProcessor);
        middle = new Vector2(0, 0);

        viewport = new ScalingViewport(Scaling.fit, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(middle, 0);
        camera.update();

        viewport.setCamera(camera);
        viewport.apply();

        minDistance = startBackground.getRegionHeight() / 2 + endBackground.getRegionHeight() / 2;
	}

	@Override
	public void render () {
        camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

		batch.begin();

        batch.setColor(Color.RED);
        batch.draw(startBackground,
                middle.x, middle.y,
                startBackground.getRegionWidth() / 2, startBackground.getRegionHeight() / 2,
                startBackground.getRegionWidth(), startBackground.getRegionHeight(),
                1.0f, 1.0f, rotation);
        batch.draw(midBackground,
                middle.x, middle.y + startBackground.getRegionHeight(),
                midBackground.getRegionWidth() / 2, -(startBackground.getRegionHeight() / 2),
                midBackground.getRegionWidth(), distance,
                1.0f, 1.0f, rotation);
        batch.draw(endBackground,
                middle.x, middle.y + endBackground.getRegionHeight() + distance,
                endBackground.getRegionWidth() / 2, -(endBackground.getRegionHeight() / 2 + distance),
                endBackground.getRegionWidth(), endBackground.getRegionHeight(),
                1.0f, 1.0f, rotation);

        batch.setColor(Color.ORANGE);

        batch.draw(startOverlay,
                middle.x, middle.y,
                startOverlay.getRegionWidth() / 2, startOverlay.getRegionHeight() / 2,
                startOverlay.getRegionWidth(), startOverlay.getRegionHeight(),
                1.0f, 1.0f, rotation);

        batch.draw(midOverlay,
                middle.x, middle.y + startBackground.getRegionHeight(),
                midOverlay.getRegionWidth() / 2, -(midOverlay.getRegionHeight() / 2),
                midOverlay.getRegionWidth(), distance,
                1.0f, 1.0f, rotation);

        batch.draw(endOverlay,
                middle.x, middle.y + endOverlay.getRegionHeight() + distance,
                endOverlay.getRegionWidth() / 2, -(endOverlay.getRegionHeight() / 2  + distance),
                endOverlay.getRegionWidth(), endOverlay.getRegionHeight(),
                1.0f, 1.0f, rotation);

        batch.setColor(Color.YELLOW);
//        batch.draw(animation,
//                middle.x, middle.y + animation.getRegionHeight() / 2,
//                animation.getRegionWidth() / 2, -(animation.getRegionHeight() / 4),
//                animation.getRegionWidth(), middle.y + animation.getRegionHeight() / 2 + distance - endOverlay.getRegionHeight() / 2,
//                1.0f, 1.0f, rotation);
		batch.end();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    InputProcessor inputProcessor = new InputProcessor() {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            Vector3 unproject = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector2 screenPos = new Vector2(unproject.x, unproject.y);

            rotation = (float) angleBetween(middle, screenPos) - 90;

            distance = screenPos.dst(middle);

            if(distance < minDistance)
                distance = minDistance;

            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    };

    public double angleBetween(Vector2 p1, Vector2 p2) {
        double xDiff = p2.x - p1.x;
        double yDiff = p2.y - p1.y;

        return Math.toDegrees(Math.atan2(yDiff, xDiff));
    }

    private void print(Object o) {
        System.out.println(o.toString());
    }
}
