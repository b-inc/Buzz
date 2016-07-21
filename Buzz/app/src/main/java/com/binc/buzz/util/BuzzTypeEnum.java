package com.binc.buzz.util;

/**
 * Created by yashbuch on 05/05/16.
 */
public enum BuzzTypeEnum {
    TEXT_BUZZ(BuzzConstants.BuzzMimeTypes.MIME_TEXT);


    private int mime_type;
    BuzzTypeEnum(int value){
        mime_type = value;
    }

    public int getValue(){
        return mime_type;
    }
}
