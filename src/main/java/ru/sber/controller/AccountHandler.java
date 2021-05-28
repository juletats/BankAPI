package ru.sber.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.controller.api.ApiResponse;
import ru.sber.model.Card;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AccountHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody;

        switch (exchange.getRequestMethod()) {
            case "GET":
                //просмотр списка карт по айди акк
                String URI = exchange.getRequestURI().getQuery();
                String[] data = URI.split("=");
                String res = "";
                List<Card> result = Operations.readCardsByAccount(Long.parseLong(data[1]));
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                mapper.writeValue(out, result);
                final byte[] list = out.toByteArray();
                responseBody = new String(list);
                exchange.sendResponseHeaders(200, responseBody.length());
                break;
            case "POST":
                //выпуск карты по айди
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder(512);
                int i;
                while ((i = br.read()) != -1) {
                    sb.append((char) i);
                }
                br.close();
                final JsonNode tree = mapper.readTree(sb.toString());
                if (Operations.addNewCardByAccount(tree.get("idAccount").asLong())) {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Card added"));
                    exchange.sendResponseHeaders(200, responseBody.length());
                } else {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Card was not added"));
                    exchange.sendResponseHeaders(404, responseBody.length());
                }
                break;
            default:
                responseBody = mapper.writeValueAsString(new ApiResponse("Unsupported method"));
                exchange.sendResponseHeaders(400, responseBody.length());
        }
        OutputStream os = exchange.getResponseBody();
        os.write(responseBody.getBytes());
        os.close();
    }
}