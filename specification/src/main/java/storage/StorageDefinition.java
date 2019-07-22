package storage;

/**
 * Use this as specification for storage definition operations(create, delete, etc).
 */
public interface StorageDefinition {

    /**
     * Create storage on given path.
     *
     * @param path path of the storage
     */
    void createStorage(String path);

    /**
     * Delete storage on given path.
     *
     * @param path path of the storage
     */
    void deleteStorage(String path);

}
