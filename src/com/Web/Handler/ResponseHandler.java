package com.Web.Handler;

import com.Util.Debug;
import com.Web.Content.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class ResponseHandler {


    public ResponseHandler(){};
    public void send(Socket socket, Response response) throws IOException {
        OutputStream os = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        writer.write(response.getHeader());
        writer.flush();
        //Debug.log(response.getHeader());
//        writer.write(new String(response.getContent(),"utf-8"));
        os.write(response.getContent());
        os.flush();
        writer.write("\r\n");


    }
}
