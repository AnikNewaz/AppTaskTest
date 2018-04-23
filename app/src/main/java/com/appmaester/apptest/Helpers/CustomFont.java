package com.appmaester.apptest.Helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Nik on 4/23/2018.
 */

public class CustomFont extends AppCompatTextView {

    private static Typeface sMaterialDesignIcons;

    public CustomFont(Context context) {
        this(context, null);
    }

    public CustomFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;//Won't work in Eclipse graphical layout
        setTypeface();
    }

    private void setTypeface() {
        if (sMaterialDesignIcons == null) {
            sMaterialDesignIcons = Typeface.createFromAsset(getContext().getAssets(), "fonts/GillSansUltraBold.ttf");
        }
        setTypeface(sMaterialDesignIcons);
    }
}

