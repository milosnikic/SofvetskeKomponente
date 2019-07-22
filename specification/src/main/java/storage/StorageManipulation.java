package storage;

/**
 * Use this as specification for storage manipulation operations(move storage, etc).
 */
public interface StorageManipulation {

    /**
     * Change storage location.
     *
     * @param oldPath old storage path
     * @param newPath new storage path
     */
    void moveStorage(String oldPath, String newPath);

}
