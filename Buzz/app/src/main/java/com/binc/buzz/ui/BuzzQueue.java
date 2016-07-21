package com.binc.buzz.ui;

import android.content.Context;
import android.util.Log;

import com.binc.buzz.bean.BuzzInfo;
import com.binc.buzz.network.BuzzServer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yashbuch on 07/07/16.
 */
public class BuzzQueue {
    private final String TAG = BuzzQueue.class.getSimpleName();
    private static Queue<BuzzInfo> queue;
    private static BuzzQueue buzzQueue;
    private Context iContext;
    private BuzzQueue(Context ctx) {
        queue = new LinkedList<>();
        iContext = ctx;
    }

    public static BuzzQueue getBuzzQueueInstance(Context ctx) {
        synchronized (BuzzQueue.class) {
            if (buzzQueue == null) {
                buzzQueue = new BuzzQueue(ctx);
            }
        }
        return buzzQueue;
    }

    private void notifyQueueEmpty() {
        BuzzServer.getBuzzFetcherInstance(iContext).getBuzz();
    }

    public BuzzInfo get() {
        BuzzInfo bi = null;
        Log.i(TAG, "queue size before get:" + queue.size());
        if (getSize() < 5)
            notifyQueueEmpty();

        bi = queue.poll();

        return bi;
    }

    public void put(BuzzInfo bi) {
        Log.i(TAG, "bi put");
        queue.add(bi);
    }

    public int getSize() {
        return queue.size();
    }
}
