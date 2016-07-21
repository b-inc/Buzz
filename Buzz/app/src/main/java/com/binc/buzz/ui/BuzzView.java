package com.binc.buzz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by yashbuch on 04/05/16.
 */
public class BuzzView extends RelativeLayout {

    //constructors
    public BuzzView(Context context) {
        this(context, null, 0);
    }

    public BuzzView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BuzzView(Context ctx, AttributeSet attrSet, int defStyleAttr) {
        super(ctx, attrSet, defStyleAttr);
    }
}
