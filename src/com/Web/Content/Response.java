package com.Web.Content;

import com.Web.Interfaces.Responsable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * @version 0.0.0
 * @author  dai
 * @date    2022/9/11
 * @description 生成Response实体类
 * */
public class Response implements Responsable {
    private int statusCode;
    private String status;
    private String version;

//    ArrayList<String> keys;
    Hashtable<String,String> fields;
    private byte[] content;
    private String header;

    public Response(){
//        this.keys=new ArrayList<>();
        this.fields=new Hashtable<>();
        this.statusCode = 200;
        this.status = "OK";
        this.version = "HTTP/1.1";
    }

    public Response(int statusCode, String status, String version) {
//        this.keys=new ArrayList<>();
        this.fields=new Hashtable<>();
        this.statusCode = statusCode;
        this.status = status;
        this.version = version;
    }

    public void set(String key, String value){
//        this.keys.add(key);
        this.fields.put(key,value);
    }

    public String get(String key){
        return this.fields.get(key);
    }

    public void setContent(byte[] content){
        this.content=content;
    }
    public void setFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        this.content=fis.readAllBytes();
        fis.close();
    }

    @Override
    public String getHeader(){
        this.header="%s %d %s\r\n".formatted(this.version,this.statusCode,this.status);
        for (Map.Entry<String ,String > e :fields.entrySet()){
            this.header = this.header.concat("%s: %s\r\n".formatted(e.getKey(),e.getValue()));
        }
//        for (String key:keys)
//            this.header = this.header.concat("%s: %s\r\n".formatted(key,this.get(key)));
        return this.header.concat("\r\n");
    }

    @Override
    public byte[] getContent() {
        if (content == null)
            throw new IllegalStateException("response content is null");
        else
            return this.content;
    }
}
