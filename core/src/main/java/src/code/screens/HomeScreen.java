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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import src.code.Pacman;

public class HomeScreen implements Screen {

    private final Pacman game;
    private OrthographicCamera camera; // gambarnya 2d
    private SpriteBatch batch; // munculin sprite
    private ShapeRenderer shapeRenderer; // buat kotak
    private BitmapFont font;
    private Stage stage; // inisialisasi UI
    private Texture background; // background

    public HomeScreen(Pacman game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // Ukuran layar
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("fonts/army_stencil.fnt"));
        background = new Texture(Gdx.files.internal("images/pixelated.png"));
        stage = new Stage();
    }

    @Override
    public void show() {

    }

    private boolean isButtonHovered(float x, float y, float width, float height) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Warna latar belakang
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Menampilkan background
        batch.begin();
        batch.draw(background, 0, 0, 800, 650);
        batch.end();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Outline tombol
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (isButtonHovered(300, 350, 200, 50)) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        shapeRenderer.arc(314, 364, 15, 180, 90); // Kiri bawah
        shapeRenderer.arc(486, 364, 15, 270, 90); // Kanan bawah
        shapeRenderer.arc(314, 386, 15, 90, 90); // Kiri atas
        shapeRenderer.arc(486, 386, 15, 0, 90); // Kanan atas
        shapeRenderer.line(315, 349, 485, 350); // Garis bawah
        shapeRenderer.line(315, 400, 485, 400); // Garis atas
        shapeRenderer.line(300, 365, 300, 385); // Garis kiri
        shapeRenderer.line(501, 365, 500, 385); // Garis kanan

        if (isButtonHovered(300, 250, 200, 50)) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        shapeRenderer.arc(314, 264, 15, 180, 90); // Kiri bawah
        shapeRenderer.arc(486, 264, 15, 270, 90); // Kanan bawah
        shapeRenderer.arc(314, 286, 15, 90, 90); // Kiri atas
        shapeRenderer.arc(486, 286, 15, 0, 90); // Kanan atas
        shapeRenderer.line(315, 249, 485, 250); // Garis bawah
        shapeRenderer.line(315, 300, 485, 300); // Garis atas
        shapeRenderer.line(300, 265, 300, 285); // Garis kiri
        shapeRenderer.line(501, 265, 500, 285); // Garis kanan

        if (isButtonHovered(300, 150, 200, 50)) {
            shapeRenderer.setColor(Color.YELLOW);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        shapeRenderer.arc(314, 164, 15, 180, 90); // Kiri bawah
        shapeRenderer.arc(486, 164, 15, 270, 90); // Kanan bawah
        shapeRenderer.arc(314, 186, 15, 90, 90); // Kiri atas
        shapeRenderer.arc(486, 186, 15, 0, 90); // Kanan atas
        shapeRenderer.line(315, 149, 485, 150); // Garis bawah
        shapeRenderer.line(315, 200, 485, 200); // Garis atas
        shapeRenderer.line(300, 165, 300, 185); // Garis kiri
        shapeRenderer.line(501, 165, 500, 185); // Garis kanan
        shapeRenderer.end();

        // Tombol Play
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (isButtonHovered(300, 350, 200, 50)) {
            shapeRenderer.setColor(Color.NAVY);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(315, 350, 170, 50); // Bagian tengah
        shapeRenderer.rect(300, 365, 15, 20); // Kiri
        shapeRenderer.rect(485, 365, 15, 20); // Kanan
        shapeRenderer.circle(315, 365, 15); // Kiri bawah
        shapeRenderer.circle(485, 365, 15); // Kanan bawah
        shapeRenderer.circle(315, 385, 15); // Kiri atas
        shapeRenderer.circle(485, 385, 15); // Kanan atas

        // Tombol Highscore
        if (isButtonHovered(300, 250, 200, 50)) {
            shapeRenderer.setColor(Color.NAVY);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(315, 250, 170, 50); // Bagian tengah
        shapeRenderer.rect(300, 265, 15, 20); // Kiri
        shapeRenderer.rect(485, 265, 15, 20); // Kanan
        shapeRenderer.circle(315, 265, 15); // Kiri bawah
        shapeRenderer.circle(485, 265, 15); // Kanan bawah
        shapeRenderer.circle(315, 285, 15); // Kiri atas
        shapeRenderer.circle(485, 285, 15); // Kanan atas

        // Tombol Exit
        if (isButtonHovered(300, 150, 200, 50)) {
            shapeRenderer.setColor(Color.NAVY);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.rect(315, 150, 170, 50); // Bagian tengah
        shapeRenderer.rect(300, 165, 15, 20); // Kiri
        shapeRenderer.rect(485, 165, 15, 20); // Kanan
        shapeRenderer.circle(315, 165, 15); // Kiri bawah
        shapeRenderer.circle(485, 165, 15); // Kanan bawah
        shapeRenderer.circle(315, 185, 15); // Kiri atas
        shapeRenderer.circle(485, 185, 15); // Kanan atas
        shapeRenderer.end();

        // Menampilkan teks di atas tombol
        batch.begin(); // spritebatch
        font.getData().setScale(4.0f);
        font.setColor(Color.YELLOW);
        font.draw(batch, "PAC", 275, 500);
        font.setColor(Color.WHITE);
        font.draw(batch, "MAN", 390, 500);
        font.getData().setScale(1.0f); //bitmap font

        if (isButtonHovered(300, 350, 200, 50)) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, "Play", 380, 382);

        if (isButtonHovered(300, 250, 200, 50)) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, "Highscore", 355, 282);

        if (isButtonHovered(300, 150, 200, 50)) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, "Exit", 382, 182);
        batch.end();

        // Deteksi klik
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Periksa area tombol
            if (touchPos.x >= 300 && touchPos.x <= 500) {
                if (touchPos.y >= 350 && touchPos.y <= 400) {
                    game.setScreen(new PlayScreen(game));
                    dispose();
                } else if (touchPos.y >= 250 && touchPos.y <= 300) {
                    game.setScreen(new LeaderboardScreen(game));
                } else if (touchPos.y >= 150 && touchPos.y <= 200) {
                    Gdx.app.exit();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
