import com.FileTransfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入文件全路径：");
        String file = scanner.nextLine();
        System.out.print("请输入端口号：");
        int port = scanner.nextInt();*/
        String file = "E:\\vedio.mp4";
        int port =8080;
        String ip="192.168.45.61";
        Thread t = new Thread(new FileTransfer(file,ip,port));
        t.start();
    }
}
