package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.ObstacleAvoidGame;

import Config.GameConfig;

public class GameOverScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    private ObstacleAvoidGame game;
    private AssetManager manager;

    public GameOverScreen(ObstacleAvoidGame game)
    {
        this.game = game;
        manager = this.game.getManager();
    }

    @Override
    public void show()
    {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        gameoverUI();

    }

    private void gameoverUI()
    {
        TextureAtlas gameplay = manager.get(GameConfig.GAME_PLAY);
        TextureAtlas ui = manager.get(GameConfig.UI);

        Image backg = new Image(new TextureRegionDrawable(gameplay.findRegion("background")));

        backg.setBounds(0,0,GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
        BitmapFont font = manager.get(GameConfig.FONT_STRING);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        Label gameover = new Label("GAME OVER!", style);

        ImageButton back = new ImageButton(new TextureRegionDrawable(ui.findRegion("back")), new TextureRegionDrawable(ui.findRegion("backPressed")));

        gameover.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f + 45, Align.center);
        back.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f - 45, Align.center);

        back.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                game.setScreen(new MenuScreen(game));
            }
        });
        stage.addActor(backg);
        stage.addActor(gameover);
        stage.addActor(back);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height,true);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
