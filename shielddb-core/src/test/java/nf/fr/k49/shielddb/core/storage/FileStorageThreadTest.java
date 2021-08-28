package nf.fr.k49.shielddb.core.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileStorageThreadTest {
    

    static Path testWorkDir;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testWorkDir = Files.createTempDirectory(FileStorageTest.class.getSimpleName());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Files.walk(testWorkDir).map(Path::toFile).forEach(File::delete);
	}

    @Test
    void test() throws IOException, InterruptedException {
		String targetFilePath = getExistentFilePathForTest(StandardCharsets.UTF_8);

		var contentFake = "a".repeat(500);
		Files.write(Paths.get(targetFilePath), contentFake.getBytes(StandardCharsets.UTF_8));

		FileStorage fileStorage = new FileStorage(targetFilePath, StandardCharsets.UTF_8);

		var executor = Executors.newFixedThreadPool(2);

		var countDown = new CountDownLatch(1);

		executor.execute(() -> {
			try {
				countDown.await();
				int count = 0;
				while(true) {
					final String readContent = fileStorage.read();
					if(count == 0) {
						assertEquals(500, readContent.length(), "Streaming was broken");
					} else {
//						assertEquals(500, readContent.length(), "Streaming was broken");
					}

					System.out.println("Leu: " + readContent.length());
					Thread.sleep(100);
					count++;
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		});

		executor.execute(() -> {
			try {
				countDown.await();
				Thread.sleep(600);
				while(true) {
					var payload = "b".repeat(5000);
					fileStorage.write(payload);
					System.out.println("Escreveu: " + payload.length());
					Thread.sleep(100);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		});

		countDown.countDown();
		Thread.sleep(10000);
		executor.shutdownNow();
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


}
