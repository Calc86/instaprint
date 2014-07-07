package com.instaprint;

import com.google.gson.Gson;
import com.gui.ImageContainer;
import com.instagram.Response;
import com.instagram.Tag;
import com.instagram.Urls;
import com.net.HTTP;
import com.printer.Printer;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by calc on 04.07.14.
 *
 */
public class Controller {
    private final int timeout;
    private long time;
    private final ImageContainer ic;

    private Thread thread;
    private boolean run = false;
    private final Config config;
    private final Printer printer;

    private final Set<String> ids;

    private boolean firstRun = true;

    private final HTTP http = new HTTP();

    public Controller(final Config config, final Printer printer, int timeout, long time, ImageContainer ic) {
        this.config = config;
        this.timeout = timeout;
        this.time = time;
        this.ic = ic;
        this.printer = printer;

        ids = new HashSet<String>();
    }

    public void start(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                go();
            }
        });

        run = true;
        thread.start();
    }

    public void stop(){
        run = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread = null;
    }

    public void pause(){

    }

    public void go(){
        // 1 получить json с инстаграмма
        while(run){
            try {
                process(getRecent());

                Thread.sleep(timeout);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date today = Calendar.getInstance().getTime();
                System.out.println(df.format(today));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(String json){
        if(json.equals("")) return;
        Gson gson = new Gson();

        Response response = gson.fromJson(json, Response.class);

        long nextTime = time;
        System.out.println("Code: " + response.getMeta().getCode());
        if(response.getMeta().getCode() == 200){
            for(Tag tag : response.getData()){
                if(!config.getSecondTag().equals("") && !tag.getTags().contains(config.getSecondTag())) continue;

                if(ids.contains(tag.getId())) continue;
                /*System.out.println(tag.getCreated_time() + " " + time);
                System.out.println(tag.getCreated_time() >= time);
                if(tag.getCreated_time() <= time){
                    System.out.println("continue");
                    continue;
                }*/

                if(firstRun){
                    ids.add(tag.getId());
                    continue;
                }

                System.out.println("created time: " + tag.getCreated_time());
                ids.add(tag.getId());

                nextTime = Math.max(nextTime, tag.getCreated_time());
                ic.addImage(tag.getImages().getThumbnail().getUrl());

                String file = "images/" + tag.getId() + ".jpg";
                if(!saveImage(tag.getImages().getStandard().getUrl(), file)) continue;

                File f = new File(file);
                file = f.getAbsolutePath();
                printer.print(file);
            }
            firstRun = false;
        }

        time = nextTime;
    }

    /**
     * get recent images by tag
     * @return json
     */
    private String getRecent(){
        try {
            return http.getContent(Urls.getTagsRecent(config.getMainTag(), config.getAccessToken()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Сохранить картинку в images
     * @return boolean
     */
    private boolean saveImage(String url, String file){
        try {
            http.putContent(url, file);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
