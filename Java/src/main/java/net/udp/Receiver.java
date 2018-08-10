package net.udp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver {
    public static void main(String[] args) {
        MulticastSocket receiveMulticast = null;

        try {
            InetAddress receiveAddress = InetAddress.getByName(Constants.MULTICAST_IP);
            if (!receiveAddress.isMulticastAddress()) {
                throw new Exception("不是多播地址！");
            }

            receiveMulticast = new MulticastSocket(Constants.MULTICAST_PORT);
            receiveMulticast.joinGroup(receiveAddress);
            receiveMulticast.setLoopbackMode(true);

            DatagramPacket packet = new DatagramPacket(new byte[Constants.RECEIVE_LENGTH], Constants.RECEIVE_LENGTH);

            while (true) {
                receiveMulticast.receive(packet);
                if (packet.getData() != null) {
                    System.out.println("receive " + new String(packet.getData()).trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (receiveMulticast != null) {
                receiveMulticast.close();
            }
        }
    }
}
