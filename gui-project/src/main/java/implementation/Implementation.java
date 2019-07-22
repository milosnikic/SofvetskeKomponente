package implementation;

import file.FileBasicOperation;

public class Implementation {
	private static Implementation instance;
	private FileBasicOperation implementation;

	private Implementation() {

	}

	public static Implementation getInstance() {
		if (instance == null) {
			instance = new Implementation();
		}
		return instance;
	}

	public void setImplementation(FileBasicOperation implementation) {
		this.implementation = implementation;
	}

	public FileBasicOperation getImplementation() {
		return implementation;
	}

}
