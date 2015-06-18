import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintStream;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(30000);
        while (true) {
            Socket s = ss.accept();
            PrintStream ps= new PrintStream(s.getOutputStream());
            ps.println("Hello, Client! -- from server");
            ps.close();
            s.close();
        }
    }
}
