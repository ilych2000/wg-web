package ru.wg.utils;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.lang3.CharUtils;
import org.apache.log4j.Logger;

public class FileUtils {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(FileUtils.class);

    public static void clearTempDir(final String paternForDelete, String dir) {

        File folder = new File(dir);

        File[] files = folder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isFile() && (file.getName().indexOf(paternForDelete) != -1);
            }
        });

        clearDir(files);
    }

    public static void clearTempDir(final long timeLife, String dir) {

        File folder = new File(dir);

        File[] files = folder.listFiles(new FileFilter() {

            private long deleteTime = System.currentTimeMillis() - timeLife;

            @Override
            public boolean accept(File file) {
                return file.isFile() && (file.lastModified() < deleteTime);
            }
        });

        clearDir(files);
    }

    private static void clearDir(File[] files) {

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    public static String toSafeFileName(String unsafeFileName) {
        if (unsafeFileName == null) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < unsafeFileName.length(); i++) {
            char ch = unsafeFileName.charAt(i);
            if (CharUtils.isAsciiAlphanumeric(ch) || (ch == '-') || (ch == '.')) {
                b.append(ch);
            } else {
                b.append('_');
                b.append((int) ch);
                b.append('_');
            }
        }
        return b.toString();
    }
}
