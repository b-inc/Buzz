package com.binc.buzz.ui;

import android.content.Context;
import android.graphics.Bitmap;

import com.binc.buzz.network.BuzzServer;
import com.binc.buzz.util.BuzzConstants;
import com.binc.buzz.util.BuzzTypeEnum;

/**
 * Created by yashbuch on 25/06/16.
 */
public class BuzzType {
    private static Context mContext;

    static class BuzzBuilder {
        int mime_type;
        private String text_data;
        String image_url;

        public String getImageUrl() {
            return image_url;
        }

        public BuzzBuilder ImageUrl(String img_url) {
            this.image_url = img_url;
            return this;
        }

        public BuzzBuilder(Context ctx, int mime){
            mContext = ctx;
            this.mime_type = mime;
        }

        public BuzzBuilder TextData(String text){
            this.text_data = text;
            return this;
        }

        public String getStringData(){
            return this.text_data;
        }

        public BuzzView build(){
            BuzzView buzz = null;
            switch(mime_type){
                case BuzzConstants.BuzzMimeTypes.MIME_TEXT:
                    buzz = TextBuzzBuilder.build(this);
                    break;
                case BuzzConstants.BuzzMimeTypes.MIME_IMG:
                    buzz = ImageBuzzBuilder.build(this);
            }
            return buzz;
        }
    }

    static class TextBuzzBuilder {
        static BuzzView build(BuzzBuilder builder){
            TextBuzzView tb_view = new TextBuzzView(mContext);
            tb_view.setTextData(builder.getStringData());
            return tb_view;
        }
    }

    static class ImageBuzzBuilder {
        static BuzzView build(BuzzBuilder builder){
            ImageBuzzView imb_view = new ImageBuzzView(mContext);
            BuzzServer.getBuzzFetcherInstance(mContext).getImage(builder.getImageUrl(), imb_view);
            return imb_view;
        }
    }
}
