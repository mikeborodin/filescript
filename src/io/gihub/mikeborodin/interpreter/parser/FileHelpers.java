package io.gihub.mikeborodin.interpreter.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileHelpers {
    public static boolean IsValidPath(String path)
    {
        File f = new File(path);
        try {
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    public static boolean isAbsolute(String path){
        File f = new File(path);
        return f.isAbsolute();
    }

    public static String createAbsolutePath(String workingdir, String relative)
    {
        //
        String nworkingdir = !"\\/".contains(""+workingdir.charAt(workingdir.length() - 1))
                ? workingdir + '\\' : workingdir;

        String nrelative = "\\/".contains(""+relative.charAt(0)) ? relative.substring(1) : relative;
        return nworkingdir + nrelative;
    }

}
