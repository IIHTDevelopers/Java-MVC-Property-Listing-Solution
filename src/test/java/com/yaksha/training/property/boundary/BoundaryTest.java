package com.yaksha.training.property.boundary;


import com.yaksha.training.property.entity.Property;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.yaksha.training.property.utils.MasterData.getProperty;
import static com.yaksha.training.property.utils.MasterData.randomStringWithSize;
import static com.yaksha.training.property.utils.TestUtils.boundaryTestFile;
import static com.yaksha.training.property.utils.TestUtils.currentTest;
import static com.yaksha.training.property.utils.TestUtils.testReport;
import static com.yaksha.training.property.utils.TestUtils.yakshaAssert;

@ExtendWith(SpringExtension.class)
public class BoundaryTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void afterAll() {
        testReport();
    }

    @Test
    public void testPropertyNameNotBlank() throws Exception {
        Property property = getProperty();
        property.setName("");
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyNameNotNull() throws Exception {
        Property property = getProperty();
        property.setName(null);
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyNameMinThree() throws Exception {
        Property property = getProperty();
        property.setName(randomStringWithSize(2));
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyNameMaxTwenty() throws Exception {
        Property property = getProperty();
        property.setName(randomStringWithSize(21));
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyAddressNotBlank() throws Exception {
        Property property = getProperty();
        property.setAddress("");
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyAddressNotNull() throws Exception {
        Property property = getProperty();
        property.setAddress(null);
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyAddressMinThree() throws Exception {
        Property property = getProperty();
        property.setAddress(randomStringWithSize(2));
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyAddressMaxTwoHundred() throws Exception {
        Property property = getProperty();
        property.setAddress(randomStringWithSize(201));
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyDimensionNotBlank() throws Exception {
        Property property = getProperty();
        property.setDimensions("");
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyDimensionNotNull() throws Exception {
        Property property = getProperty();
        property.setDimensions(null);
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyDimensionMinThree() throws Exception {
        Property property = getProperty();
        property.setDimensions(randomStringWithSize(2));
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyDimensionMaxTen() throws Exception {
        Property property = getProperty();
        property.setDimensions(randomStringWithSize(11));
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyRoomNotNull() throws Exception {
        Property property = getProperty();
        property.setRooms(null);
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testPropertyPriceNotNull() throws Exception {
        Property property = getProperty();
        property.setPrice(null);
        Set<ConstraintViolation<Property>> violations = validator.validate(property);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

}
