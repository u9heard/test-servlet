package org.example.interfaces;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface RequestLogger {
    void log(HttpServletRequest req) throws IOException;

}
