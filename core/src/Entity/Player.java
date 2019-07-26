package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import Config.GameConfig;

public class Player
{
    private static final float BOUND_RADIUS = 0.4f;
    private static final float SIZE = 2 * BOUND_RADIUS;
    public static float X_MAX_SPEED = 0.1f;
    private float x = GameConfig.WORLD_WIDTH / 2f;
    private float y = 1.0f;
    private Circle bounds;

    public Player()
    {
        bounds = new Circle(x, y, BOUND_RADIUS);
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        updatebounds();
    }

    public void update()
    {
        float xspeed = 0.0f;

        //System.out.println(world);
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.getX() >= Gdx.graphics.getWidth() / 2f) && (x <= GameConfig.WORLD_WIDTH - 0.45f))
        {
            xspeed = X_MAX_SPEED;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.getX() <= Gdx.graphics.getWidth() / 2f) && (x >= 0.45f))
        {
            xspeed = -X_MAX_SPEED;
        }
        x = x + xspeed;
        updatebounds();
    }

    private void updatebounds()
    {
        bounds.setPosition(this.x, this.y);
    }

    public void drawDebug(ShapeRenderer renderer)
    {
        renderer.setColor(Color.WHITE);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.circle(x, y, BOUND_RADIUS, 100);
        renderer.end();
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}
