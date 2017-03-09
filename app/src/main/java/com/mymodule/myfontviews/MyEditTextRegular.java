package com.mymodule.myfontviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by wscubetech on 25/5/16.
 */
public class MyEditTextRegular extends EditText {
    public MyEditTextRegular(Context context) {
        super(context);
        init();
    }

    public MyEditTextRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditTextRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = CustomFont.setFontRegular(getContext().getAssets());
        setTypeface(tf, 1);
    }
}
