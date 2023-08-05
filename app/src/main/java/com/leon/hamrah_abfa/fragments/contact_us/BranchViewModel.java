package com.leon.hamrah_abfa.fragments.contact_us;

import org.osmdroid.util.GeoPoint;

public class BranchViewModel {
    private final String zoneTitle;
    private final String economicalCode;
    private final String phone;
    private final String fax;
    private final String zone;
    private final String address;
    private final String postalCode;
    private String x;
    private String y;
    private final GeoPoint point;
    private final String manager;

    public BranchViewModel(String zoneTitle, String manager, String zone, String economicalCode,
                           String phone, String fax, String address, String postalCode, GeoPoint point) {
        this.zoneTitle = zoneTitle;
        this.manager = manager;
        this.zone = zone;
        this.economicalCode = economicalCode;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.postalCode = postalCode;
        this.point = point;
    }

    public String getZoneTitle() {
        return zoneTitle;
    }

    public String getManager() {
        return manager;
    }

    public String getZone() {
        return zone;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public GeoPoint getPoint() {
        return point;
    }

    public String getEconomicalCode() {
        return economicalCode;
    }
}
