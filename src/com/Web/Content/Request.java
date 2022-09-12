package com.Web.Content;

import com.Util.Debug;
import com.Util.Tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

/**
 * @version 0.0.0
 * @author  dai
 * @date    2022/9/11
 * @description 生成Request实体类
 * */
public class Request {
    public String version;
    public String method;
    public String dir;
    public Hashtable<String,String> fields;

    public Request(Socket socket) throws IOException {
        this.fields=new Hashtable<>();
        this.excute(socket);
    }
    private void excute(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String s= reader.readLine();
        Debug.log(socket.getInetAddress()+" "+s, Tag.REQU);
        String[] header = s.split(" ");
        String[] kv;
        this.method=header[0];
        this.dir=header[1];
        this.version=header[2];
        //System.out.println(s);
        s=reader.readLine();
        while(!s.equals("")){//以空字符串结束
            //System.out.println(s);
            kv=s.split(":");
            this.fields.put(kv[0],kv[1]);
            s=reader.readLine();
        }
        //System.out.println("-----------------------------------------------------------");
//        Debug.console("method:%s".formatted(this.method));
//        Debug.console("version:%s".formatted(this.version));
//        Debug.console("dir:%s".formatted(this.dir));
//        for (Map.Entry e:fields.entrySet())
//            Debug.console("%s:%s".formatted(e.getKey(),e.getValue()));
    }
}
