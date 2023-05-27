package com.leon.hamrah_abfa.fragments.contact_us;

import org.osmdroid.util.GeoPoint;

public class BranchViewModel {
    private final String name;
    private final String manager;
    private final String zone;
    private final String phone1;
    private final String fax;
    private final String address;
    private final String postal;
    private final GeoPoint point;

    public BranchViewModel(String name, String manager, String zone, String phone1, String fax,
                           String address, String postal, GeoPoint point) {
        this.name = name;
        this.manager = manager;
        this.zone = zone;
        this.phone1 = phone1;
        this.fax = fax;
        this.address = address;
        this.postal = postal;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public String getZone() {
        return zone;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getFax() {
        return fax;
    }

    public String getAddress() {
        return address;
    }

    public String getPostal() {
        return postal;
    }

    public GeoPoint getPoint() {
        return point;
    }
}
