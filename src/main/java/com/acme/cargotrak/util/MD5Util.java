package com.acme.cargotrak.util;

/**
 * Wrapper around Util.md5 to make legacy call sites readable.
 *
 * @author Rajesh Kumar 2008-12
 */
public class MD5Util {

    public static String hash(String input) {
        return Util.md5(input);
    }
}
