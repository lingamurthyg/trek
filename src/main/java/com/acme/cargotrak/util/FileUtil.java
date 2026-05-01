package com.acme.cargotrak.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Yes, this duplicates Util.readFile/writeFile. miyer added these in 2010 because
 * the Util grab-bag was already too big. Now we have both. Unfixable.
 *
 * Note: HARD-CODED Windows path used as default temp dir. somebody developed on Windows.
 */
public class FileUtil {

    public static final String TEMP_DIR_LINUX   = "/var/app/cargo/tmp";
    public static final String TEMP_DIR_WINDOWS = "C:\\temp\\cargo\\";

    public static String defaultTempDir() {
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("windows") >= 0) return TEMP_DIR_WINDOWS;
        return TEMP_DIR_LINUX;
    }

    public static byte[] read(File f) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] buf = new byte[(int) f.length()];
            int read = 0;
            while (read < buf.length) {
                int n = fis.read(buf, read, buf.length - read);
                if (n < 0) break;
                read += n;
            }
            return buf;
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception ex) {}
        }
    }

    public static void write(File f, byte[] data) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(data);
        } finally {
            try { if (fos != null) fos.close(); } catch (Exception ex) {}
        }
    }

    public static boolean ensureDir(String path) {
        File f = new File(path);
        return f.exists() ? f.isDirectory() : f.mkdirs();
    }

    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dst);
            byte[] buf = new byte[8192];
            int n;
            while ((n = fis.read(buf)) > 0) fos.write(buf, 0, n);
        } finally {
            try { if (fis != null) fis.close(); } catch (Exception ex) {}
            try { if (fos != null) fos.close(); } catch (Exception ex) {}
        }
    }
}
