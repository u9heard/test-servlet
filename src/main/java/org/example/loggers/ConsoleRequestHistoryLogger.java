package org.example.loggers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.interfaces.RequestLogger;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ConsoleRequestHistoryLogger implements RequestLogger {
    private List<String> history;

    public ConsoleRequestHistoryLogger(){
        history = new LinkedList<>();
    }

    @Override
    public void log(HttpServletRequest request) throws IOException {
        StringBuilder logBuilder = new StringBuilder();
        parseMethod(request, logBuilder);
        parseURI(request, logBuilder);
        parseHeaders(request, logBuilder);
        parseParameters(request, logBuilder);
        parseBody(request, logBuilder);

        synchronized (this){
            history.add(logBuilder.toString());
            printAll();
        }
    }

    private void printAll() {
        for(String s : history){
            System.out.println(s);
        }
        System.out.println("=======" + history.size() + "=======");
    }

    protected void parseMethod(HttpServletRequest request, StringBuilder logBuilder){
        logBuilder.append("Received request: " + request.getMethod() + '\n');
    }

    protected void parseURI(HttpServletRequest request, StringBuilder logBuilder){
        logBuilder.append("on " + request.getRequestURI() + '\n');
    }

    protected void parseHeaders(HttpServletRequest request, StringBuilder logBuilder){
        logBuilder.append("Headers: " + '\n');
        Enumeration<String> headerNames = request.getHeaderNames();

        if(headerNames.hasMoreElements()) {
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                logBuilder.append(header + "=" + request.getHeader(header) + '\n');
            }
        }
    }

    protected void parseParameters(HttpServletRequest request, StringBuilder logBuilder){
        Enumeration<String> parameterNames = request.getParameterNames();

        if(parameterNames.hasMoreElements()){
            logBuilder.append("Parameters: " + '\n');
            while (parameterNames.hasMoreElements()){
                String parameterName = parameterNames.nextElement();
                logBuilder.append(parameterName + "=" + Arrays.stream(request.getParameterValues(parameterName)).toList() + '\n');
            }
        }
    }

    protected void parseBody(HttpServletRequest request, StringBuilder logBuilder){
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
    }
}
