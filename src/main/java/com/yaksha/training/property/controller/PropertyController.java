package com.yaksha.training.property.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.service.PropertyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = {"/property", "/"})
public class PropertyController {

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @RequestMapping(value = {"/list", "/"})
    public String listProperties(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "max", required = false) Double max,
                                 @PageableDefault(size = 5) Pageable pageable,
                                 Model theModel) {
        Page<Property> properties = propertyService.searchProperties(name, max, pageable);
        theModel.addAttribute("properties", properties.getContent());
        theModel.addAttribute("name", name != null ? name : "");
        theModel.addAttribute("max", max);
        theModel.addAttribute("totalPage", properties.getTotalPages());
        theModel.addAttribute("page", pageable.getPageNumber());
        theModel.addAttribute("sortBy", pageable.getSort().get().count() != 0 ?
                pageable.getSort().get().findFirst().get().getProperty() + "," + pageable.getSort().get().findFirst().get().getDirection() : "");
        return "list-properties";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        theModel.addAttribute("property", new Property());
        return "property-add";
    }

    @PostMapping("/saveProperty")
    public String saveProperty(@Valid @ModelAttribute("property") Property theProperty, BindingResult bindingResult, Model theModel) {
        if (bindingResult.hasErrors()) {
            return "property-add";
        }
        propertyService.saveProperty(theProperty);
        return "redirect:/property/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("propertyId") Long propertyId, Model theModel) {
        theModel.addAttribute("property", propertyService.getProperty(propertyId));
        return "property-add";

    }

    @GetMapping("/showFormForDelete")
    public String showFormForDelete(@RequestParam("propertyId") Long propertyId, Model theModel) {
        propertyService.deleteProperty(propertyService.getProperty(propertyId));
        return "redirect:/property/list";

    }

    @RequestMapping("/search")
    public String searchProperty(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "max", required = false) Double max,
                                 Model theModel,
                                 Pageable pageable) {
        Page<Property> properties = propertyService.searchProperties(name, max, pageable);
        theModel.addAttribute("properties", properties.getContent());
        theModel.addAttribute("name", name != null ? name : "");
        theModel.addAttribute("max", max);
        theModel.addAttribute("totalPage", properties.getTotalPages());
        theModel.addAttribute("page", pageable.getPageNumber());
        return "search-list-properties";
    }

}
