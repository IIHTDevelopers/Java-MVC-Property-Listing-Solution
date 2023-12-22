package com.yaksha.training.property.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yaksha.training.property.entity.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MasterData {

    public static Property getProperty() {
        Property property = new Property();
        property.setId(1L);
        property.setName(randomStringWithSize(10));
        property.setAddress(randomStringWithSize(10));
        property.setDimensions(randomStringWithSize(10));
        property.setRooms(randomInteger());
        property.setPrice(randomDouble());
        return property;
    }

    public static List<Property> getPropertyList(int size) {
        Long id = 0L;
        List<Property> properties = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Property property = new Property();
            property.setId(++id);
            property.setName(randomStringWithSize(10));
            property.setAddress(randomStringWithSize(10));
            property.setDimensions(randomStringWithSize(10));
            property.setRooms(randomInteger());
            property.setPrice(randomDouble());
            properties.add(property);
        }
        return properties;
    }


    private static Random rnd = new Random();

    public static String randomStringWithSize(int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        String s = "";
        for (int i = 0; i < size; i++) {
            s = s + (String.valueOf(alphabet.charAt(rnd.nextInt(alphabet.length()))));
        }
        return s;
    }

    public static boolean randomBoolean() {
        String alphabet = "1234567890";
        Random rnd = new Random();
        return rnd.nextInt(alphabet.length()) % 2 == 0;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            final String jsonContent = mapper.writeValueAsString(obj);

            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer randomInteger() {
        String alphabet = "1234567890";
        Random rnd = new Random();
        return rnd.nextInt(alphabet.length());
    }

    public static Double randomDouble() {
        Random rnd = new Random();
        return rnd.nextDouble();
    }
}
