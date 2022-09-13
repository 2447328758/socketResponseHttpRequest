package com.Web.Test;

import com.Web.Controller.ThreadController;
import com.Web.Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @version 0.0.1
 * @author  dai
 * @date    2022/9/13
 * @description test using Thread controller
 * */
public class test2 {
    public static void main(String[] args) throws IOException {
        Server.setInetSocketAddr("127.0.0.1",8080);
        ServerSocket server = Server.CreateServer("./root");

        ThreadController threadController = new ThreadController();
        threadController.setMaxCount(30);
        threadController.setServer(new Server());
        threadController.start();
}
