package ru.sber;

import com.sun.net.httpserver.HttpServer;
import ru.sber.controller.AccountHandler;
import ru.sber.controller.CardHandler;
import ru.sber.controller.ClientHandler;
import ru.sber.util.DbUtil;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public void run() throws IOException {
        DbUtil.createDB();
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8081), 1);
        server.createContext("/client/actions", new ClientHandler());
        server.createContext("/account/actions", new AccountHandler());
        server.createContext("/card/actions", new CardHandler());
        server.setExecutor(null);
        server.start();
    }
}
