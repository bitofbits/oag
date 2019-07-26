package Config;

public class GameConfig
{
    public static final float WIDTH = 360f;// pixels
    public static final float HEIGHT = 600f;//pixels

    public static final float HUD_WIDTH = 360f;//world units here 1 pixel is 1 world unit
    public static final float HUD_HEIGHT = 600f;//world units here 1 pixel is 1 world unit

    public static final float WORLD_WIDTH = 6f;//world units
    public static final float WORLD_HEIGHT = 10f;//world units

    public static final float WORLD_CENTERX = WORLD_WIDTH / 2f;//world units
    public static final float WORLD_CENTERY = WORLD_HEIGHT / 2f;//world units

    public static final float OBSTACLE_SPAWN_TIME = 0.25f;
    public static final int LIVES_START = 3;
    public static final int INITIAL_SCORE = 0;
    public static final float SCORE_INTERVAL = 0.5f;

    public static final String FONT_STRING ="ui/fonts/ui_26_font.fnt";
    public static final String BACKGROUND_STRING = "gameplay/background.png";
    public static final String FACE_STRING = "gameplay/player.png";
    public static final String OBSTACLE_STRING = "gameplay/obstacle.png";
    public static final String GAME_PLAY = "gameplay/gameplay.atlas";
    public static final String UI = "ui/ui.atlas";
    private GameConfig()
    {

    }
}
