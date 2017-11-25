package com.mojka.poisk.data.model;

import java.io.Serializable;
import java.util.LinkedList;

public class MapFilter implements Serializable {
    private LinkedList<ServiceType> serviceTypes = new LinkedList<>();

    public MapFilter() {
    }

    public void addService(ServiceType serviceType) {
        getServiceTypes().add(serviceType);
    }

    public void removeService(ServiceType serviceType) {
        getServiceTypes().remove(serviceType);
    }

    public LinkedList<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(LinkedList<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
}
