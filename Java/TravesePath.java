import java.io.File;

public class TravesePath {
    public static void main(String[] args) {
	File dir = new File("d:/HelloWorld");
	if (dir.isDirectory()) {
	    listAllFiles(dir);
	}
    }

    public static void listAllFiles(File dir) {
	if (dir == null) {
	    return;
	}

	System.out.println(dir.getAbsolutePath());
	if (dir.isDirectory()) {
	    File[] files = dir.listFiles();
	    if (files != null) {
		for (File file : files) {
		    if (file.isDirectory()) {
			listAllFiles(file);
		    } else {
			System.out.println(file.getAbsolutePath());
		    }
		}
	    }
	}
    }
}
