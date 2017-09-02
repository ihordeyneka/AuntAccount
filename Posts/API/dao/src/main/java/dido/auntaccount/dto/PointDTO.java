package dido.auntaccount.dto;

public class PointDTO {

    private double lat;
    private double lon;

    public PointDTO() {
    }

    public PointDTO(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
