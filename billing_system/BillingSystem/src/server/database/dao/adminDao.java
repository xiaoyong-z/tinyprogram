package server.database.dao;

import model.user;

public interface adminDao {
    int insert(String username, String password);
    user login(String username);
    int delete(int id);
    int update(int id, String password);
    Object[][] listTable();
}