package com.obstacleavoid.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class texturepacker
{
    private static final String raw_assets_path="desktop/raw-assets";
    private static final String aseets_path = "android/assets";

    public static void main(String[] args)
    {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug=false;
        TexturePacker.process(settings,raw_assets_path+"/gameplay",aseets_path+"/gameplay","gameplay");
    }

}
