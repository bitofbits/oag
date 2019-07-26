package Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.obstacleavoid.game.ObstacleAvoidGame;

public class GameManager
{
    public static final GameManager INSTANCE = new GameManager();
    private int highscore;
    private Preferences preferences_high_score;

    private GameManager()
    {
        preferences_high_score = Gdx.app.getPreferences(ObstacleAvoidGame.class.getName());
        highscore = preferences_high_score.getInteger("High_Score", 0);
    }

    public void updatehighscore(int score)
    {
        if (highscore > score)
        {
            return;
        }
        highscore = score;
        preferences_high_score.putInteger("High_Score", highscore);
        preferences_high_score.flush();
    }

    public String stringhighscore()
    {
        return String.valueOf(highscore);
    }
}
