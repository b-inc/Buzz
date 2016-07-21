package com.binc.buzz.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.binc.buzz.R;

/**
 * Created by yashbuch on 12/07/16.
 */
public class ImageBuzzView extends BuzzView {
    Context iContext;
    View iView;

    public ImageBuzzView(Context context) {
        super(context);
        iContext = context;
        init();
    }

    public ImageBuzzView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageBuzzView(Context ctx, AttributeSet attrSet, int defStyleAttr) {
        super(ctx, attrSet, defStyleAttr);
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(iContext);
        iView = inflater.inflate(R.layout.image_buzz_layout, this, true);
    }

    public void setImage(Bitmap bitmap){
        ImageView networkImageView = (ImageView)iView.findViewById(R.id.networkImageView);
        networkImageView.setImageBitmap(bitmap);
    }

}
