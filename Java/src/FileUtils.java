import java.io.File;

public class FileUtils {
    public static void main(String[] args) {

        listAllFiles(".");

        File file = new File("./FileUtils.java");
        System.out.println(getSimpleName(file.getAbsolutePath()));
    }

    public static String getSimpleName(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            filename = filename.substring(0, index);
        }

        index = filename.lastIndexOf(File.separator);

        if (index != -1 && filename.length() > index + 1) {
            filename = filename.substring(index + 1);
        }


        return filename;
    }


    public static void listAllFiles(String directory) {
        if (directory == null) {
            System.out.println("parameter is null");
            return;
        }

        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("parameter is invalid");
            return;
        }

        System.out.println(dir.getAbsolutePath());
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        listAllFiles(file.getAbsolutePath());
                    } else {
                        System.out.println(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
