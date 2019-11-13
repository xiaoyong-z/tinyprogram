package client.service.dao;

import java.io.IOException;

public interface userDao {
    int signin(String userName, String passWord);
    int insert(String userName, String passWord);
    int update(int id, String passWord);
    int delete(int id);
    Object[][] listTable();
}
