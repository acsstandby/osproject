package com.osyunge2.dataobject;

import java.util.List;

public class EasyUIDataGridResult {
    private List<?> rows;
    private  long total;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
