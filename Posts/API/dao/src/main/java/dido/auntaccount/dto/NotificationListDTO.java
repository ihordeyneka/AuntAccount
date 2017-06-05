package dido.auntaccount.dto;

import java.util.List;

public class NotificationListDTO {

    private long total;
    private List<NotificationDTO> rows;

    public NotificationListDTO() {

    }

    public NotificationListDTO(long total, List<NotificationDTO> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<NotificationDTO> getRows() {
        return rows;
    }

    public void setRows(List<NotificationDTO> rows) {
        this.rows = rows;
    }
}
