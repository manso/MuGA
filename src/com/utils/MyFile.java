//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 22/mar/2016, 11:47:12
 *
 * @author zulu - computer
 */
public class MyFile {

    /**
     * gets the path of one filename
     *
     * @param fullFilename full name of the file ( teste.txt )
     * @return  path ( x:/dir/ )
     */
    public static String getPath(String fullFilename) {
        File file = new File(fullFilename);
        fullFilename = file.getAbsolutePath();
        file = new File(fullFilename);

        if (file.isDirectory()) {
            return file.getAbsolutePath() +  File.separatorChar;
        }
        return file.getParent() == null
                ? "." + File.separatorChar
                : file.getParent() + File.separatorChar;
    }
    /**
     * gets the file name
     *
     * @param fullFilename full path ( c:/dir/file.txt)
     * @return name (file.txt)
     */
    public static String getFullFileName(String fullFilename) {
        File file = new File(fullFilename);
        if (file.isDirectory()) {
            return "";
        }
        String path = file.getAbsolutePath();
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }

    /**
     * gets only the file name
     *
     * @param fullFilename full path ( c:/dir/file.txt)
     * @return only the name (file)
     */
    public static String getFileName(String fullFilename) {
        String fileName = getFullFileName(fullFilename);
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * gets only the extension of the file name
     *
     * @param fullFilename full path ( c:/dir/file.txt)
     * @return extension (txt)
     */
    public static String getFileExtension(String fullFilename) {
        String fileName = getFullFileName(fullFilename);
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    public static void main(String[] args) {
        //String f = "c:\\a\\b\\file.point.txt";
        String f = "c:/windows/x.txt";
        System.out.println("path = " + getPath(f));
        System.out.println("full File = " + getFullFileName(f));
        System.out.println("name = " + getFileName(f));
        System.out.println("extendion = " + getFileExtension(f));

    }

    /**
     * read all bytes in the text file
     * @param fileName
     * @return 
     */
    public static String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException ex) {
            // Logger.getLogger(MyFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603221147L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
