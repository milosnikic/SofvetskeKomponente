package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
/**
 * Implementation of {@link FileBasicOperation} interface. This implementation
 * uses your PC as storage.
 */
public class FileBasicOperationLocalImplementation implements FileBasicOperation {
	private String str = "";
	/**
	 * Upload file to your PC .
	 *
	 * @param file
	 *            file which we want to upload
	 * @param path
	 *            path of the file on the storage
	 */
	public void uploadFile(File file, String path) {
		File f = new File(path);
		if (f.mkdir()) {
			// Directory created
			System.out.println("Directory created");
		} else {
			// Directory exists
			System.out.println("Directory already exists");
		}
		f = new File(path, file.getName());
		try {
			if (f.createNewFile()) {
				// File created
				System.out.println("File is created");
			} else {
				// File exists
				System.out.println("File already exists");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Files.copy(Paths.get(file.getPath()), Paths.get(path, file.getName()), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Copy done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Download file from storagePath on PC to location specified with(path).
	 *
	 * @param path
	 *            where we want to upload file
	 * @param storagePath
	 *            where file is located
	 */
	public void downlaodFile(String path, String storagePath) {
		File f = new File(path);
		if (f.mkdir()) {
			// Directory created
			System.out.println("Directory created");
		} else {
			// Directory exists
			System.out.println("Directory already exists");
		}
		File temp = new File(storagePath);
		f = new File(path, temp.getName());
		try {
			if (f.createNewFile()) {
				// File created
				System.out.println("File is created");
			} else {
				// File exists
				System.out.println("File already exists");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Files.copy(Paths.get(storagePath), Paths.get(path, temp.getName()), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Copy done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Rename existing file to newFileName (on your PC)
	 * 
	 * @param file
	 *            name of the file thats is being renamed
	 * @param newFileName
	 *            new name for file
	 */
	public boolean renameFile(File file, String newFileName) {
		String path = file.getParent();
		String ext = FilenameUtils.getExtension(file.getName());
		newFileName = newFileName + "." + ext;
		String newPath = Paths.get(path, newFileName).toString();
		System.out.println(newPath);
		return file.renameTo(new File(newPath));
	}
	/**
	 * Upload files to on your PC (Multiple Files)
	 * 
	 * @param path
	 *            where to upload file
	 * @param files
	 *            files to upload
	 */
	public void uploadFiles(String path, File... files) {
		for (int i = 0; i < files.length; i++) {
			uploadFile(files[i], path);
		}
	}
	/**
	 * Create directory on your PC
	 * 
	 * @param path
	 *            where to create directory
	 */
	public void createDirectory(String path) {
		File f = new File(path);
		if (f.mkdir()) {
			JOptionPane.showMessageDialog(null, "Directory created successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Directory already exists");
		}
	}
	/**
	 * Gives file preview on a given path.
	 * 
	 * @return folder 
	 * name of the files in a folder (in a list)
	 */
	public String filePreview(File folder) {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				str += fileEntry.getName() + "\n";
				filePreview(fileEntry);
			} else {
				str += "\t" + fileEntry.getName() + "\n";
			}
		}
		return str;
	}

	/**
	 * Creates file on a certain path
	 * 
	 * return path where it created a file, name of the created file
	 *
	 */
	@Override
	public void createFile(String name, String path) {
		File f = new File(name);
		try {
			f.createNewFile();
			uploadFile(f, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Gives list names of directories
	 * 
	 * @return folder 
	 * name of the files in a folder (in a list)
	 */
	@Override
	public List getDirectories(String folder) {
		List<String> folders = new LinkedList<>();
		getFolders(folder, folders);
		return folders;

	}
	/**
	 * Gives list names of folders
	 * 
	 * @return list of folders
	 * folder is name of the folders
	 */
	private void getFolders(String folder, List<String> folders) {
		File directory = new File(folder);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isDirectory()) {
				folders.add(file.getName());
				getFolders(file.getAbsolutePath(), folders);
			}
		}

	}

}
