package com.binc.buzz.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.binc.buzz.R;
import com.binc.buzz.bean.BuzzInfo;
import com.binc.buzz.network.BuzzServer;

/**
 * Created by yashbuch on 08/05/16.
 */
public class BuzzFragment extends Fragment {
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_buzz, container, false);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        BuzzInfo bi = BuzzQueue.getBuzzQueueInstance(getActivity()).get();
        if(bi != null) {
            BuzzView bv = generateBuzzView(bi);
            ((RelativeLayout) mView).addView(bv);
        }
    }

    BuzzView generateBuzzView(BuzzInfo bi){
        return BuzzReConstructor.constructBuzz(bi,getActivity());
    }
}
