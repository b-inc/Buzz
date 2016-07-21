package com.binc.buzz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.binc.buzz.R;

/**
 * Created by yashbuch on 05/05/16.
 */
public class TextBuzzView extends BuzzView {

    Context mContext = null;
    View view = null;

    //constructor
    public TextBuzzView(Context context){
        super(context);
        mContext = context;
        //init views
        init();
    }

    public TextBuzzView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        mContext = context;
        //init views
    }

    public TextBuzzView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        //init views
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.text_buzz_layout, this, true);
    }

    public void setTextData(String text_data){
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(text_data);
    }


}
