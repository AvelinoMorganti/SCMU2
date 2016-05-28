package com.whaves.scmu;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Douglas on 16/04/2016.
 */
public class CookiesImpl implements Serializable {

    String name;
    String value;
    String comment;
    String commentURL;
    String domain;
    Date expiryDate;
    String path;
    int []ports;
    int version;
    boolean persistent;
    boolean secure;
    boolean expired;


    public CookiesImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public CookiesImpl(String name,
                       String value,
                       String comment,
                       String commentURL,
                       String domain,
                       Date expiryDate,
                       String path,
                       int []ports,
                       int version,
                       boolean persistent,
                       boolean secure,
                       boolean expired) {
        this.name = name;
        this.value = value;
        this.comment = comment;
        this.commentURL = commentURL;
        this.domain = domain;
        this.expiryDate = expiryDate;
        this.path = path;
        this.ports = ports;
        this.version = version;
        this.persistent = persistent;
        this.secure = secure;
        this.expired = expired;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentURL() {
        return commentURL;
    }

    public String getDomain() {
        return domain;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getPath() {
        return path;
    }

    public int []getPorts() {
        return ports;
    }

    public int getVersion() {
        return version;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isExpired(Date date) {
        return false;
    }

}
