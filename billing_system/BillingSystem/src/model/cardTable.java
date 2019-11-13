package model;

import java.io.Serializable;

public class cardTable implements Serializable {
    private Object[][] cardTable;

    public cardTable(Object[][] userTable) {
        this.cardTable = userTable;
    }

    public Object[][] getCardTable() {
        return cardTable;
    }

    public void setCardTable(Object[][] cardTable) {
        this.cardTable = cardTable;
    }
}
