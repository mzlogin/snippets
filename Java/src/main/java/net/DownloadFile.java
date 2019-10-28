package net;

import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;

public class DownloadFile {
    public static void main(String[] args) {

        String picUrl = "https://mazhuang.org/assets/images/qrcode.jpg";

        String tempPath =System.getProperty("java.io.tmpdir");
        String fileName = String.valueOf(System.currentTimeMillis());
        int index = picUrl.lastIndexOf(".");
        String suffix = picUrl.substring(index);
        fileName += suffix;
        String filePath = tempPath + fileName;

        System.out.println("file path: " + filePath);

        boolean result = downloadPicture(picUrl, filePath);
        System.out.println("download result: " + result);
    }

    public static boolean downloadPicture(String picUrl, String filePath) {
        URL url;

        try {
            url = new URL(picUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
