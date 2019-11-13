package model;

import java.io.Serializable;

public class userTable implements Serializable {
    private Object[][] userTable;

    public userTable(Object[][] userTable) {
        this.userTable = userTable;
    }

    public Object[][] getUserTable() {
        return userTable;
    }

    public void setUserTable(Object[][] userTable) {
        this.userTable = userTable;
    }
}
