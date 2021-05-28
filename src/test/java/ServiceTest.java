import com.sun.net.httpserver.HttpServer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sber.controller.AccountHandler;
import ru.sber.controller.CardHandler;
import ru.sber.controller.ClientHandler;
import ru.sber.util.DbUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

public class ServiceTest {

    @BeforeClass
    public static void start() throws IOException {
        DbUtil.createDB();
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 1);
        server.createContext("/ClientActions", new ClientHandler());
        server.createContext("/AccountActions", new AccountHandler());
        server.createContext("/CardActions", new CardHandler());
        server.setExecutor(null);
        server.start();
    }


    @Test
    public void testReadCardsByAccount() throws IOException {
        String url = "http://localhost:8080/AccountActions?idAccount=1000200030004000501";
        URL Url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input="";
        StringBuilder res = new StringBuilder();
        while ((input = in.readLine()) != null) {
            res.append(input + " ");
        }
        in.close();
        String result = res.toString();

        String expected = "[{\"idCard\":1234234534564567590,\"balance\":1010.0}] ";
        Assert.assertEquals(expected, result);
    }


}
