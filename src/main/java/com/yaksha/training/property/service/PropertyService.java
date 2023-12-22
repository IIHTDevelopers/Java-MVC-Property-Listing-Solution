package com.yaksha.training.property.service;

import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
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

    public Page<Property> searchProperties(String name, Double maxPrice, Pageable pageable) {
        if ((name != null && name.trim().length() > 0) ||
                (maxPrice != null )) {
            if (name != null && name.isEmpty()) {
                name = null;
            }
            return propertyRepository.findByPropertyNameAndMaxPrice(name, maxPrice, pageable);
        } else {
            return propertyRepository.findAll(pageable);
        }
    }


}
