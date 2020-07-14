
import com.valaubr.Main;
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
        Main.main(new String[]{"-c 0d6df3c84b9f2610c96caa888e08294cb87be001bdd96e921767f5fef7e832358e2efe251b2cf3f2b3" +
                "35e9a95dfd4277a2d670931d6f2668020a74ffd97fa417aa0c612adf80b16a4c7b57b5943aebd82ba2f96b84795282ae5819" +
                "f97e61376d012d9ec34ad8fd2bdadefe9ed650c606d0e1be76b0553710b9c38e5fab855cc8da82bf6b5eb9fc37dd74ed1c99" +
                "742858c5bb1b5118abea6883e747d47af802ed8febfd28eaa8a4ccdab041eab6e295d7a6c759a9ccf5b09c9af6cfd9a88634" +
                "0e8438e6d67428683a0cf846156c4a36fd3b842af6643e480f8f4dcaff02b9f66abfa7e6e35e9d8f1c827df9dd303641b92d" +
                "f3f3c5fad7414e34c7068b0bbc32fdaddc24ea10b9cb6439b76ad3c0e25dba807472e9a3ac3b048968bde032941c35c7aca9" +
                "3fd6ab3f55addf59af132b2b9fa86bea59a880fd7668144b9c73cda157315ef2bf97a8c4dd693477f3ff2181b2db7ea354af" +
                "c82d17e581e5e0211e207d2bb9e269040a59b2860b2b21ebc3379122a628b94312e675ed48ef11da95d3d1129a7afac775f4" +
                "455620f684f921a791418c5b56452dff4b5880d86f380a7fb09e305383b84e9c1b08bdfc7b7392e8ecc738466f636ddfcd72" +
                "0b559dd0014a3e05cf22d86d2c2c36fff169c2a9cbe3a0df157c0dae68ff5889d86c315df2f69199c79bc9cad4838d35c3a7" +
                "47dba3ddd1b74d0122ca362df61ab33369c908ca88c1f56d5a612d377c872a118485d4997799f182a889988bcd15d2641cb4" +
                "f299b0db7d0f5a6925249fc5df473fba9a05a07089928e33a73446f2612d9c7ee3985e0b3c29f7e10d316e5574d1e55e8580" +
                "1f0360d23b6774c0c0570da5e78e8a56ab45f5d0c251a1d7d1d65d1d895a9b777d9fe7088104efe011ffc5ae002ccc74ca82" +
                "f9c5299c2abb056a33655571bfc1d31bf8460896c0e1eecbf9880b4b68f089b0034d959e8e689f4f301182b675810d21636a" +
                "f1ebf8ac4037d26576fd8eee7fff61a0addb1053a94ba2a75ca32c6d9d8bca8bbdab6f4bd0eb892f16801b6004e7d28248ed" +
                "497110e41923bd7321d999174afb06e4287d556b8255b710294c867c6ec974b409bd464802ec2cb7dd3b4869ceed47e286e2" +
                "27e72f735e8cf386760b0f4d78bb6f8fa52f979938df5232863967e2b7020cb59b3d0e841b0394cfe4b8b5076ebf76df1c62" +
                "62a3deb0e76535d89e2513e3347e474c4a06f8646556ad62874e8b133462af099733c76b7c91c44b21df0e7ec21fbe94c1ae" +
                "eb8fe59debc051cf2edba48d781e3783025c82e4df32c00d79853d2d6493658ea90bc8719cff10953781d573af3476a496e9" +
                "aa94e3d1aea73193254010d717d1708d2525c824bf2f823155319cce437cf8abc0d9e5971fd537e9b5393c5963124db7c7f8" +
                "77ea9c410108d1df80f0a8e89452d4eaa526b99fc5eb1451e048f77d45631d71d53d20dcaeda6943dd5262765140982e2a62" +
                "9462e6208b9e90655533a0775aa79aeec13d283abd8c148c1512600a0247e5741563152fdd94cbd0b914855f44e6d9de5cd8" +
                "5e3cbe9dce125e8c30a6a7ca1d7af4a9ac27d990e50e1d869ed6ea51ad7bd727e20ca8892a9665d751df36caa515126f43fb" +
                "39e5ed0a4a17b8a81b6a16b76ee7fb0995bfd8b9e4cb1598a19c46e6bf4ea3813c18e78f68a8b82456c1b51274ed8db85baa" +
                "d7cb5afc39965b77abc6953dd551b9b883c123d516ba9624c5628fe2bcc17abb36cfabe71a0212bae6b28925509c692a34b6" +
                "feeb52fe5120b63ec1bcd9bac06ae6745b70a47f9f620b6871a094e14003d2556e8deb582bd5f67851b37a34bef4641fed8a" +
                "2394a2a8d3bea57e28b252ed901e21002e677e8ecfce629cbc76d7d53ad2a588dd803e98f6dd40ce3dd8665fe9b6d069e231" +
                "cdc21eee71e6f1076fcd0cab1c308dd0f8334458e71607682bf4a8caefb4df8c35ce77788556fe6bd759bb730086419f6aab" +
                "4c5175ca38736a3daa580cdb016d5b56517f18e026a12cea160a30234483e835810cd70cfb6a154f18ca8075fffbf8074d90" +
                "ba2b96d1d236cb8bad81ff30c9f768271f699793d0007bf906d277d57af83c70ef3fde6d9463e7fb97e62da10b089e26bd0b" +
                "44f89604782ce98d4dae18ed632a1e9efe90cdf8a18ca88379a0d1d7bf0299207d23637130fe60ed08c7bcf6ada9a58d1ef4" +
                "9df2cf08bc0b7246b8e91302e82dd287a204ee582663c5270765622f916c5ab150e62a140a2c7e068ae58e83a65b843b0101" +
                "9fd0df5916dbed6610733ff903c05eee87e7f22a75de9efe91f46e2f42d54e5783c840dd2622fc8083816ea28983a3b12fb5" +
                "f6200d460764d0b31bcceba93b9dc95b6ba932f61cc35eebb5b52fc1319ee33eacb15da052cc170f8e9ee64d1f5a2052a29c" +
                "39931bfc490f24bcda309f747643971f0bdcb7b39648b9b1d5ca6ae2e4cace5828be78a7b17cc6205bf38e13f98862ec52eb" +
                "dbb31114b86db8f3510c35f63c8e3db5b77f741da4b286beaff5d2c54dcebe9c6341ec1f494c2cc568867033da11944deb63" +
                "5e51869299cec9b0493e5bb33aaf7467acaa4c81d82dd05a0b8736ec15a6891c95cb7139836b76cdd86a8399a93efa7dfae3" +
                "8909c9894075a001a23e2dc0d9d57deff6e0d4fdd3cb98274ae642413e90013730d6b78eabf788e8cfbfe52efec0b1068a78" +
                "19a47c2a72373895cb91128876beeef0c634e1167bdab33423c368c279e91baa4dd3fe9083ab5314c16458a3c950ed01035b" +
                "1233d1317911fbd69565335a6a56d6d358c6f072f9ef92dbb7f218b3e6a0ff70fe8d5230e1fbdabdcbb76484e336c723e4b9" +
                "94ff936f79f87f32e236e8393cd83ab6d594b5346de42fd4121f8d90ce0de87367fb8ad9c1e0bee991b9e7adf577655c7fd5" +
                "121e130193e4accf0455e84e8c5ee4a5fb252147da2f781e11f9be5470e2676199f6a49bb3373fe2b81258b7aac4c62f9615" +
                "d9fc9a8d01892f49b8138d387726f608bceacdfd2b803ce87a5685f0f34a59460851eecea6118566b2470f0641ed3d3e1f53" +
                "94bb049713ab87a9f40737c0d0d838bd0411d2ba9983bfaebd3f8bf8241294388ba0baf0efde911c1781eb019fe4bc305e37" +
                "19bd28c52d0db1f66369de13239b5b4c4671ee0f86e76007390c761d4613a1648d9d22d7e58548302340cafef4d20a4789fb" +
                "6afc2e1697445f926396d47b7542fc076eb91f074a3fa5cfc7b374b1ee8c213d4d4d85971e88b5d0077d3a1097893141dbf4", testFileEncrypted.toString()});

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