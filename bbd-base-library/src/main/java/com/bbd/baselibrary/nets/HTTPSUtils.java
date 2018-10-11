package com.bbd.baselibrary.nets;

import android.content.Context;
import android.util.Log;

import com.bbd.baselibrary.R;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Author:Ariana Wong
 * Date: 2017-12-18
 * Desctiption:
 */

public class HTTPSUtils {

    public static SSLSocketFactory getSSLSocketFactory1(Context context) {
        final String CLIENT_TRUST_PASSWORD = "Bbdsecret1";//信任证书密码
        final String CLIENT_AGREEMENT = "TLS";//使用协议
        final String CLIENT_TRUST_KEYSTORE = "BKS";
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream is = context.getResources().openRawResource(R.raw.bbd_z);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } finally {
                is.close();
            }
            trustManager.init(tks);
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SslContextFactory", e.getMessage());
        }
        return sslContext.getSocketFactory();
    }
}
