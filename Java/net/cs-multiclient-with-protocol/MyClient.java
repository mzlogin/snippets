import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.net.UnknownHostException;

public class MyClient {
    private static final int SERVER_PORT = 30000;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader brServer;
    private BufferedReader keyIn;
    public void init() {
        try {
            keyIn = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket("127.0.0.1", SERVER_PORT);
            ps = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tip = "";
            while (true) {
                String userName = JOptionPane.showInputDialog(tip + "input your name ");
                ps.println(YeekuProtocol.USER_ROUND + userName + YeekuProtocol.USER_ROUND);
                String result = brServer.readLine();
                if (result.equals(YeekuProtocol.NAME_REP)) {
                    tip = "repeat! reinput.";
                    continue;
                }

                if (result.equals(YeekuProtocol.LOGIN_SUCCESS)) {
                    break;
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("cannot find server.");
            closeRs();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("network unusual.");
            closeRs();
            System.exit(1);
        }
        new ClientThread(brServer).start();
    }

    private void readAndSend() {
        try {
            String line = null;
            while ((line = keyIn.readLine()) != null) {
                if (line.indexOf(":") > 0 && line.startsWith("//")) {
                    line = line.substring(2);
                    ps.println(YeekuProtocol.PRIVATE_ROUND + line.split(":")[0] + YeekuProtocol.SPLIT_SIGN + line.split(":")[1] + YeekuProtocol.PRIVATE_ROUND);
                } else {
                    ps.println(YeekuProtocol.MSG_ROUND + line + YeekuProtocol.MSG_ROUND);
                }
            }
        } catch (IOException ex) {
            System.out.println("network unusual.");
            closeRs();
            System.exit(1);
        }
    }

    private void closeRs() {
        try {
            if (keyIn != null) {
                keyIn.close();
            }

            if (brServer != null) {
                brServer.close();
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

    public static void main(String[] args) throws IOException {
        MyClient client = new MyClient();
        client.init();
        client.readAndSend();
    }
}

class ClientThread extends Thread {
    BufferedReader br = null;
    public ClientThread(BufferedReader br) {
        this.br = br;
    }

    public void run() {
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
