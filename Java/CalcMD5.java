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

    public static Long stringMD5FirstHalfToLong(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = input.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            Long result = 0L;
            if (resultByteArray != null && (resultByteArray.length == 16)) {
                int i = 0;
                while (i < 8) {
                    int j = 0xFF & resultByteArray[i];
                    long l = result << 8 | j;
                    i++;
                    result = l;
                }
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String input = "android";
        System.out.println(CalcMD5.stringMD5(input));
        System.out.println(CalcMD5.stringMD5FirstHalfToLong(input));
    }
}
