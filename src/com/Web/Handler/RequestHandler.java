package com.Web.Handler;

import com.Web.Content.Request;
import com.Web.Content.Response;
import com.Web.Server;
import com.Util.Debug;
import jdk.jfr.Description;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * @version 0.0.0
 * @author  dai
 * @date    2022/9/11
 * @description 处理各种请求
 * */
public class RequestHandler implements Runnable{
    private ServerSocket serverSocket;
    public RequestHandler(ServerSocket server){
        if(server!=null)
            this.serverSocket=server;
        else
            throw new NullPointerException("server is null!");
    }

    @Override
    public void run() {
        synchronized (serverSocket){
            try {
                Debug.log(this+"线程开始！");
                Socket socket = serverSocket.accept();
                Debug.log(socket.getInetAddress()+"连接成功！");
                Request request = new Request(socket);
                Response response = this.excute(request);
                new ResponseHandler().send(socket,response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Description("根据request实体，创建对应的response")
    private Response excute(Request request) throws IOException {
        Response response = new Response();
        //Debug.log("request dir\t"+request.dir);// / +路径
        //Debug.log("server root\t"+Server.Root.getPath());// ./root
        File requestFile = new File(Server.Root.getPath()+ URLDecoder.decode(request.dir));
        if (requestFile.isDirectory()){
            response.setContent(this.getIndexPage(requestFile));
            response.set("Content-Type","text/html;charset=utf-8");
            response.set("Content-Length",String.valueOf(response.getContent().length));
        }
        else if (requestFile.exists()&&requestFile.isFile()){
            //Debug.log(requestFile.getPath());
            response.setFile(requestFile.getPath());
            response.set("Content-Type","application/octet-stream");
            response.set("Content-Length",String.valueOf(requestFile.length()));
            response.set("Content-Disposition","attachment;filename=%s".formatted(requestFile.getName()));
        }
        //Debug.log(response.getHeader());
            //throw new IllegalStateException("UnKnown Error!");
        return response;
    }

    private byte[] getIndexPage(File file) throws IOException {
        String root =Server.Root.getPath().replace("\\","/");
        String currentRequestDir=file.getPath().replace("\\","/");
        currentRequestDir=currentRequestDir.substring(currentRequestDir.indexOf(root)+root.length());
        String lastDir = currentRequestDir.substring(0,currentRequestDir.lastIndexOf("/")==-1?0:currentRequestDir.lastIndexOf("/"));
        lastDir = lastDir == "" ? "/":lastDir;
        //Debug.log("last dir in html\t"+lastDir);
        String template="<div class='as'><a href='%s'>%s</a><br></div>";
        String links="";
        //Debug.log("actual dir in server\t"+file.getPath());
        for (String f : file.list()){
            links=links.concat(template.formatted(currentRequestDir.concat("/"+f),f));
        }
        FileInputStream fis = new FileInputStream("resources/index.html");
        String index = new String(fis.readAllBytes(),"utf-8").replace("{{links}}",links)
                .replace("{{lastdir}}",lastDir);
        fis.close();
        //Debug.console(index);
        return index.getBytes("utf-8");
    }
}
