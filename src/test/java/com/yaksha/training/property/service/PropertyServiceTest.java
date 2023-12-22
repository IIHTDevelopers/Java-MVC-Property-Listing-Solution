package com.yaksha.training.property.service;

import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.repository.PropertyRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Test
    public void testServiceSearchPropertyWithNullKeys() throws Exception {
        List<Property> properties = getPropertyList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Property> expected = new PageImpl<>(properties);
        when(propertyRepository.findAll(pageable)).thenReturn(expected);
        Page<Property> actual = propertyService.searchProperties(null, null, pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual.getContent()))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSearchPropertyWithKeys() throws Exception {
        List<Property> properties = getPropertyList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Property property = properties.stream().findFirst().get();
        List<Property> filteredList = new ArrayList<>();
        filteredList.add(property);
        Page<Property> expected = new PageImpl<>(filteredList);
        when(propertyRepository.findByPropertyNameAndMaxPrice(property.getName(), property.getPrice(), pageable)).thenReturn(expected);
        Page<Property> actual = propertyService.searchProperties(property.getName(), property.getPrice(), pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual.getContent()))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSearchPropertyWithSortByName() throws Exception {
        List<Property> properties = getPropertyList(5);
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Property> expected = new PageImpl<>(properties.stream().sorted(Comparator.comparing(Property::getName))
                .collect(Collectors.toList()));
        when(propertyRepository.findAll(pageable)).thenReturn(expected);
        Page<Property> actual = propertyService.searchProperties(null, null, pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual.getContent()))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testServiceSearchPropertyWithSortByPrice() throws Exception {
        List<Property> properties = getPropertyList(5);
        Sort sort = Sort.by("price");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Property> expected = new PageImpl<>(properties.stream().sorted(Comparator.comparing(Property::getName))
                .collect(Collectors.toList()));
        when(propertyRepository.findAll(pageable)).thenReturn(expected);
        Page<Property> actual = propertyService.searchProperties(null, null, pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual.getContent()))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

}
