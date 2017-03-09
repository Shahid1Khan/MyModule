package com.mymodule.myfontviews;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by wscubetech on 3/6/16.
 */
public class CustomFont {
    public static final Typeface setFontBold(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/Font-SemiBold.otf");
    }

    public static final Typeface setFontRegularLight(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/Font-Light.otf");
    }

    public static final Typeface setFontRegular(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/Font-Regular.otf");
    }

    public static final Typeface setFontRegularThin(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/Font-Light.otf");
    }
}
