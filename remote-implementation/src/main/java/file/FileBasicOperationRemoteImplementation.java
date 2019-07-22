package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderBuilder;
import com.dropbox.core.v2.files.ListFolderContinueErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;

import util.Client;

/**
 * Implementation of {@link FileBasicOperation} interface. This implementation
 * use dropbox as storage.
 */
public class FileBasicOperationRemoteImplementation implements FileBasicOperation {

	private Client client;

	public FileBasicOperationRemoteImplementation(String accessToken, String config) {
		client = new Client(config, accessToken);
	}

	/**
	 * Upload file to dropbox.
	 *
	 * @param file
	 *            file which we want to upload
	 * @param path
	 *            path of the file on the storage
	 */
	public void uploadFile(File file, String path) {
		try {
			InputStream in = new FileInputStream(file);
			FileMetadata metadata = client.getClient().files().uploadBuilder(path).uploadAndFinish(in);
		} catch (FileNotFoundException e) {
			System.out.println("File can't be found");
			e.printStackTrace();
		} catch (UploadErrorException e) {
			System.out.println("File can't be uploaded");
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Download file from storagePath on dropbox to location specified with(path).
	 *
	 * @param path
	 *            where we want to upload file
	 * @param storagePath
	 *            where file is located
	 */
	public void downlaodFile(String path, String storagePath) {

		try {
			DbxDownloader<FileMetadata> downloader = client.getClient().files().download(storagePath);
			FileOutputStream out = new FileOutputStream(new File(path));
			downloader.download(out);
			out.close();
		} catch (DbxException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Rename existing file to newFileName
	 * 
	 * @param file
	 *            name of the file thats is being renamed
	 * @param newFileName
	 *            new name for file
	 */
	public boolean renameFile(File file, String newFileName) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Upload files to dropbox (Multiple Files)
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
	 * Create directory on dropbox
	 * 
	 * @param path
	 *            where to create directory
	 */
	public void createDirectory(String path) {
		try {
			FolderMetadata folder = client.getClient().files().createFolder(path);
			System.out.println("Folder on dropbox created!");
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Gives file preview on a given path/folder.
	 * 
	 * @return folder with name of the file
	 */
	public String filePreview(File folder) {
		String r = "";
//		try {
//			ListFolderResult result = client.getClient().files().listFolder(folder.getAbsolutePath());
//			while (true) {
//				for (Metadata metadata : result.getEntries()) {
//					System.out.println(metadata.getPathDisplay());
//					r += metadata.getPathDisplay();
//				}
//				if (!result.getHasMore()) {
//					break;
//				}
//				result = client.getClient().files().listFolderContinue(result.getCursor());
//			}
//		} catch (ListFolderContinueErrorException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DbxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return r;

	}
	/**
	 * Creates file on a given path.
	 *
	 */
	@Override
	public void createFile(String name, String path) {
		File f = new File(name);
		try {
			f.createNewFile();
			uploadFile(f, path + "/" + name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Gives directory names on a given path.
	 * 
	 * @return folder
	 *            name of the folders (in a list)
	 */
	@Override
	public List getDirectories(String folder) {
		ListFolderResult result;
		List<String> dirs = new LinkedList<>();
		
		try {
			result = client.getClient().files().listFolder(folder);
			while (true) {
				for (Metadata metadata : result.getEntries()) {
					if (metadata instanceof FolderMetadata) {
						dirs.add(metadata.getName());
					}

				}
				if (!result.getHasMore()) {
					break;
				}
				result = client.getClient().files().listFolderContinue(result.getCursor());
			}
		} catch (ListFolderErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dirs;
	}

}
