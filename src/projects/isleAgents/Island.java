package projects.isleAgents;

public class Island {
    private double longitude;
    private double latitude;
    private String name;

    public Island(String name, int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + "(" + latitude + "," + longitude + ")";
    }
}
