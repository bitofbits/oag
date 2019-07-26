package com.obstacleavoid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import Screen.LoadingScreen;

public class ObstacleAvoidGame extends Game
{
    private AssetManager manager;
    //ObstacleAvoidGame obstacleAvoidGame;
    @Override
    public void create()
    {
        //obstacleAvoidGame = new ObstacleAvoidGame();
        manager = new AssetManager();
        //setScreen(new GameScreen(this));
        this.setScreen(new LoadingScreen(this));
    }

    public void setManager(AssetManager manager)
    {
        this.manager = manager;
    }

    public AssetManager getManager()
    {
        return manager;
    }

    @Override
    public void dispose()
    {

        manager.dispose();
    }
}
