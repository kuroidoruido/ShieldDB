package nf.fr.k49.shielddb.core.storage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class FileStorage implements ShieldDBStorage {

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private final Lock readLock = readWriteLock.readLock();
 
    private final Lock writeLock = readWriteLock.writeLock();

	private Path parentDir;
	private Path path;
	private Path backupPath;
	private Charset charset;

	/**
	 * Same as #FileStorage(String, Charset) with a default Charset set to
	 * StandardCharsets.UTF_8.
	 * 
	 * @param path the path of the json file
	 * @throws IOException if an error occured when reading the file.
	 */
	public FileStorage(final String path) throws IOException {
		this(path, StandardCharsets.UTF_8);

		if (!Files.exists(this.path)) {
			clear();
		}
	}

	public FileStorage(final String path, final Charset charset) throws IOException {
		checkArgument(isNotBlank(path), "Path cannot be null or empty");
		checkArgument(Objects.nonNull(charset), "Charset cannot be null");
		this.path = Paths.get(path).toAbsolutePath();
		this.parentDir = this.path.getParent();
		this.backupPath = Paths.get(path + ".backup").toAbsolutePath();
		this.charset = charset;

		if (path.contains(File.separator)) {
			this.parentDir.toFile().mkdirs();
		}
	}

	@Override
	public synchronized String read() throws IOException {
		try {
			readLock.lock();
			return Files.readAllLines(path, charset).stream().collect(Collectors.joining(""));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
		return null;
	}

	@Override
	public synchronized void write(String json) throws IOException {
		writeLock.lock();
		checkArgument(isNotBlank(json), "JSON cannot be null or empty");
		final Path tmp = Files.createTempFile(this.parentDir, "ShieldDB_", ".temp");
		Files.write(tmp, json.getBytes());
		Files.move(path, backupPath, StandardCopyOption.ATOMIC_MOVE);
		Files.move(tmp, path, StandardCopyOption.ATOMIC_MOVE);
		Files.deleteIfExists(tmp);
		Files.deleteIfExists(backupPath);
		writeLock.unlock();
	}

	@Override
	public void clear() throws IOException {
		Files.write(this.path, "".getBytes());
	}
}
