package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.ObstacleAvoidGame;

import Config.GameConfig;

public class MenuScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    private ObstacleAvoidGame game;
    private AssetManager manager;


    public MenuScreen(ObstacleAvoidGame game)
    {
        this.game = game;
        manager=this.game.getManager();
    }

    @Override
    public void show()
    {
        viewport = new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        initUI();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.act();
        stage.draw();
        /*initUI();*/


    }
    private void initUI()
    {
        TextureAtlas gameatlas = manager.get(GameConfig.GAME_PLAY);
        TextureAtlas ui_atlas = manager.get(GameConfig.UI);
        TextureRegion background = gameatlas.findRegion("background");
        TextureRegion panelback = ui_atlas.findRegion("panel");

        Table table = new Table();
        Table buttontable = new Table();


        table.setBackground(new TextureRegionDrawable(background));
        buttontable.setBackground(new TextureRegionDrawable(panelback));

        ImageButton playbutton = new ImageButton(new TextureRegionDrawable(ui_atlas.findRegion("play")),new TextureRegionDrawable(ui_atlas.findRegion("playPressed")));
        ImageButton options = new ImageButton(new TextureRegionDrawable(ui_atlas.findRegion("options")),new TextureRegionDrawable(ui_atlas.findRegion("optionsPressed")));
        ImageButton highscore = new ImageButton(new TextureRegionDrawable(ui_atlas.findRegion("highscore")),new TextureRegionDrawable(ui_atlas.findRegion("highscorePressed")));

        buttontable.defaults().pad(20f);
        buttontable.add(playbutton).row();
        buttontable.add(highscore).row();
        buttontable.add(options).row();
        buttontable.center();

        table.add(buttontable);

        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);


        //Listeners
        playbutton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                playgame();
            }
        });

        highscore.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                highscorescreen();
            }
        });
        options.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                optionscreen();
            }
        });
    }

    private void optionscreen()
    {
        game.setScreen(new OptionsScreen(game));
    }

    private void highscorescreen()
    {
        game.setScreen(new HighScoreScreen(game));
    }

    private void playgame()
    {
        game.setScreen(new GameScreen(game));
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
