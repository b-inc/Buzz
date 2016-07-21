package com.binc.buzz.ui;

import android.content.Context;

import com.binc.buzz.bean.BuzzInfo;
import com.binc.buzz.network.BuzzServer;
import com.binc.buzz.util.BuzzConstants;
import com.binc.buzz.util.BuzzTypeEnum;

/**
 * Created by yashbuch on 25/06/16.
 */
/* creates buzz from the data feed obtained from queue*/
public class BuzzReConstructor {
    static Context mContext;
    public static BuzzView constructBuzz(BuzzInfo bi, Context ctx){
        mContext = ctx;
        BuzzView bv = null;
        BuzzType.BuzzBuilder builder = new BuzzType.BuzzBuilder(mContext, bi.getMime());
        switch(bi.getMime()) {
            case BuzzConstants.BuzzMimeTypes.MIME_TEXT: {
                bv = builder.TextData(bi.getTextData())
                        .build();
                break;
            }
            case BuzzConstants.BuzzMimeTypes.MIME_IMG: {
                bv =  builder.ImageUrl(bi.getImageUrl())
                        .build();
                break;
            }
        }
        return bv;
    }
}
