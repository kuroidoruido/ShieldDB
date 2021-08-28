package nf.fr.k49.shielddb.core.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileStorageTest {

	static Path testWorkDir;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testWorkDir = Files.createTempDirectory(FileStorageTest.class.getSimpleName());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Files.walk(testWorkDir).map(Path::toFile).forEach(File::delete);
	}


	private Path createFilePathForTest() {
		return Paths.get(testWorkDir.toString(), UUID.randomUUID().toString());
	}

	private String getExistentFilePathForTest() throws IOException {
		return Files.write(createFilePathForTest(), UUID.randomUUID().toString().getBytes(Charset.defaultCharset()))
				.toString();
	}

	private String getExistentFilePathForTest(Charset charset) throws IOException {
		return Files.write(createFilePathForTest(), UUID.randomUUID().toString().getBytes(charset)).toString();
	}

	@Test
	@Order(0)
	final void testFileStorageString() throws IOException {
		String existentFilePath = getExistentFilePathForTest();
		assertDoesNotThrow(() -> {
			new FileStorage(existentFilePath);
		}, "must be able to be instantiated when it's passed as arguments a valid and existent file path");
	}

	@Test
	@Order(1)
	final void testFileStorageStringCharset() throws IOException {
		String targetFilePath = getExistentFilePathForTest(StandardCharsets.ISO_8859_1);
		assertDoesNotThrow(() -> {
			new FileStorage(targetFilePath, StandardCharsets.ISO_8859_1);
		}, "must be able to be instantiated when it's passed as arguments a valid and existent file path and a valid charset");
	}

	@Test
	@Order(2)
	final void testRead() throws IOException {
		String targetFilePath = getExistentFilePathForTest(StandardCharsets.UTF_8);
		String expectedContent = Files.readAllLines(Paths.get(targetFilePath), StandardCharsets.UTF_8)
				.stream().collect(Collectors.joining(""));
		FileStorage fileStorage = new FileStorage(targetFilePath, StandardCharsets.UTF_8);
		String actualContent = fileStorage.read();
		assertThat("the read method does not working as expected: unexpected retrieved content", actualContent,
				equalTo(expectedContent));
	}

	@Test
	@Order(3)
	final void testWrite() throws IOException {
		String targetFilePath = getExistentFilePathForTest(StandardCharsets.UTF_8);
		FileStorage fileStorage = new FileStorage(targetFilePath, StandardCharsets.UTF_8);
		String expectedContent = UUID.randomUUID().toString();
		fileStorage.write(expectedContent);
		String actualContent = Files.readAllLines(Paths.get(targetFilePath), StandardCharsets.UTF_8).stream()
				.collect(Collectors.joining(""));
		assertThat("the write method does not working as expected: unexpected content has been wrote", expectedContent,
				equalTo(actualContent));
	}

	@Test
	@Order(4)
	final void testClear() throws IOException {
		String targetFilePath = getExistentFilePathForTest(StandardCharsets.UTF_8);
		FileStorage fileStorage = new FileStorage(targetFilePath, StandardCharsets.UTF_8);
		String expectedContent = "";
		fileStorage.clear();
		String actualContent = Files.readAllLines(Paths.get(targetFilePath), StandardCharsets.UTF_8).stream()
				.collect(Collectors.joining(""));
		assertThat("the clear method does not working as expected: the retrieved content wasn't cleaned", expectedContent,
				equalTo(actualContent));
	}

}
