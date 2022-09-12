package com.Web;


import com.Util.Debug;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @version 0.0.0
 * @author  dai
 * @date    2022/9/11
 * @description 创建服务器对象
 * */
public class Server {

    public static File Root;
    private static SocketAddress SocketAddr;
    private static ServerSocket ServerSocket;
    public static ServerSocket CreateServer(String root) throws IOException {
        Debug.setFileWriter("./log/log.txt");
        setRoot(root);
        if (ServerSocket !=null&& ServerSocket.isBound())
            ;
        else
            initServerSocket();
        return ServerSocket;
    }

    public static void setInetSocketAddr(String ip, int port){
        if (SocketAddr !=null)
            throw new IllegalStateException("socketaddr is already exists!");
        else
            SocketAddr =new InetSocketAddress(ip,port);
    }

    private static void initServerSocket() throws IOException {
        if (SocketAddr ==null)
            throw new IllegalStateException("socketaddr is null! must invoke method setInetSocketAddr first!");
        ServerSocket =new ServerSocket();
        ServerSocket.bind(SocketAddr);
        Debug.log(SocketAddr+"创建服务成功！");
    }


    public static void setRoot(String root){
        File file=new File(root);
        if (!file.exists()||!file.isDirectory())
            throw new IllegalStateException("dir is not found!");
        Server.Root =file;
    }

}
