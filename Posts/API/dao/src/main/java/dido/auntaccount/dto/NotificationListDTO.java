package dido.auntaccount.dto;

import java.util.List;

public class NotificationListDTO {

    private Integer total;
    private List<NotificationDTO> rows;

    public NotificationListDTO() {

    }

    public NotificationListDTO(Integer total, List<NotificationDTO> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<NotificationDTO> getRows() {
        return rows;
    }

    public void setRows(List<NotificationDTO> rows) {
        this.rows = rows;
    }
}
