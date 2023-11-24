package com.yaksha.training.property.service;

import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.repository.PropertyRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.yaksha.training.property.utils.MasterData.asJsonString;
import static com.yaksha.training.property.utils.MasterData.getProperty;
import static com.yaksha.training.property.utils.MasterData.getPropertyList;
import static com.yaksha.training.property.utils.TestUtils.businessTestFile;
import static com.yaksha.training.property.utils.TestUtils.currentTest;
import static com.yaksha.training.property.utils.TestUtils.testReport;
import static com.yaksha.training.property.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

public class PropertyServiceTest {

    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testServiceGetProperties() throws Exception {
        List<Property> actual = getPropertyList(5);
        when(propertyRepository.findAll()).thenReturn(actual);
        List<Property> expected = propertyService.getProperties();
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSaveProperty() throws Exception {
        Property actual = getProperty();
        when(propertyRepository.save(actual)).thenReturn(actual);
        Property expected = propertyService.saveProperty(actual);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceGetProperty() throws Exception {
        Property actual = getProperty();
        when(propertyRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        Property expected = propertyService.getProperty(actual.getId());
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }


    @Test
    public void testServiceDeleteProperty() throws Exception {
        Property actual = getProperty();
        boolean expected = propertyService.deleteProperty(actual);
        yakshaAssert(currentTest(),
                (expected ? true : false),
                businessTestFile);
    }

}
