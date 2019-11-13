package model;

import java.io.Serializable;

public class consumeTable implements Serializable {
    private Object[][] consumeTable;

    public consumeTable(Object[][] consumeTable) {
        this.consumeTable = consumeTable;
    }

    public Object[][] getCardTable() {
        return consumeTable;
    }

    public void setCardTable(Object[][] consumeTable) {
        this.consumeTable = consumeTable;
    }
}
