
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private Path testFileDecrypted = Paths.get("src", "test", "resources", "testFileDecrypted.txt");
    private Path testFileEncrypted = Paths.get("src", "test", "resources", "testFileEncrypted.txt");
    private String fileName = "testFileEncrypted.txt";
    private File fileD = new File(testFileDecrypted.toString());
    private File fileE = new File(testFileEncrypted.toString());

    @Test
    void encryptionText() throws IOException {
        Main.main(new String[]{"-c 1a3b3f5c", testFileDecrypted.toString(), "-o " + fileName});
        File f = new File(fileName);
        assertTrue(FileUtils.contentEquals(f, fileE));
        assertFalse(FileUtils.contentEquals(f, fileD));
    }

    @Test
    void decryptionText() throws IOException {
        Main.main(new String[]{"-c 1a3b3f5c", testFileEncrypted.toString(), "-o " + fileName});
        File f = new File(fileName);
        assertTrue(FileUtils.contentEquals(f, fileD));
        assertFalse(FileUtils.contentEquals(f, fileE));
    }

    @Test
    void noNewNameOption() {
        Main.main(new String[]{"-c 1a3b3f5c", testFileEncrypted.toString()});
        assertTrue(new File("testFileEncrypted.txt").exists());
    }

    @Test
    void badKey() {
        assertThrows(NumberFormatException.class, () -> {
            Main.main(new String[]{"-c 13p5", testFileDecrypted.toString()});
        });
        assertThrows(NumberFormatException.class, () -> {
            Main.main(new String[]{"-c 13fdsa5", testFileDecrypted.toString()});
        });
        assertThrows(NumberFormatException.class, () -> {
            Main.main(new String[]{"-c 1d3a5ffq", testFileDecrypted.toString()});
        });
        assertThrows(NumberFormatException.class, () -> {
            Main.main(new String[]{"-c f0eeee25g5f", testFileDecrypted.toString()});
        });
    }

    @Test
    void soBigKey() {
        Main.main(new String[]{"-c ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", testFileEncrypted.toString()});

        assertTrue(new File("testFileEncrypted.txt").exists());
    }

    @AfterEach
    void deleteFinalFile() {
        File file = new File(fileName);
        file.delete();
        file = new File("testFileDecrypted.txt");
        file.delete();
        file = new File("testFileEncrypted.txt");
        file.delete();
    }
}