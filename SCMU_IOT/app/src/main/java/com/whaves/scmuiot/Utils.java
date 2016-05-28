package com.whaves.scmuiot;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaves.scmuiot.CookiesImpl;

/**
 * Created by Douglas on 21/04/2016.
 */
public class Utils {


    public static CookieStoreImpl createCookieStoreImpl(CookieStore cookieStore){

        List<Cookie> cookies = cookieStore.getCookies();
        List<CookiesImpl> listCookies = new ArrayList<CookiesImpl>();
        for (Cookie c : cookies) {
            listCookies.add(
                    new CookiesImpl(c.getName(),
                            c.getValue(),
                            c.getComment(),
                            c.getCommentURL(),
                            c.getDomain(),
                            c.getExpiryDate(),
                            c.getPath(),
                            c.getPorts(),
                            c.getVersion(),
                            c.isPersistent(),
                            c.isSecure(),
                            c.isExpired(new Date())));
        }

        return new CookieStoreImpl(listCookies);
    }

    public static BasicCookieStore createApacheCookieStore(List<CookiesImpl> cookiesList){

        BasicClientCookie c[] = new BasicClientCookie[cookiesList.size()];
        BasicCookieStore ck = new BasicCookieStore();
        int i = 0;

        for(CookiesImpl a: cookiesList){
            c[i] = new BasicClientCookie(a.getName(), a.getValue());
            c[i].setComment(a.getComment());
            //c[i].setCreationDate(a.g);
            c[i].setDomain(a.getDomain());
            c[i].setExpiryDate(a.getExpiryDate());
            c[i].setPath(a.getPath());
            c[i].setSecure(a.isSecure());
            c[i].setVersion(a.getVersion());
            ck.addCookie(c[i]);
            i++;
       }
        //CookieStore asd = (CookieStore) ck;

        return ck;
    }


}
