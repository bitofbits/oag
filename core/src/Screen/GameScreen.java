package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.ObstacleAvoidGame;

import Common.GameManager;
import Config.GameConfig;
import Entity.Obstacle;
import Entity.Player;
import Utils.GridUtils;

public class GameScreen implements Screen
{
    private final ObstacleAvoidGame obstacleAvoidGame;
    private final AssetManager manager;
    private TextureAtlas atlas;
    private OrthographicCamera camera;
    private OrthographicCamera camera_hud;
    private Viewport viewport_hud;
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private int lives = GameConfig.LIVES_START;
    private int scores = GameConfig.INITIAL_SCORE;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private float obstacletimer = 0.0f;
    private float scoretimer = 0.0f;
    private boolean isalive = true;
    private TextureRegion background;
    private TextureRegion face;
    private TextureRegion obs;

    public GameScreen(ObstacleAvoidGame ObstacleAvoidGame)
    {
        this.obstacleAvoidGame = ObstacleAvoidGame;
        this.manager = obstacleAvoidGame.getManager();
    }

    @Override
    public void show()
    {
        camera = new OrthographicCamera();
        camera_hud = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        viewport_hud = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera_hud);
        batch = new SpriteBatch();
        //Gdx.app.log("GameScreen ", String.valueOf(manager));
        //manager.load(GameConfig.GAME_PLAY, TextureAtlas.class);
        //manager.load(GameConfig.FONT_STRING,BitmapFont.class);
        //manager.load(GameConfig.BACKGROUND_STRING,Texture.class);
        //manager.load(GameConfig.FACE_STRING,Texture.class);
        //manager.load(GameConfig.OBSTACLE_STRING,Texture.class);
        //manager.finishLoading();
        font = manager.get(GameConfig.FONT_STRING);
        atlas = manager.get(GameConfig.GAME_PLAY);
        background = atlas.findRegion("background");
        //background = manager.get(GameConfig.BACKGROUND_STRING);
        face = atlas.findRegion("player");
        //face = manager.get(GameConfig.FACE_STRING);
        obs = atlas.findRegion("obstacle");
        //obs=manager.get(GameConfig.OBSTACLE_STRING);
        //font = new BitmapFont(Gdx.files.internal("C:\\Users\\Astitva\\Downloads\\LibGdx Projects\\Obstacle Avoid Game\\android\\assets\\ui\\fonts\\ui_26_font.fnt"));
        //background = new Texture("C:\\Users\\Astitva\\Downloads\\LibGdx Projects\\Obstacle Avoid Game\\android\\assets\\gameplay\\background.png");
        //face = new Texture("C:\\Users\\Astitva\\Downloads\\LibGdx Projects\\Obstacle Avoid Game\\android\\assets\\gameplay\\player.png");
        //obs = new Texture("C:\\Users\\Astitva\\Downloads\\LibGdx Projects\\Obstacle Avoid Game\\android\\assets\\gameplay\\obstacle.png");
        renderer = new ShapeRenderer();
        player = new Player();
    }

    @Override
    public void render(float delta)
    {

        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //player.setPosition(GameConfig.WORLD_WIDTH/2f,1);
        rendorbackground();
        grid();
        if (isalive)
        {
            renderui(delta);
            createnewobstacle(delta);
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            {
                renderbegin();
            }
            //renderbegin();
            obstacletexture();
            facetexture();
        }

        if (collisionDetect())
        {
            isalive = false;

            if(lives<=1)
            {
                obstacles.clear();
                --lives;
                //obstacleAvoidGame.setScreen(new GameOverScreen(obstacleAvoidGame));
            }
        }
        if (((Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isTouched()) && isalive == false) || lives<=0)
        {
            --lives;
            if (lives <= 0)
            {
                isalive = false;
                obstacles.clear();
                obstacleAvoidGame.setScreen(new GameOverScreen(obstacleAvoidGame));
                //gameoverscreen
            }
            else
                {
                isalive = true;
                obstacles.clear();
                }

            player.setPosition(GameConfig.WORLD_WIDTH / 2f, 1f);
        }
    }

    public void obstacletexture()
    {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 0; i < obstacles.size; i++)
        {
            batch.draw(obs, obstacles.get(i).getX() - Obstacle.BOUND_RADIUS, obstacles.get(i).getY() - Obstacle.BOUND_RADIUS, 2 * Obstacle.BOUND_RADIUS, 2 * Obstacle.BOUND_RADIUS);
        }
        batch.end();
    }

    public void facetexture()
    {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(face, player.getX() - 0.4f, player.getY() - 0.4f, 2 * 0.4f, 2 * 0.4f);
        batch.end();
    }

    public void rendorbackground()
    {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0.0f, 0.0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        batch.end();
    }

    public void renderui(float delta)
    {
        viewport_hud.apply();
        scoretimer += delta;
        batch.setProjectionMatrix(camera_hud.combined);
        if (scoretimer >= GameConfig.SCORE_INTERVAL && isalive == true)
        {
            scoretimer = 0f;
            ++scores;
            GameManager.INSTANCE.updatehighscore(scores);

        }
        String live = "LIVES: " + Integer.toString(lives);
        String score = "SCORE: " + Integer.toString(scores);
        layout.setText(font, live);

        batch.begin();
        font.draw(batch, live, GameConfig.HUD_WIDTH - (layout.width + 20), GameConfig.HUD_HEIGHT - 20);
        font.draw(batch, score, 20, GameConfig.HUD_HEIGHT - 20);
        batch.end();
    }

    public void renderbegin()
    {
        player.update();
        player.drawDebug(renderer);

    }

    public void createnewobstacle(float delta)
    {
        obstacletimer += delta;
        if (obstacletimer >= GameConfig.OBSTACLE_SPAWN_TIME)
        {
            Obstacle obstacle = new Obstacle();
            obstacles.add(obstacle);
            obstacletimer = 0.0f;
        }
        drawobstacles();
    }

    public void drawobstacles()
    {
        for (int i = 0; i < obstacles.size; i++)
        {
            obstacles.get(i).update();
            if (obstacles.get(i).getY() <= -0.5f)
            {
                obstacles.removeIndex(i);
            }
            obstacles.get(i).drawDebug(renderer);
        }
    }

    public boolean collisionDetect()
    {
        double distance = 0;
        for (int i = 0; i < obstacles.size; i++)
        {
            distance = Math.sqrt(Math.pow((player.getX() - obstacles.get(i).getX()), 2) + Math.pow(player.getY() - obstacles.get(i).getY(), 2));
            if (/*player.getX() <= obstacles.get(i).getX() + 0.71f && player.getX() >= obstacles.get(i).getX() - 0.71f && obstacles.get(i).getY() <= 1.71f*/distance <= 0.70f)
            {
                return true;
            } else
            {
                return false;
            }
        }
        return false;
    }

    public void grid()
    {
        GridUtils.DisplayGrid(viewport, renderer, 1);
    }


    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
        viewport_hud.update(width, height, true);
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
        batch.dispose();
        renderer.dispose();
    }
}
