package be.pxl.business;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class PdfReader {

    private Path pdfPath;

    public PdfReader(Path pdfPath) {
        this.pdfPath = pdfPath;
    }

    public byte[] readPdfBinary() {
        byte[] pdfBinary = null;
        try {
            FileReader reader = new FileReader(pdfPath.toFile());

            pdfBinary = Files.readAllBytes(pdfPath);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return pdfBinary;
    }
}
