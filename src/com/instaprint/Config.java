package com.instaprint;

import java.io.*;

/**
 * Created by calc on 04.07.14.
 *
 */
public class Config implements Serializable {
    transient private static final String CONFIG_FILE_NAME = "config.dat";

    private String accessToken = "1413531024.6041eee.200d59e8b9c3448e913ef319f2a26b8a";
    private String mainTag = "calcprint";
    private String secondTag = "print";
    private String irfanViewPath = "C:\\Program Files (x86)\\IrfanView\\i_view32.exe";

    private boolean exist(){
        File f = new File(CONFIG_FILE_NAME);
        return f.exists() && !f.isDirectory();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public String getSecondTag() {
        return secondTag;
    }

    public void setSecondTag(String secondTag) {
        this.secondTag = secondTag;
    }

    public String getIrfanViewPath() {
        return irfanViewPath;
    }

    public void setIrfanViewPath(String irfanViewPath) {
        this.irfanViewPath = irfanViewPath;
    }

    public static boolean serialize(Config config){
        File file = new File(CONFIG_FILE_NAME);

        try {
            if(!file.exists())
                if(!file.createNewFile())
                    return false;

            FileOutputStream fileOut = new FileOutputStream(CONFIG_FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(config);
            objectOut.flush();
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Config unserialize(){
        Config config = null;
        try {
            File file = new File(CONFIG_FILE_NAME);
            if(!file.exists()) return null;

            FileInputStream fileIn = new FileInputStream(CONFIG_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            config = (Config) objectIn.readObject();
            objectIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return config;
    }
}
