package com.Web.Controller;

import com.Util.Debug;
import com.Web.Handler.RequestHandler;
import com.Web.Server;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @version 0.0.0
 * @author  dai
 * @date    2022/9/13
 * @description 线程管理
 * */
public class ThreadController {
    private static Server server;
    private static int MaxCount;
    private static ArrayList<Thread> Rhandles=new ArrayList<>();
    private static  ThreadController controller;

    public static ThreadController CreateController(){
        if (controller==null)
            controller=new ThreadController();
        return controller;
    }

    public void setServer(Server server){
        ThreadController.server=server;
    }


    public void setMaxCount(int maxCount) {
        if (maxCount<1)
            maxCount=30;
        MaxCount = maxCount;
    }

    public int getMaxCount(){
        return MaxCount;
    }

    public void start(){
        Thread t;
        while (true){
            while(Rhandles.size()<MaxCount){
                t = new Thread(new RequestHandler(server.CreateServer()));
                t.start();
                Rhandles.add(t);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean contains(Thread t){
        return Rhandles.contains(t);
    }

    public void remove(Thread thread) throws IOException {
        Rhandles.remove(thread);
        Debug.log(thread + "is removed!");
    }
}
