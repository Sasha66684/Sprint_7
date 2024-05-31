package ru.yandex.praktikum.model;





public class Courier {
    private String login;
    private String password;
    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static Courier from(DataCourier couriers) {
        return new Courier(couriers.getLogin(), couriers.getPassword());
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return  password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
