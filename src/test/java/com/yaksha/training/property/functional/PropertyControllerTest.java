package com.yaksha.training.property.functional;

import static com.yaksha.training.property.utils.MasterData.asJsonString;
import static com.yaksha.training.property.utils.MasterData.getProperty;
import static com.yaksha.training.property.utils.MasterData.getPropertyList;
import static com.yaksha.training.property.utils.MasterData.randomStringWithSize;
import static com.yaksha.training.property.utils.TestUtils.boundaryTestFile;
import static com.yaksha.training.property.utils.TestUtils.businessTestFile;
import static com.yaksha.training.property.utils.TestUtils.currentTest;
import static com.yaksha.training.property.utils.TestUtils.testReport;
import static com.yaksha.training.property.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yaksha.training.property.controller.PropertyController;
import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.service.PropertyService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(SpringExtension.class)
public class PropertyControllerTest {

	@Mock
	private PropertyService propertyService;

	@InjectMocks
	private PropertyController propertyController;

	private MockMvc mockMvc;

	// private static Validator validator;
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(propertyController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testControllerListPropertiesHome() throws Exception {
		Pageable pageable = PageRequest.of(0, 5);
		List<Property> properties = getPropertyList(5);
		Page<Property> propertyPage = new PageImpl<>(properties);
		when(propertyService.searchProperties(null, null, pageable)).thenReturn(propertyPage);
		MvcResult result = this.mockMvc.perform(get("/")).andReturn();
		yakshaAssert(currentTest(), result.getModelAndView() != null && result.getModelAndView().getViewName() != null
				&& result.getModelAndView().getViewName().contentEquals("list-properties"), businessTestFile);
	}

	@Test
	public void testControllerListProperties() throws Exception {
		Pageable pageable = PageRequest.of(0, 5);
		List<Property> properties = getPropertyList(5);
		Page<Property> propertyPage = new PageImpl<>(properties);
		when(propertyService.searchProperties(null, null, pageable)).thenReturn(propertyPage);
		MvcResult result = this.mockMvc.perform(get("/list")).andReturn();
		yakshaAssert(currentTest(), result.getModelAndView() != null && result.getModelAndView().getViewName() != null
				&& result.getModelAndView().getViewName().contentEquals("list-properties"), businessTestFile);
	}

	@Test
	public void testControllerShowFormForAdd() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/showFormForAdd")).andReturn();
		yakshaAssert(currentTest(), result.getModelAndView() != null && result.getModelAndView().getViewName() != null
				&& result.getModelAndView().getViewName().contentEquals("property-add"), businessTestFile);
	}

	@Test
	public void testControllerSaveProperty() throws Exception {
		Property property = getProperty();
		MvcResult result = this.mockMvc.perform(post("/saveProperty").flashAttr("property", property)).andReturn();
		yakshaAssert(currentTest(),
				result.getModelAndView() != null && result.getModelAndView().getViewName() != null
						&& result.getModelAndView().getViewName().contentEquals("redirect:/property/list"),
				businessTestFile);
	}

	@Test
	public void testControllerShowFormForUpdate() throws Exception {
		Property property = getProperty();
		when(propertyService.getProperty(property.getId())).thenReturn(property);
		MvcResult result = this.mockMvc
				.perform(get("/showFormForUpdate").param("propertyId", property.getId().toString())).andReturn();
		yakshaAssert(currentTest(), result.getModelAndView() != null && result.getModelAndView().getViewName() != null
				&& result.getModelAndView().getViewName().contentEquals("property-add"), businessTestFile);
	}

	@Test
	public void testControllerShowFormForDeleteProperty() throws Exception {
		Property property = getProperty();
		MvcResult result = this.mockMvc
				.perform(get("/showFormForDelete").param("propertyId", property.getId().toString())).andReturn();
		yakshaAssert(currentTest(),
				result.getModelAndView() != null && result.getModelAndView().getViewName() != null
						&& result.getModelAndView().getViewName().contentEquals("redirect:/property/list"),
				businessTestFile);
	}

	@Test
	public void testControllerSearchPropertyWithNulKeys() throws Exception {
		String name = null;
		Double max = null;
		Pageable pageable = PageRequest.of(0, 5);
		List<Property> properties = getPropertyList(5);
		Page<Property> expected = new PageImpl<>(properties);
		when(propertyService.searchProperties(name, max, pageable)).thenReturn(expected);
		MvcResult result = this.mockMvc
				.perform(post("/list").param("name", name).param("max", "").param("page", "0").param("size", "5"))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getModelAndView() != null && result.getModelAndView().getViewName() != null
						&& result.getModelAndView().getViewName().contentEquals("list-properties")
						&& asJsonString(expected.getContent())
								.equals(asJsonString(result.getModelAndView().getModel().get("properties"))) ? "true"
										: "false",
				businessTestFile);
	}

	@Test
	public void testControllerSearchPropertyWithName() throws Exception {
		Double max = null;
		Pageable pageable = PageRequest.of(0, 10);
		List<Property> properties = getPropertyList(10);
		Property property = properties.stream().findAny().get();
		String name = property.getName();
		List<Property> filterList = new ArrayList<>();
		filterList.add(property);
		Page<Property> expected = new PageImpl<>(filterList);
		when(propertyService.searchProperties(name, max, pageable)).thenReturn(expected);
		MvcResult result = this.mockMvc
				.perform(post("/list").param("name", name).param("max", "").param("page", "0").param("size", "10"))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getModelAndView() != null && result.getModelAndView().getViewName() != null
						&& result.getModelAndView().getViewName().contentEquals("list-properties")
						&& asJsonString(expected.getContent())
								.equals(asJsonString(result.getModelAndView().getModel().get("properties"))) ? "true"
										: "false",
				businessTestFile);
	}

	@Test
	public void testControllerSearchPropertyWithMaxPrice() throws Exception {
		String name = null;
		Pageable pageable = PageRequest.of(0, 10);
		List<Property> properties = getPropertyList(10);
		Property property = properties.stream().findAny().get();
		Double max = property.getPrice();
		List<Property> filterList = new ArrayList<>();
		filterList.add(property);
		Page<Property> expected = new PageImpl<>(filterList);
		when(propertyService.searchProperties(name, max, pageable)).thenReturn(expected);
		MvcResult result = this.mockMvc.perform(
				post("/list").param("name", name).param("max", max.toString()).param("page", "0").param("size", "10"))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getModelAndView() != null && result.getModelAndView().getViewName() != null
						&& result.getModelAndView().getViewName().contentEquals("list-properties")
						&& asJsonString(expected.getContent())
								.equals(asJsonString(result.getModelAndView().getModel().get("properties"))) ? "true"
										: "false",
				businessTestFile);
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
