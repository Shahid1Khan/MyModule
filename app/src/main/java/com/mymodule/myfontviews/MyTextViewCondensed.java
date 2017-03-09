package com.mymodule.myfontviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wscubetech on 2/6/16.
 */
public class MyTextViewCondensed extends TextView {
    public MyTextViewCondensed(Context context) {
        super(context);
        init();
    }

    public MyTextViewCondensed(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewCondensed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Light.ttf");
        setTypeface(tf, 1);
    }
}
