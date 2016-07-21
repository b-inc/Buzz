package com.binc.buzz.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.binc.buzz.bean.BuzzInfo;
import com.binc.buzz.ui.BuzzQueue;
import com.binc.buzz.ui.ImageBuzzView;
import com.binc.buzz.util.BuzzConstants;

/**
 * Created by yashbuch on 10/07/16.
 */
public class BuzzServer {
    private static BuzzServer buzzServer;
    private Context iContext;
    private RequestQueue requestQueue;
    private ProgressDialog buzzDialog;
    private ProgressDialog imgDialog;

    private BuzzServer(Context ctx) {
        iContext = ctx;
        buzzDialog = new ProgressDialog(iContext);
        imgDialog = new ProgressDialog(iContext);
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    public static BuzzServer getBuzzFetcherInstance(Context ctx) {
        synchronized (BuzzServer.class) {
            if (buzzServer == null) {
                buzzServer = new BuzzServer(ctx);
            }
        }
        return buzzServer;
    }

    public void getBuzz() {
        Log.i("yash", "fetching..");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(iContext.getApplicationContext());
        }

        StringRequest request = new StringRequest(Request.Method.GET, "http://10.0.0.3/first_php.php/api/fetch/buzz",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("yash", "response:" + response);
                        BuzzInfo bi = new BuzzInfo();
                        if (Math.random() < 0.5) {
                            bi.setMime(BuzzConstants.BuzzMimeTypes.MIME_TEXT);
                            bi.setTextData(response);
                        } else {
                            bi.setMime(BuzzConstants.BuzzMimeTypes.MIME_IMG);
                            bi.setImageUrl("http://10.0.0.3/first_php.php/api/fetch/image");
                        }
                        BuzzQueue.getBuzzQueueInstance(iContext)
                                .put(bi);
                        dismissDialog(buzzDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yash", "error:" + error);
                        dismissDialog(buzzDialog);
                    }
                });

        showDialog(buzzDialog);
        requestQueue.add(request);
    }

    public void getImage(String url, final ImageBuzzView im) {
        Log.i("yash", "fetching image..");

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(iContext.getApplicationContext());
        }

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.i("yash", "image response");
                        im.setImage(response);
                        dismissDialog(imgDialog);
                    }
                }, 100, 100, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yash", "image error");
                        dismissDialog(imgDialog);
                    }
                });

        showDialog(imgDialog);
        requestQueue.add(imageRequest);
    }

    private void showDialog(ProgressDialog dialog) {
        dialog.setCancelable(false);
        dialog.setMessage("fetching...");
        dialog.show();
    }

    private void dismissDialog(ProgressDialog dialog) {
        dialog.dismiss();
    }
}
