package src.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import src.code.Pacman;
import src.code.database.DatabaseManager;

public class ClosingScreen implements Screen {
    private Pacman game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture background;
    private FitViewport viewport;
    private Stage stage;
    private BitmapFont font, titleFont;
    private Label scoreLabel;
    private Label titleLabel;
    private Label errorLabel;
    private TextField nameInput;
    private Rectangle submitButton, nameInputBox;
    private boolean isSubmitted;
    private int finalScore;

    private DatabaseManager databaseManager;
    private ShapeRenderer shapeRenderer;

    public ClosingScreen(Pacman game, int score) {
        this.game = game;
        this.finalScore = score;
        this.batch = game.batch;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(800, 600, camera);
        this.stage = new Stage(viewport, batch);
        this.font = new BitmapFont(Gdx.files.internal("fonts/army_stencil.fnt"));
        this.titleFont = new BitmapFont(Gdx.files.internal("fonts/army_stencil.fnt"));

        this.databaseManager = new DatabaseManager();
        this.shapeRenderer = new ShapeRenderer();
        background = new Texture("images/homescreen.png");

        // Title Label
        titleLabel = new Label("Summary", new Label.LabelStyle(titleFont, Color.YELLOW));
        titleLabel.setFontScale(4.0f);
        titleLabel.setPosition(250, 500);
        stage.addActor(titleLabel);

        // Score Label
        scoreLabel = new Label("Your Score: " + finalScore, new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setFontScale(2.5f);
        scoreLabel.setPosition(220, 350);
        stage.addActor(scoreLabel);

        // Name Input Field
        nameInput = new TextField("", new TextField.TextFieldStyle(font, Color.WHITE, null, null, null));
        nameInput.setMessageText("Enter Name (3 letters)");
        nameInput.setPosition(250, 256);
        nameInput.setSize(400, 50);
        stage.addActor(nameInput);
        nameInputBox = new Rectangle(300, 250, 400, 50);

        // Error Label
        errorLabel = new Label("", new Label.LabelStyle(font, Color.RED));
        errorLabel.setFontScale(2.5f);
        errorLabel.setPosition(300, 180);
        stage.addActor(errorLabel);

        // Submit Button
        submitButton = new Rectangle();
        isSubmitted = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private boolean isButtonHovered(float x, float y, float width, float height) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }

    private boolean isButtonClicked(float x, float y, float width, float height) {
        return Gdx.input.justTouched();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 800, 1000);
        batch.end();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Submit
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (isButtonHovered(300, 100, 150, 50)) {
            shapeRenderer.setColor(Color.NAVY);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(300, 100, 150, 50);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (isButtonHovered(300, 100, 150, 50)) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        shapeRenderer.rect(300, 100, 150, 50);
        shapeRenderer.end();

        // Name input
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (isButtonClicked(190, 250, 400, 50)) {
            shapeRenderer.setColor(Color.NAVY);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(190, 250, 400, 50);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (isButtonClicked(190, 250, 400, 50)) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        shapeRenderer.rect(190, 250, 400, 50);
        shapeRenderer.end();

        batch.begin();
        font.getData().setScale(1.5f);
        if (isButtonHovered(300, 100, 150, 50)) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, "Submit", 330, 137);
        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x >= 300 && touchPos.x <= 450 && touchPos.y >= 100 && touchPos.y <= 150 && !isSubmitted) {
                String playerName = nameInput.getText().trim();

                if (playerName.length() != 3) {
                    errorLabel.setPosition(100, 200);
                    errorLabel.setText(" Must be exactly 3 letters!");
                } else {
                    errorLabel.setText("");
                    databaseManager.insertHighscore(playerName, finalScore);
                    isSubmitted = true;
                    game.setScreen(new HomeScreen(game));
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        titleFont.dispose();
        databaseManager.closeConnection();
        shapeRenderer.dispose();
    }
}
