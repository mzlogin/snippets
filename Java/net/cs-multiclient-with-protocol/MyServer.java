import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyServer {
    private static final int SERVER_PORT = 30000;
    public static YeekuMap<String, PrintStream> clients = new YeekuMap<String, PrintStream>();
    public void init() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket s = ss.accept();
                new Thread(new ServerThread(s)).start();
            }
        } catch (IOException ex) {
            System.out.println("server start failed, " + SERVER_PORT + " is occupation?");
        } finally {
            try {
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MyServer server = new MyServer();
        server.init();
    }
}

class ServerThread extends Thread {
    private Socket socket;
    BufferedReader br = null;
    PrintStream ps = null;
    
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(YeekuProtocol.USER_ROUND) && line.endsWith(YeekuProtocol.USER_ROUND)) {
                    String userName = getRealMsg(line);
                    if (MyServer.clients.containsKey(userName)) {
                        System.out.println("repeat");
                        ps.println(YeekuProtocol.NAME_REP);
                    } else {
                        System.out.println("success");
                        ps.println(YeekuProtocol.LOGIN_SUCCESS);
                        MyServer.clients.put(userName, ps);
                    }
                } else if (line.startsWith(YeekuProtocol.PRIVATE_ROUND) && line.endsWith(YeekuProtocol.PRIVATE_ROUND)) {
                    String userAndMsg = getRealMsg(line);
                    String user = userAndMsg.split(YeekuProtocol.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(YeekuProtocol.SPLIT_SIGN)[1];
                    MyServer.clients.get(user).println(MyServer.clients.getKeyByValue(ps) + "say to you quietly: " + msg);
                } else {
                    String msg = getRealMsg(line);
                    for (PrintStream clientPs : MyServer.clients.valueSet()) {
                        clientPs.println(MyServer.clients.getKeyByValue(ps) + " say: " + msg);
                    }
                }
            }
        } catch (IOException e) {
            MyServer.clients.removeByValue(ps);
            System.out.println(MyServer.clients.size());
            try {
                if (br != null) {
                    br.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getRealMsg(String line) {
        return line.substring(YeekuProtocol.PROTOCOL_LEN, line.length() - YeekuProtocol.PROTOCOL_LEN);
    }
}
