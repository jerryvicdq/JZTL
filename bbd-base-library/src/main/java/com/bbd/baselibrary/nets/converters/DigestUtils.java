package com.bbd.baselibrary.nets.converters;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class DigestUtils {
    public final static String ENCODING = "UTF-8";

    public final static String DIGEST_KEY = "AVuu7SWwv99YCbb11IHlk5ONlq77YXba3HLlp57RRvu99Bbe13HHkk55RQuu99i3";
    public final static String E_KEY = "9HkocpYLeG1LNi5m";

    public static String decodeBase64(String data) {
        byte[] _date = data.getBytes();
        try {
            return new String(Base64.decodeBase64(_date), ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static String decodeBase64(String data, String encoding) {
        byte[] _date = data.getBytes();
        try {
            return new String(Base64.decodeBase64(_date), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static String encodeBase64(String data) {
        byte[] _date = data.getBytes();
        try {
            return new String(Base64.encodeBase64(_date), ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static String encodeBase64(byte[] date) {
        try {
            return new String(Base64.encodeBase64(date), ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static byte[] decodeBase64(byte[] date) {
        return Base64.decodeBase64(date);
    }


    public static String encodeBase64(String data, String encoding) {
        byte[] _date = data.getBytes();
        try {
            return new String(Base64.encodeBase64(_date), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static String encodeBase64(String data, String byteEncoding, String encoding) {
        try {
            byte[] _date = data.getBytes(byteEncoding);
            return new String(Base64.encodeBase64(_date), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static String md5(String data) {
//        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
        return new String(Hex.encodeHex(org.apache.commons.codec.digest.DigestUtils.md5(data)));
    }


    public static String sha1(String data) {
        return org.apache.commons.codec.digest.DigestUtils.sha1Hex(data);
    }


    public static String urlEncode(String data, String encoding) {
        try {
            return URLEncoder.encode(data, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static String urlDecode(String data, String encoding) {
        try {
            return URLDecoder.decode(data, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding unknow", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5(DigestUtils.DIGEST_KEY + "111111"));
    }
}
