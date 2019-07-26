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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.ObstacleAvoidGame;

import Common.GameManager;
import Config.GameConfig;

public class HighScoreScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    private ObstacleAvoidGame game;
    private AssetManager manager;

    public HighScoreScreen(ObstacleAvoidGame game)
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
        highscoreUI();

    }

    private void highscoreUI()
    {
        TextureAtlas gameatlas = manager.get(GameConfig.GAME_PLAY);
        TextureAtlas ui_atlas = manager.get(GameConfig.UI);
        TextureRegion background = gameatlas.findRegion("background");
        TextureRegion panelback = ui_atlas.findRegion("panel");
        BitmapFont font = manager.get(GameConfig.FONT_STRING);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);


        Table table = new Table();
        Table buttontable = new Table();


        table.setBackground(new TextureRegionDrawable(background));
        buttontable.setBackground(new TextureRegionDrawable(panelback));

        String highscore = GameManager.INSTANCE.stringhighscore();
        Label high1 = new Label("HIGHSCORE:", style);
        Label high2 = new Label(highscore, style);
        ImageButton backbutton = new ImageButton(new TextureRegionDrawable(ui_atlas.findRegion("back")), new TextureRegionDrawable(ui_atlas.findRegion("backPressed")));

        buttontable.defaults().pad(20f);
        buttontable.add(high1).row();
        buttontable.add(high2).row();
        buttontable.add(backbutton);
        buttontable.center();


        table.add(buttontable);
        table.center();
        table.setFillParent(true);
        table.pack();
        stage.addActor(table);


        //listeners
        backbutton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                goback();
            }
        });
    }

    private void goback()
    {
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
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
