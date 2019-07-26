package Screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.ObstacleAvoidGame;

import Config.GameConfig;

public class LoadingScreen extends ScreenAdapter
{
    public static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);
    public static final float PROGRESS_BAR_WIDTH = 3 * GameConfig.HUD_WIDTH / 4f;
    public static final float PROGRESS_BAR_HEIGHT = 30f;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private float progress = 0.0f;
    private float WaitTime = 1.0f;//time to wait after all the assets are loaded into assetmanager. (how much to wait after progress bar hits 100percent).
    private final ObstacleAvoidGame game;
    private final AssetManager manager;
    private BitmapFont font1;
    private GlyphLayout layout;

    public LoadingScreen(ObstacleAvoidGame obstacleAvoidGame)
    {
        this.game = obstacleAvoidGame;
        this.manager = game.getManager();
    }

    @Override
    public void show()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        batch = new SpriteBatch();
        layout = new GlyphLayout();
        font1 = new BitmapFont(Gdx.files.internal("ui/fonts/ui_26_font.fnt"));
        layout.setText(font1, "LOADING...");
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        manager.getLogger().setLevel(Logger.DEBUG);

        manager.load(GameConfig.GAME_PLAY, TextureAtlas.class);
        manager.load(GameConfig.FONT_STRING, BitmapFont.class);
        manager.load(GameConfig.UI,TextureAtlas.class);

    }


    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        draw();
        renderer.end();
        update(delta);

    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    @Override
    public void hide()
    {
        // screens dont dispose automatically
        dispose();
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        font1.dispose();
    }

    private void draw()
    {

        renderer.rect((GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f, (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f, progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        batch.begin();
        font1.draw(batch,"LOADING...",(GameConfig.HUD_WIDTH-layout.width)/2f,((GameConfig.HUD_HEIGHT/2f-PROGRESS_BAR_HEIGHT-15)));
        batch.end();
    }

    private void update(float delta)
    {
        //waitmillis(100L);// this is just to simulate waiting time when lots of assets are loaded. since in this game only 2 assets are being loaded, without this line the progress bar will be completed very quickly.
        progress = manager.getProgress();// between 0-1
        if (manager.update())//returns true when nothing to load
        {
            WaitTime -= delta;
            if (WaitTime <= 0)
            {
                //game.setManager(manager);
                game.setScreen(new MenuScreen(game));
                //game.setScreen(new GameScreen(game));
            }
        }

    }

    private static void waitmillis(long time)
    {
        try
        {
            Thread.sleep(time);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
