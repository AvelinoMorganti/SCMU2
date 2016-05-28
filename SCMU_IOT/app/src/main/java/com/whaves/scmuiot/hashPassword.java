/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whaves.scmuiot;

/**
 *
 * @author avelino
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class hashPassword {

    public hashPassword() {
    }

    public static char getRandomChar() {
        String str = "0Aa1Bb2Cc3Dd4EeFf5Gg6Hh7Ii8Jj9Kk0Ll1Mm2Nn3Oo4Pp57Qq6Rr7Ss8Tt9Uu1Vv20Ww3Xx4Yy5Zz6!@#7$8%*&9()-_";
        int rnd = (int) (Math.random() * 62);
        return str.charAt(rnd);
    }

    public static String getRandomString(int lenght) {
        String randomString = "";
        for (int i = 0; i < lenght; i++) {
            randomString += getRandomChar();
        }
        return randomString;
    }

    public static String getSHA512(String string) {
        String sha512 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-512");
            crypt.reset();
            crypt.update(string.getBytes("UTF-8"));
            string = "";
            sha512 = byteToHex(crypt.digest());

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao codificar senha."
                    + "\n Algoritmo de Hash não encontrado." + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro ao codificar senha."
                    + "\nCodificação Não suportada." + e.getMessage());
        }
        return sha512;
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
