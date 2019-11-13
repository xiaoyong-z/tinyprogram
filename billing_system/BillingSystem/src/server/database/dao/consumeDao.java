package server.database.dao;

import java.util.Calendar;

public interface consumeDao {
    void use(String cardNumber);
    void stop(String cardNumber, Calendar start);
    Calendar search(String cardNumber);
    Object[][] listTable(String cardNumber);
}
