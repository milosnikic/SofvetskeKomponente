package file;

import java.io.File;
import java.util.List;

/**
 * Basic operations with file.
 */
public interface FileBasicOperation {

	/**
	 * Upload file in storage on given path.
	 *
	 * @param file
	 *            file which we want to upload
	 * @param path
	 *            path of the file on the storage
	 */
	void uploadFile(File file, String path);

	/**
	 * Download file from storagePath to location specified with(path).
	 *
	 * @param path
	 *            where we want to download file
	 * @param storagePath
	 *            where file is located
	 */
	void downlaodFile(String path, String storagePath);

	/**
	 * 
	 * Renaming existing file to newFileName
	 * 
	 * @param file
	 *            that needs to be renamed
	 * @param newFileName
	 *            new file name
	 * @return true if file name is changed, false otherwise
	 */
	boolean renameFile(File file, String newFileName);

	/**
	 * Upload files in storage on given path.
	 * 
	 * @param path
	 * @param files
	 */
	void uploadFiles(String path, File... files);

	/**
	 * Create directory in storage on given path.
	 * 
	 * @param path
	 */
	void createDirectory(String path);

	/**
	 * Create empty file on given path
	 * 
	 * @param name
	 *            name of file
	 * @param path
	 *            file path
	 */
	void createFile(String name, String path);

	/**
	 * Display all files in given folder.
	 * 
	 * @param folder
	 */
	String filePreview(File folder);

	/**
	 * Method that returns list of directories
	 * 
	 * @param folderPath
	 *            path to folder
	 * @return list of files/folders
	 */
	List getDirectories(String folderPath);
}
