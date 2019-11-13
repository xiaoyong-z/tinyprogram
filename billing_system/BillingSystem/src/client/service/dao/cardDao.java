package client.service.dao;

public interface cardDao {
    Object[][] listTable();
    int addCard(String userName, String passWord, double balance);
    int use(String cardNumber);
    int stop(String cardNumber);
    int topUp(String cardNumber, double cardtopUpAmount);
    int drawBack(String cardNumber);
    int cancel(String cardNumber);
}
