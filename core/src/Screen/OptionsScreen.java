package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import Entity.Obstacle;
import Entity.Player;

public class OptionsScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    private ObstacleAvoidGame game;
    private AssetManager manager;
    private Image checkmark;

    public OptionsScreen(ObstacleAvoidGame game)
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
        optionsUI();
    }

    private void optionsUI()
    {

        TextureAtlas gameatlas = manager.get(GameConfig.GAME_PLAY);
        TextureAtlas uiatlas = manager.get(GameConfig.UI);

        TextureRegion background = gameatlas.findRegion("background");
        Image backg = new Image(new TextureRegionDrawable(background));

        //TextureRegion panelback = uiatlas.findRegion("panel");

        TextureRegion check = uiatlas.findRegion("check-mark");
        checkmark = new Image(new TextureRegionDrawable(check));

        BitmapFont font = manager.get(GameConfig.FONT_STRING);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        final ImageButton easy = new ImageButton(new TextureRegionDrawable(uiatlas.findRegion("easy")));
        easy.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f + 90f, Align.center);

        checkmark.setPosition(easy.getX()+30,easy.getY()+40,Align.left);

        easy.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                //checkmark.setRegionX(easy.getX() + 25);
                checkmark.setPosition(easy.getX()+30,easy.getY()+40,Align.left);
                Obstacle.yspeed=0.1f;
                Player.X_MAX_SPEED = 0.10f;
            }
        });

        final ImageButton medium = new ImageButton(new TextureRegionDrawable(uiatlas.findRegion("medium")));
        medium.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f, Align.center);

        medium.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                checkmark.setPosition(medium.getX()+30,medium.getY()+40,Align.left);
                Obstacle.yspeed=0.15f;
                Player.X_MAX_SPEED = 0.12f;
            }
        });

        final ImageButton hard = new ImageButton(new TextureRegionDrawable(uiatlas.findRegion("hard")));
        hard.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f - 90f, Align.center);

        hard.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                checkmark.setPosition(hard.getX()+27,hard.getY()+40,Align.left);
                Obstacle.yspeed=0.2f;
                Player.X_MAX_SPEED = 0.13f;

            }
        });
        ImageButton backbutton = new ImageButton(new TextureRegionDrawable(uiatlas.findRegion("back")), new TextureRegionDrawable(uiatlas.findRegion("backPressed")));
        backbutton.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 2f - 180f, Align.center);

        backbutton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                back();
            }
        });
        stage.addActor(backg);
        stage.addActor(easy);
        stage.addActor(medium);
        stage.addActor(hard);
        stage.addActor(backbutton);
        stage.addActor(checkmark);
    }

    private void back()
    {
        game.setScreen(new MenuScreen(game));
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
