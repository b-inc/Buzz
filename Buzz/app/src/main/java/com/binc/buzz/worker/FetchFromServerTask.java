package com.binc.buzz.worker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.binc.buzz.bean.BuzzInfo;
import com.binc.buzz.ui.BuzzQueue;
import com.binc.buzz.ui.BuzzReConstructor;
import com.binc.buzz.util.BuzzTypeEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yashbuch on 07/07/16.
 */
public class FetchFromServerTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;

    public FetchFromServerTask(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //ideally supposed to getBuzz json from server and let the re-constructor do the construction
        // without the need of BuzzInfo
        Log.i("yash", "fetching:");

        String str = fetchFromServer();
        BuzzInfo bi = new BuzzInfo();
        bi.setMime(BuzzTypeEnum.TEXT_BUZZ.getValue());
        bi.setTextData(str);
/*        try {
            BuzzQueue.getBuzzQueueInstance(mContext)
                    .put(BuzzReConstructor.constructBuzz(bi, mContext));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private String fetchFromServer() {
        String parsedString = "";
        try {
            URL url = new URL("http://10.0.0.4/first_php.php/api/getBuzz");
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            parsedString = convertInputStreamToString(is);
        } catch (Exception e) {
            Log.d("error", "" + e);
        }
        Log.i("yash", "async response:"+parsedString);
        return parsedString;
    }

    private static String convertInputStreamToString(InputStream iStream)
            throws IOException {
        StringBuilder sb = null;
        if (iStream != null) {
            sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        iStream, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                iStream.close();
            }
            return sb.toString();
        }
        return null;
    }
}
