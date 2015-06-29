import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalcMD5 {
    public static String stringMD5(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = input.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            System.out.println(resultByteArray);
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        final String hexDigits = "0123456789abcdef";
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits.toCharArray()[(b & 0xf0) >>> 4];
            resultCharArray[index++] = hexDigits.toCharArray()[b & 0xf];
        }

        return new String(resultCharArray);
    }

    public static void main(String[] args) {
        System.out.println(CalcMD5.stringMD5("hello"));
    }
}
