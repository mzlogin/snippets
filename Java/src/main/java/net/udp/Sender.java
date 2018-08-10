package net.udp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender {

    public static String sContent = "1111";

    public static void main(String[] args) {
        MulticastSocket multicastSocket = null;

        try {
            InetAddress destAddress = InetAddress.getByName(Constants.MULTICAST_IP);
            if (!destAddress.isMulticastAddress()) {
                throw new Exception("不是多播地址！");
            }

            multicastSocket = new MulticastSocket();
            multicastSocket.setTimeToLive(Constants.TTL_TIME);
            multicastSocket.setReuseAddress(true);
            // multicastSocket.joinGroup(destAddress);

            while (true) {
                Thread.sleep(1000);
                byte[] sendMsg = sContent.getBytes();
                DatagramPacket packet = new DatagramPacket(sendMsg, sendMsg.length, destAddress, Constants.MULTICAST_PORT);
                multicastSocket.send(packet);
                System.out.println("multicast " + sContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (multicastSocket != null) {
                multicastSocket.close();
            }
        }
    }
}
