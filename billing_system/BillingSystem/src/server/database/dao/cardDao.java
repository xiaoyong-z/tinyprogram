package server.database.dao;

import model.card;

import java.util.Calendar;

public interface cardDao {
    int insert(card card);

    card search(String cardNumber);
    void searchShow(String cardNumber);

    int useUpdate(String cardNumber);
    int stopUpdate(String cardNumber, double balance, Calendar end, double cost, Calendar start);

    int topUp(String cardNumber, double cardAmount);
    int drawBack(String cardNumber);

    int cancel(String cardNumber);
    Object[][] listTable();
}
