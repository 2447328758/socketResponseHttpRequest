package com.Util;


import java.io.*;
import java.util.Date;

public class Debug {

    private static BufferedWriter fileWriter;
    private static String template="%s:[%s]\t%s";

    public static void log(String log) throws IOException {Debug.log(log,Tag.INFO);}
    public static void log(String log,Tag tag) throws IOException {
        Debug.console(log,tag);
        Debug.file(log,tag);
    }

    public static void console(String log) { Debug.console(log, Tag.INFO); }
    public static void console(String log, Tag tag){
        System.out.println(template.formatted(new Date(),tag,log));
    }

    public static void setFileWriter(String file) throws IOException {
        if (fileWriter!=null){
            Debug.console("fileWriter already exists!",Tag.FATAL);
            throw new IllegalStateException("fileWriter already exists!");
        }
        fileWriter = new BufferedWriter(new FileWriter(file));
        Debug.console(file+"设置成功",Tag.INFO);
    }
    public static void file(String log, Tag tag) throws IOException {
        synchronized (fileWriter){
            fileWriter.write(template.formatted(new Date(),tag,log)+"\r\n");
            fileWriter.flush();
        }
    }
}
