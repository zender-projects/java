package com.zd.learn.java.basic.concurrencyart.chapter04.httpserver;

import com.zd.learn.java.basic.concurrencyart.chapter04.threadpool.DefaultThreadPool;
import com.zd.learn.java.basic.concurrencyart.chapter04.threadpool.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SimpleHttpServer {

    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);
    static String basePath;
    static ServerSocket serverSocket;

    static int port = 8080;

    public static void setPort(int port) {
        if(port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if(basePath != null && new File(basePath).exists() &&
                new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void start() throws Exception{
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }



    static class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;
            BufferedReader br = null;
            BufferedReader bufferedReader = null;
            PrintWriter out = null;
            InputStream inputStream = null;
            try{
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = bufferedReader.readLine();
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                if(filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    inputStream = new FileInputStream(filePath);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int i = 0;
                    while((i = inputStream.read()) != -1) {
                        outputStream.write(i);
                    }
                    byte[] array = outputStream.toByteArray();
                    out.print("HTTP/1.1 200 OK");
                    out.print("Server:Molly");
                    out.print("Content-Type:image/jpeg");
                    out.print("Content-Length:" + array.length);
                    out.print("");
                    socket.getOutputStream().write(array, 0, array.length);
                }else{
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.print("HTTP/1.1 200 OK");
                    out.print("Content-Type:text/html;charset=UTF-8");
                    out.print("");
                    while ((line = br.readLine()) != null) {
                        out.print(line);
                    }
                }
                out.flush();
            }catch (Exception ex) {
                out.print("HTTP/1.1 500");
                out.print("");
                out.flush();
            }finally {
                close(br, bufferedReader, out, socket);
            }
        }

        private void close(Closeable ... closeable) {
            if(closeable != null) {
                for (Closeable closeable1 : closeable) {
                    try{
                        //AbstractQueuedSynchronizer
                        closeable1.close();
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
