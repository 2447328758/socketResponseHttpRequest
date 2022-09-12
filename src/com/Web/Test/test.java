package com.Web.Test;

import com.Web.Handler.RequestHandler;
import com.Web.Server;

import java.io.IOException;
import java.net.ServerSocket;


//BCACA CDABA
public class test {
    public static void main(String[] args) throws IOException {
        Server.setInetSocketAddr("127.0.0.1",8080);
        ServerSocket server = Server.CreateServer("./root");

        for (int i=0;i<30;i++){
            new Thread(new RequestHandler(server)).start();
        }

//        File f = Server.Root;
//        File t1,t2,t3;
//        for (int i=0;i<3;i++){
//            t1 = new File(f.toString()+"\\"+ UUID.randomUUID());
//            t1.mkdir();
//            for (int j=0;j<10;j++){
//                t2 = new File(t1.toString()+"\\"+ UUID.randomUUID());
//                t2.mkdir();
//                for (int h=0;h<20;h++){
//                    t3 = new File(t2.toString()+"\\"+ UUID.randomUUID());
//                    t3.mkdir();
//                }
//            }
//        }
    }
}
