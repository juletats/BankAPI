package ru.sber.model;

public class Client {

    private long idClient;
    private String fio;

    public Client() {
    }

    public Client(int idClient, String fio) {
        this.idClient = idClient;
        this.fio = fio;
    }

    public Client(String fio) {
        this.fio = fio;
    }

    public Client(long idClient) {
        this.idClient = idClient;
        fio = "";
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", fio='" + fio + '\'' +
                '}';
    }
}
