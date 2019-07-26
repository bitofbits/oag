package Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GridUtils
{
    private static int default_size = 1;
    public static void DisplayGrid(Viewport viewport, ShapeRenderer shapeRenderer, int cell_size)
    {
            if (viewport == null)
            {
                throw new IllegalArgumentException("Viewport parameter required");
            }
            if (shapeRenderer == null)
            {
                throw new IllegalArgumentException("ShapeRenderer parameter required");
            }
            if (cell_size < 0)
            {
                cell_size = default_size;
            }
            int worldwidth = (int) viewport.getWorldHeight();
            int worldheight = (int) viewport.getWorldHeight();
            int doubleworldwidth = 2 * worldheight;
            int doubleworldheight = 2 * worldheight;
            shapeRenderer.setColor(Color.WHITE);


            // draw verticle grid lines
            //.....
            for (int i = -doubleworldwidth; i < doubleworldwidth; i = i + cell_size)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                if (i == 0)
                {
                    shapeRenderer.setColor(Color.RED);
                }
                shapeRenderer.line(i, -doubleworldheight, i, doubleworldheight);
                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.end();
            }

            // draw horizontal grid lines
            //.....
            for (int i = -doubleworldheight; i < doubleworldheight; i = i + cell_size)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                if (i == 0)
                {
                    shapeRenderer.setColor(Color.RED);
                }
                shapeRenderer.line(-doubleworldwidth, i, doubleworldwidth, i);
                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.end();
            }
    }
}
