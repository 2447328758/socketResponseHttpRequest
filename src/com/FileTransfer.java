package com;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class FileTransfer implements Runnable{
    private static String ip;
    private static  int port;
    private static SocketAddress socketAddress;
    private File file;
    private byte[] data;


    public FileTransfer(String f, int port) throws IOException {
        this.initFile(f);
        FileTransfer.port=port;
        InetAddress inetAddress = InetAddress.getLoopbackAddress();
        FileTransfer.socketAddress = new InetSocketAddress(inetAddress,port);
        System.out.println(FileTransfer.socketAddress+"设置成功！");
    }
    public FileTransfer(String f, String ip, int port) throws IOException {
        FileTransfer.ip=ip;
        FileTransfer.port=port;
        this.initFile(f);
    }
    private boolean initFile(String f) throws IOException {
        file = new File(f);
        if (file.exists()){
            FileInputStream fis = new FileInputStream(file);
            data=fis.readAllBytes();
            fis.close();
            return true;
        }
        else
            throw new FileNotFoundException(f+"is not exists!");
    }

    private boolean start() throws IOException {
        int i = 0;
        ServerSocket server = new ServerSocket();
        SocketAddress sa=socketAddress;
        if (sa==null){}
        sa = new InetSocketAddress(ip,port);
        System.out.println(sa+"设置成功！");
        server.bind(sa);
        while(true){
            try{
                i++;
                Socket socket = server.accept();
                OutputStream os = socket.getOutputStream();
                System.out.println(socket.getInetAddress()+"连接成功！\tid:"+i);
                //this.PrintRequest(socket);
                Writer writer = new PrintWriter(os);
                writer.write("HTTP/1.1 200 OK\r\n");
                //writer.write("Connection:keep-alive\r\n");
                writer.write("Content-Type:application/octet-stream\r\n");
                writer.write("Content-Length:%d\r\n".formatted(data.length));
                writer.write("Content-Disposition:attachment;filename="+file.getName()+"\r\n\r\n");
                System.out.println(file.getAbsoluteFile()+"\tlen:"+data.length);
                writer.flush();
                os.write(data);
                os.flush();// 把所有的数据都推送出去
                System.out.println("发送完成！");
            }catch (IOException ex){
                System.out.println(ex.getMessage());
                System.out.println("send failed!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void PrintRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while(reader.ready()){
            System.out.println(reader.readLine());
        }
        System.out.println("----------------------------------------------");
    }
}
