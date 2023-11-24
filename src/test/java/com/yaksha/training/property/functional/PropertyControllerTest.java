package com.yaksha.training.property.functional;

import com.yaksha.training.property.controller.PropertyController;
import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.service.PropertyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.yaksha.training.property.utils.MasterData.getProperty;
import static com.yaksha.training.property.utils.MasterData.getPropertyList;
import static com.yaksha.training.property.utils.TestUtils.businessTestFile;
import static com.yaksha.training.property.utils.TestUtils.currentTest;
import static com.yaksha.training.property.utils.TestUtils.testReport;
import static com.yaksha.training.property.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private MockMvc mockMvc;

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
    public void testControllerListPropertiesHome() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/")).andReturn();
        when(propertyService.getProperties()).thenReturn(getPropertyList(5));
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("list-properties"), businessTestFile);
    }

    @Test
    public void testControllerListProperties() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/list")).andReturn();
        when(propertyService.getProperties()).thenReturn(getPropertyList(5));
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("list-properties"), businessTestFile);
    }

    @Test
    public void testControllerShowFormForAdd() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/showFormForAdd")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add"), businessTestFile);
    }

    @Test
    public void testControllerSaveProperty() throws Exception {
        Property property = getProperty();
        MvcResult result = this.mockMvc.perform(post("/saveProperty")
                .flashAttr("property", property)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/property/list"), businessTestFile);
    }

    @Test
    public void testControllerShowFormForUpdate() throws Exception {
        Property property = getProperty();
        when(propertyService.getProperty(property.getId())).thenReturn(property);
        MvcResult result = this.mockMvc.perform(get("/showFormForUpdate")
                .param("propertyId", property.getId().toString())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("property-add"), businessTestFile);
    }

    @Test
    public void testControllerShowFormForDeleteProperty() throws Exception {
        Property property = getProperty();
        MvcResult result = this.mockMvc.perform(get("/showFormForDelete")
                .param("propertyId", property.getId().toString())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/property/list"), businessTestFile);
    }

}
