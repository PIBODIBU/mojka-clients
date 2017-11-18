package com.mojka.poisk.data.model;

import java.io.Serializable;
import java.util.LinkedList;

public class MapFilter implements Serializable {
    private LinkedList<Service> services = new LinkedList<>();

    public MapFilter() {
    }

    public void addService(Service service) {
        getServices().add(service);
    }

    public void removeService(Service service) {
        getServices().remove(service);
    }

    public LinkedList<Service> getServices() {
        return services;
    }

    public void setServices(LinkedList<Service> services) {
        this.services = services;
    }
}
