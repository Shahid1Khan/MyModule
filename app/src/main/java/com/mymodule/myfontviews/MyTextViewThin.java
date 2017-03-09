package com.mymodule.myfontviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wscubetech on 2/6/16.
 */
public class MyTextViewThin extends TextView {
    public MyTextViewThin(Context context) {
        super(context);
        init();
    }

    public MyTextViewThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewThin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = CustomFont.setFontRegularThin(getContext().getAssets());
        setTypeface(tf, 1);
    }
}
