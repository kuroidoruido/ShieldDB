package nf.fr.k49.shielddb.core.storage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileStorageInstantiationTest {

    static Path workDirectoryForTests;

    @BeforeClass
    public static void beforeAll() throws Exception {
        workDirectoryForTests = Files.createTempDirectory(FileStorage.class.getSimpleName());
    }

    @AfterClass
    public static void afterAll() throws Exception {
        Files.walk(workDirectoryForTests).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }

    @Test
    public void givenAnInexistentFilepath_test() throws IOException {

        final Path databaseFilePath = Paths.get(workDirectoryForTests.toString(), UUID.randomUUID().toString());

        new FileStorage(databaseFilePath.toString());

        assertThat("the given file path wasn't created by the implementation as expected",
                Files.exists(databaseFilePath), equalTo(true));

    }

    @Test
    public void givenAnExistentFilepath_test() throws IOException, InterruptedException {

        final Path databaseFilePath = Paths.get(workDirectoryForTests.toString(), UUID.randomUUID().toString());

        Path createdFile = Files.write(databaseFilePath, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

        long lastedChange = createdFile.toFile().lastModified();

        new FileStorage(databaseFilePath.toString());

        Thread.sleep(100);

        assertThat("the given file path wasn't created by the implementation as expected",
                Files.exists(databaseFilePath), equalTo(true));

        assertThat("cannot replace ingfo insite the ", databaseFilePath.toFile().lastModified(), equalTo(lastedChange));
    }

    @Test
    public void givenAnISO_8859_1ExistentFilepath_test() throws IOException, InterruptedException {

        final Path databaseFilePath = Paths.get(workDirectoryForTests.toString(), UUID.randomUUID().toString());

        byte[] rawData = UUID.randomUUID().toString().getBytes(StandardCharsets.ISO_8859_1);


        Path createdFile = Files.write(databaseFilePath, rawData);

        long lastedChange = createdFile.toFile().lastModified();

        new FileStorage(databaseFilePath.toString(),StandardCharsets.ISO_8859_1);

        Thread.sleep(100);

        assertThat("the given file path wasn't created by the implementation as expected",
                Files.exists(databaseFilePath), equalTo(true));

        assertThat("cannot replace ingfo insite the ", databaseFilePath.toFile().lastModified(), equalTo(lastedChange));
    }

}