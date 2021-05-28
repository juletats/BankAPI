package ru.sber.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sber.controller.api.ApiResponse;
import ru.sber.model.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CardHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = "";

        switch (exchange.getRequestMethod()) {
            case "GET":
                //проверка баланса по карте и по аккаунту
                String URI = exchange.getRequestURI().getQuery();
                String[] data = URI.split("=");

                System.out.println(data[0]);
                if (data[0].equals("idCard")) {
                    responseBody += Operations.checkBalanceByCard(Long.parseLong(data[1]));
                } else {
                    responseBody += Operations.checkBalanceByAccount(Long.parseLong(data[1]));
                }
                System.out.println(responseBody);

                if (!(Double.parseDouble(responseBody) == (-404))) {
                    Card card = new Card(Long.parseLong(data[1]), Double.parseDouble(responseBody));
                    responseBody = mapper.writeValueAsString(card);
                    exchange.sendResponseHeaders(200, responseBody.length());
                } else {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Card does not exist"));
                    exchange.sendResponseHeaders(400, responseBody.length());
                }
                break;
            case "POST":
                //пополнение счета
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder(512);
                int i;
                while ((i = br.read()) != -1) {
                    sb.append((char) i);
                }
                br.close();

                JsonNode requestBody = mapper.readTree(sb.toString());
                long idCard = requestBody.get("idCard").asLong();
                double balance = requestBody.get("balance").asDouble();
                boolean str = Operations.depositMoney(idCard, balance);
                if (str) {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Deposit recorded"));
                    exchange.sendResponseHeaders(200, responseBody.length());
                } else {
                    responseBody = mapper.writeValueAsString(new ApiResponse("Deposit was not recorded"));
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
