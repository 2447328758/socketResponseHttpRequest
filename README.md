# socketResponseHttpRequest
This is a small App for simple data transport through LAN. The server is use socket to response the HttpRequest.

## The author 

I'm just a college student form China with poor English and poor coding skills.I create the small app for fun and during my coding, I refered many others' codes and struggled to find articles
about HTTP beacause the articles I searched onliner more likely to talk about some packaged http tools like Spring. But I just want to learn about how datas are trasported
in socket.

The porject is not only done just with me but also who writes the code I refered and writes the blogs I read. And I'm thankful to all of them.

***Last but not least, I'm realy expected to recieved advices about
wheater my English writing or the code skills. Also, I beg your understanding and forgiveness for my some umcomfortable but not indended expressions in the passage for it's the
first time to post online in English.***

## com.FileTransfer
FileTransfer is an experiment for my test that to create a tool to transport files throw LAN with Http protocol.It show how http works.
- Request 
  The format of request is as follows:
  
  <request_method> <request_path> <http_protocol_version>\r\n
  
  [key:value]\r\n
  
  [key:value]\r\n
  
  ...
  
  \r\n *//this is a mark for server to identify the request header and the request body*
  
  [request body]
  
  /**
  
  the key-values tells server how to response the request.
  
  */
  
- Response
  The format of response is as follows:
  
  <http_protocol_version> <status_code> <status_message>
  
  [key:value]\r\n
  
  [key:value]\r\n
  
  ...
  
  \r\n *//this is a mark for broswer to identify the request header and the request body*
  
  [response body]It can be binary data;
  
## The use of the file transfer

All the functions are based on the com.FileTransfer.

### Quick start 

- Directory setting

  Make sure that the resources/index.html the project supply is under the working directory. Because it is a template for all html page you will see.

- Create a server with ip, port and the file path to transfer like this:

  The MAIN function determines how you set your root path. And the ip and port is up to you and your computer.

```java
  Server.setInetSocketAddr("127.0.0.1",8080);
  ServerSocket server = Server.CreateServer("./root");
```

- Create and start RequestHandler threads whih the server to handle the request

  The amout of Threads decides how many requests your server can handle. It is just a simple usage and you can expand the use with your own codes.

```java
  for (int i=0;i<30;i++){
      new Thread(new RequestHandler(server)).start();
  }
```

- Opne your broswer under the same LAN and just use it.

  Tag the address like ip:port in ther url text box and press Enter.Then you wiil see the index page.