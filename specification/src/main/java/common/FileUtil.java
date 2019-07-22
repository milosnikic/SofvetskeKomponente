package common;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Contains some helper methods for working with files.
 */
public final class FileUtil {

	private FileUtil() {

	}

	/**
	 * Method generates archive file. Different extensions supported (zip,rar)
	 * 
	 * @param archivePath
	 *            where to storage archive
	 * @param files
	 *            list of files you want in archive
	 */
	public static void zip(String archivePath, File... files) {
		try {
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(archivePath));
			for (File fileToZip : files) {
				zipOut.putNextEntry(new ZipEntry(fileToZip.getName()));
				Files.copy(fileToZip.toPath(), zipOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
