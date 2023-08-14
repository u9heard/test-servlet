package org.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.example.interfaces.RequestLogger;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class ConsoleRequestLogger implements RequestLogger {
    String method;

    HttpServletRequest request;

    ConsoleRequestLogger(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public void print() throws IOException {
        StringBuilder logBuilder = new StringBuilder();

        logBuilder.append("Received request: " + request.getMethod() + '\n');
        logBuilder.append("on " + request.getRequestURI() + '\n');

        logBuilder.append("Headers: " + '\n');
        Enumeration<String> headerNames = request.getHeaderNames();

        if(headerNames.hasMoreElements()) {
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                logBuilder.append(header + "=" + request.getHeader(header) + '\n');
            }
        }

        Enumeration<String> parameterNames = request.getParameterNames();

        if(parameterNames.hasMoreElements()){
            logBuilder.append("Parameters: " + '\n');
            while (parameterNames.hasMoreElements()){
                String parameterName = parameterNames.nextElement();
                logBuilder.append(parameterName + "=" + Arrays.stream(request.getParameterValues(parameterName)).toList() + '\n');
            }
        }

        if(request.getContentLength() != -1) {
            logBuilder.append("Data: " + '\n');
            StringBuilder requestBody = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logBuilder.append(requestBody.toString() + '\n');
        }

        System.out.println(logBuilder);
    }
}
