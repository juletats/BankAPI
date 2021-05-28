package ru.sber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.controller.api.ApiResponse;
import ru.sber.model.Card;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ClientHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody;
        // вывод карт по номеру клиента
        switch (exchange.getRequestMethod()) {
            case "GET":
                String URI = exchange.getRequestURI().getQuery();
                String[] data = URI.split("=");

                List<Card> result = Operations.readCardsByClient(Long.parseLong(data[1]));
//            String res="";
//            for (Card card:
//                    result) {
//                res+=card.getIdCard()+" ";
//                System.out.println(res);
//            }
//            String[] strings = res.split(" ");
                //здесь нужен json'чик
//            responseBody = mapper.writeValueAsString(strings);
                // responseBody = mapper.writeValueAsString(new ApiResponse(res));
//            exchange.sendResponseHeaders(200, responseBody.length());
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                System.out.println((result.toString()));
                mapper.writeValue(out, result);
                final byte[] list = out.toByteArray();
                responseBody = new String(list);
                exchange.sendResponseHeaders(200, responseBody.length());
                break;
            case "POST":
                //выпуск карты по фио
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder(512);
                int i;
                while ((i = br.read()) != -1) {
                    sb.append((char) i);
                }
                br.close();
//сразу передавать в маппер
                if (Operations.addNewCardByClient(sb.toString())) {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Card added"));
                    exchange.sendResponseHeaders(200, responseBody.length());
                } else {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Card was not added"));
                    exchange.sendResponseHeaders(400, responseBody.length());
                }
                break;
            default:
                responseBody = mapper.writeValueAsString(new ApiResponse("Unsupported method"));
                exchange.sendResponseHeaders(404, responseBody.length());
        }
        OutputStream os = exchange.getResponseBody();
        os.write(responseBody.getBytes());
        os.close();
    }
}
