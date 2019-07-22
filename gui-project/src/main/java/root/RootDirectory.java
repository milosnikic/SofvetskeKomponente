package root;

public class RootDirectory {
	private static RootDirectory instance;
	private String path;

	private RootDirectory() {

	}

	public static RootDirectory getInstance() {
		if (instance == null) {
			instance = new RootDirectory();
		}
		return instance;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
