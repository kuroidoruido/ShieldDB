package nf.fr.k49.shielddb.core.storage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

public class FileStorage implements ShieldDBStorage {

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
		this.path = Paths.get(path).toAbsolutePath();
		this.parentDir = this.path.getParent();
		this.backupPath = Paths.get(path + ".backup").toAbsolutePath();
		this.charset = charset;

		if (path.contains("/") || path.contains("\\")) {
			this.parentDir.toFile().mkdirs();
		}
	}

	@Override
	public synchronized String read() throws IOException {
		return Files.readAllLines(path, charset).stream().collect(Collectors.joining(""));
	}

	@Override
	public synchronized void write(String json) throws IOException {
		final Path tmp = Files.createTempFile(this.parentDir, "ShieldDB_", ".temp");
		Files.write(tmp, json.getBytes());
		Files.move(path, backupPath, StandardCopyOption.ATOMIC_MOVE);
		Files.move(tmp, path, StandardCopyOption.ATOMIC_MOVE);
		Files.deleteIfExists(tmp);
		Files.deleteIfExists(backupPath);
	}

	@Override
	public void clear() throws IOException {
		Files.write(this.path, "".getBytes());
	}
}
