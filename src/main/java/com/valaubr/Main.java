
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.math.BigInteger;

/**
 *
 * @author valaubr
 */
public class Main {

    @Option(name = "-c", forbids = {"-d"}, metaVar = "encryption", usage = "Encryption key")
    private String encrypt;

    @Option(name = "-d", forbids = {"-c"}, metaVar = "decryption", usage = "Decryption key")
    private String decrypt;

    @Argument(index = 0, required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    @Option(name = "-o", required = false, metaVar = "OutputName", usage = "Output file name")
    private String outputFileName;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().init(args);
    }

    private void init(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            this.run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    private void run() {
        //Кастыль из за условия с encrypt - decrypt в разные опции...
        if (encrypt == null){
            encrypt = decrypt;
        }

        encrypt = encrypt.replaceAll("(-c|-d)", "").strip();

        BigInteger key = new BigInteger(encrypt, 16);

        try {
            encryptDecrypt(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void encryptDecrypt(BigInteger key) throws IOException {
        File incFile = new File(inputFileName);
        if (outputFileName == null){
            outputFileName = incFile.getName();
        }
        outputFileName = outputFileName.replaceFirst("-o", "").strip();
        File outFile = new File(outputFileName);
        // Сразу скажу, почему так: будет несколько проблематично хранить
        // в ОЗУ текстовый файл и разбивать его по символам (Да да, первый сем меня этому научил :D)
        // К сожалению, с тем что я испольхую варианты только "Убрать всё и написать вот это"
        // (в случае если мы весь текст зашифровали в оперативе и всё выгружаем назад)
        // либо дописывать в конец (Будет наглядное пособие как создать
        // файл огромных размеров с помощью алгоритма шифрования, не защитив при этом файл :D)
        boolean check = outFile.createNewFile();

        FileInputStream isr = new FileInputStream(incFile.getAbsolutePath());
        FileOutputStream osr = new FileOutputStream(outFile.getPath());
        if (!check){
            System.out.println("Файл не возможно переписать.");
            System.exit(-1);
        }

        while (isr.available() > 0){
            osr.write(isr.read() ^ key.byteValue());
        }
        isr.close();
        osr.close();
        System.out.println("Операция завершена успешно.");
    }
}