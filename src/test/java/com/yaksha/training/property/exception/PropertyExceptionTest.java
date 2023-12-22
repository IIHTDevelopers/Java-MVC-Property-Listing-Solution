package com.yaksha.training.property.exception;

import com.yaksha.training.property.controller.PropertyController;
import com.yaksha.training.property.entity.Property;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static com.yaksha.training.property.utils.MasterData.getProperty;
import static com.yaksha.training.property.utils.TestUtils.currentTest;
import static com.yaksha.training.property.utils.TestUtils.exceptionTestFile;
import static com.yaksha.training.property.utils.TestUtils.testReport;
import static com.yaksha.training.property.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PropertyExceptionTest {

    @InjectMocks
    private PropertyController propertyController;

    private MockMvc mockMvc;

    BindingResult bindingResult = mock(BindingResult.class);

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testExceptionSavePropertyNameAsNull() throws Exception {
        Property property = getProperty();
        property.setName(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveProperty")
                .flashAttr("property", property)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add")), exceptionTestFile);
    }

    @Test
    public void testExceptionSavePropertyAddressAsNull() throws Exception {
        Property property = getProperty();
        property.setAddress(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveProperty")
                .flashAttr("property", property)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add")), exceptionTestFile);
    }

    @Test
    public void testExceptionSavePropertyDimensionAsNull() throws Exception {
        Property property = getProperty();
        property.setDimensions(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveProperty")
                .flashAttr("property", property)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add")), exceptionTestFile);
    }

    @Test
    public void testExceptionSavePropertyRoomsAsNull() throws Exception {
        Property property = getProperty();
        property.setRooms(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveProperty")
                .flashAttr("property", property)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add")), exceptionTestFile);
    }

    @Test
    public void testExceptionSavePropertyPriceAsNull() throws Exception {
        Property property = getProperty();
        property.setPrice(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/saveProperty")
                .flashAttr("property", property)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add")), exceptionTestFile);
    }

}
