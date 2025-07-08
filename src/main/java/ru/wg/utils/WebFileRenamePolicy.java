package ru.wg.utils;

import java.io.File;
import java.io.IOException;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class WebFileRenamePolicy implements FileRenamePolicy {

    @Override
    public File rename(File f) {
        String body = System.currentTimeMillis() + "_" + this.hashCode() + ".";
        String ext = "jpg";

        if (f != null) {
            f = new File(f.getParent(), body + ext);
        } else {
            f = new File(body + ext);
        }

        int count = 0;
        while (!createNewFile(f) && (count < 9999)) {
            count++;
            String newName = body + count + ext;
            f = new File(f.getParent(), newName);
        }

        return f;
    }

    private boolean createNewFile(File f) {
        try {
            return f.createNewFile();
        } catch (IOException ignored) {
            return false;
        }
    }
}
