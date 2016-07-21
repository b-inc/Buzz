package com.binc.buzz.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.binc.buzz.R;

import io.codetail.widget.RevealFrameLayout;

/**
 * Created by yashbuch on 16/07/16.
 */
public class StackView extends RevealFrameLayout {
    Context mContext;
    public StackView(Context context, String text) {
        super(context);
        mContext = context;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        prepareStack(text);
    }

    public StackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void prepareStack(String info){
        CardView cv1 = new CardView(mContext);
        cv1.setBackgroundColor(getResources().getColor(R.color.common_yellow));
        cv1.setCardElevation(5);
        //cv1.setShadowPadding(2, 0, 2, 2);
        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, 324);
        params1.setMargins(15, 5, 15, 5);

        CardView cv2 = new CardView(mContext);
        cv2.setBackgroundColor(getResources().getColor(R.color.common_yellow));
        cv2.setCardElevation(10);
        //cv2.setShadowPadding(2, 0, 2, 0);
        LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, 312);
        params2.setMargins(15, 5, 15, 5);

        CardView cv3 = new CardView(mContext);
        cv3.setBackgroundColor(getResources().getColor(R.color.common_yellow));
        cv3.setCardElevation(15);
        //cv3.ssetShadowPadding(2, 0, 2, 0);
        TextView tv = new TextView(mContext);
        tv.setText(info);
        cv3.addView(tv);
        LayoutParams params3 = new LayoutParams(LayoutParams.MATCH_PARENT, 300);
        params3.setMargins(15, 5, 15, 5);

        addView(cv1, params1);
        addView(cv2, params2);
        addView(cv3, params3);
    }
}
