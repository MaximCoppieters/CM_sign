package be.pxl.data.model;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * This class encapsulates PDF files to be signed by invitees.
 * PdfFiles can be created from the File System (Path) or from memory (InputStream)
 * This way the files don't have to be saved locally
 */
public class PdfFile {
    private String fileName;
    private InputStream pdfBinaryStream;

    private Path pdfFilePath;

    /**
     * Use this constructor if the PDF file is not saved on the file system
     */
    public PdfFile(String fileName, InputStream pdfBinaryStream) {
        this.fileName = fileName;
        this.pdfBinaryStream = pdfBinaryStream;
    }

    /**
     * Use this constructor if the PDF file is saved on the file system
    */
    public PdfFile(Path pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
        this.fileName = pdfFilePath.getFileName().toString();
    }


    public String getFileName() {
        return fileName;
    }

    public boolean isLocalFile() {
        return pdfFilePath != null;
    }

    public InputStream getPdfBinaryStream() {
        return pdfBinaryStream;
    }

    public Path getPdfFilePath() {
        return pdfFilePath;
    }
}
