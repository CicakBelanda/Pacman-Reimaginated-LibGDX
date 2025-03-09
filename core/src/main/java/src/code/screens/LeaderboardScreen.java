package src.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import src.code.Pacman;
import src.code.database.DatabaseManager;
import java.util.List;

public class LeaderboardScreen implements Screen {
    private Stage stage;
    private Texture background;
    private Table table;
    private Pacman pacman;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    private OrthographicCamera camera;

    private DatabaseManager databaseManager;

    // Tombol Back
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;

    public LeaderboardScreen(Pacman pacman) {
        this.pacman = pacman;

        databaseManager = new DatabaseManager();

        // Inisialisasi Kamera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("images/highscorebg.png");
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/army_stencil.fnt"));

        // Title secara manual
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label title = new Label("Leaderboard", labelStyle);
        title.setFontScale(2);
        title.setColor(Color.WHITE);
        stage.addActor(title);

        // Table for Leaderboard
        table = new Table();
        table.setFillParent(true);
        table.top().padTop(15);

        table.add(title).colspan(2);
        table.row();
        table.add(new Label("Name", labelStyle)).pad(10);
        table.add(new Label("Score", labelStyle)).pad(10);
        table.row();

        loadHighscores();

        stage.addActor(table);

        // Posisi Tombol Back di Kiri Atas
        backButtonWidth = 140;
        backButtonHeight = 40;
        backButtonX = 20; // 20 piksel dari kiri
        backButtonY = Gdx.graphics.getHeight() - backButtonHeight - 20; // 20 piksel dari atas
    }

    @Override
    public void show() {}

    private boolean isButtonHovered(float x, float y, float width, float height) {
        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Konversi koordinat layar ke dunia

        return touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height;
    }

    private void loadHighscores() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        // Ambil data dari database
        List<String[]> highscores = databaseManager.getHighscores();

        for (String[] entry : highscores) {
            table.add(new Label(entry[0], labelStyle)).pad(3);
            table.add(new Label(entry[1], labelStyle)).pad(3);
            table.row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Gambar Background
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        // Gambar Tombol Back
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (isButtonHovered(backButtonX, backButtonY, backButtonWidth, backButtonHeight)) {
            shapeRenderer.setColor(Color.NAVY);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        shapeRenderer.end();

        // Gambar Outline Tombol
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (isButtonHovered(backButtonX, backButtonY, backButtonWidth, backButtonHeight)) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        shapeRenderer.rect(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        shapeRenderer.end();

        // Gambar Teks Tombol Back
        spriteBatch.begin();
        if (isButtonHovered(backButtonX, backButtonY, backButtonWidth, backButtonHeight)) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.getData().setScale(1.1f);
        font.draw(spriteBatch, "Back", backButtonX + backButtonWidth / 2 - 22, backButtonY + backButtonHeight / 2 + 9);
        spriteBatch.end();

        // Render Stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Deteksi Klik Tombol Back
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX >= backButtonX && touchX <= backButtonX + backButtonWidth &&
                touchY >= backButtonY && touchY <= backButtonY + backButtonHeight) {
                pacman.setScreen(new HomeScreen(pacman));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
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
        background.dispose();
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
        databaseManager.closeConnection();
    }
}
