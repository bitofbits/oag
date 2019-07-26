package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

import Config.GameConfig;

public class Obstacle
{
    public static final float BOUND_RADIUS = 0.3f;
    private static final float SIZE = 2 * BOUND_RADIUS;
    public static float yspeed = 0.1f;
    private float x = MathUtils.random(0.31f,GameConfig.WORLD_WIDTH-0.31f);
    private float y = GameConfig.WORLD_HEIGHT;
    private Circle bounds;
    public Obstacle()
    {
        bounds = new Circle(x,y,BOUND_RADIUS);
    }
    public void setPosition(float x,float y)
    {
        this.x = x;
        this.y = y;
        updatebounds();
    }
    public void update()
    {
        setPosition(x,y-yspeed);
    }
    private void updatebounds()
    {
        bounds.setPosition(this.x,this.y);
    }
    public void drawDebug(ShapeRenderer renderer)
    {
        renderer.setColor(Color.WHITE);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.circle(x,y,BOUND_RADIUS,100);
        renderer.end();
    }

    public float getY()
    {
        return y;
    }

    public float getX()
    {
        return x;
    }
}
