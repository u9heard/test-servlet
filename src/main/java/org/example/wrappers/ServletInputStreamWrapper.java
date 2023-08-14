package org.example.wrappers;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ServletInputStreamWrapper extends ServletInputStream {
    private ReadListener readListener;
    private ByteArrayInputStream dataStream;

    public ServletInputStreamWrapper(byte[] data) {
        this.dataStream = new ByteArrayInputStream(data);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
        if (isReady()) {
            try {
                readListener.onDataAvailable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int read() throws IOException {
        return dataStream.read();
    }
}
