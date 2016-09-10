import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class CalcAES {
    public static SecretKey getKey(byte[] key) {
        try {
            if (key.length >= 16) {
                return new SecretKeySpec(key, "AES");
            }
        } catch (IllegalArgumentException ex) {
        }
        return null;
    }

    public static String encAES(String content, String key) {
        return encAES(content.getBytes(), key.getBytes());
    }

    public static String decAES(String content, String key) {
        return decAES(hexToByteArray(content), key.getBytes());
    }

    public static String encAES(byte[] content, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = getKey(key);
            if (secretKey != null) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return byteArrayToHex(cipher.doFinal(content));
            }
        } catch (Exception e) {}
        return null;
    }

    public static String decAES(byte[] content, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = getKey(key);
            if (secretKey != null) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(content));
            }
        } catch (Exception e) {}
        return null;
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

    public static byte[] hexToByteArray(String hexStr) {
       if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;   
    }

    public static void main(String[] args) {
//        System.out.println(CalcAES.encAES("id=0&sign=6db187fadfd010232350ae18521211a0&dateline=1466016580", "bhu8nhy6!QAZ@WSX"));
//        System.out.println(CalcAES.decAES("9dff70c17038382b6ca7fc039f5bbe03562a1a223ea818935d3537c5b146b59e6a0ca3052e65c69d52b34d0924db5221c88b5b56f6f487014d68f35f73339d39", "bhu8nhy6!QAZ@WSX"));
//        System.out.println(CalcAES.decAES("4cb76926b5e6ba823df94cded1f2a52aeb3a0e1b1c5a2364fce570c8e74fc985", "bhu8nhy6!QAZ@WSX"));
//        error: invalid query
//        System.out.println(CalcAES.decAES("aaaf9c0d6b5d6b4db1e19d6cde9b0ab2d0bad91464ed96b4f82321d5778df33d", "bhu8nhy6!QAZ@WSX"));
//        error: invalid partkey
//        System.out.println(CalcAES.decAES("93effa550c4e99853750a71b3f2cf08c4b50dcb0b9853f0ad67000bfeb34e347", "bhu8nhy6!QAZ@WSX"));
//        data: first data
//        System.out.println(CalcAES.decAES("a86a3666fdec54e39b3a8fea5cdff888d8717ac2b4a4d26df69967689a27d7c7", "bhu8nhy6!QAZ@WSX"));
//        data: second data
//        System.out.println(CalcAES.decAES("d9e128643d46562942c62de2e81dd1f34b50dcb0b9853f0ad67000bfeb34e347", "bhu8nhy6!QAZ@WSX"));
//        data: third data
//        System.out.println(CalcAES.decAES("1827d23bc2bf3fe8e919996c3d11ba89", "bhu8nhy6!QAZ@WSX"));
//        error: no data
//        System.out.println(CalcAES.encAES("partkey=0&sign=6db187fadfd010232350ae18521211a0", "bhu8nhy6!QAZ@WSX"));
    }
}
