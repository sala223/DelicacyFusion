package rps.dev.web;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ReaderWriterChannel {
    private final Reader reader;

    public ReaderWriterChannel(Reader r) {
        this.reader = r;
    }

    public void write(Writer w) throws IOException {
        char[] buffer = new char[512];
        int bufferCount = 0;
        while ((bufferCount = this.reader.read(buffer)) > 0) {
            w.write(buffer, 0, bufferCount);
        }
    }
}
