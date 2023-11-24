package com.yaksha.training.property.service;

import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getProperties() {
        return propertyRepository.findAll();
    }

    public Property saveProperty(Property theProperty) {
        return propertyRepository.save(theProperty);
    }

    public Property getProperty(Long propertyId) {
        return propertyRepository.findById(propertyId).get();
    }

    public boolean deleteProperty(Property property) {
        propertyRepository.delete(property);
        return true;
    }

}
