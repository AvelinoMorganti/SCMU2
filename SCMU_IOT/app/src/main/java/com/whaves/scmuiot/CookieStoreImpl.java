package com.whaves.scmuiot;

import org.apache.http.cookie.Cookie;

import java.io.ObjectInput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Douglas on 16/04/2016.
 */
public class CookieStoreImpl implements Serializable {


    List<CookiesImpl> listCookies;

    public CookieStoreImpl(List<CookiesImpl> listCookies) {
        this.listCookies = listCookies;
    }

    public List<CookiesImpl> getData() {
        return listCookies;
    }

}
