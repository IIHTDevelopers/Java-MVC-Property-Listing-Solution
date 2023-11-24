package com.yaksha.training.property.controller;


import com.yaksha.training.property.entity.Property;
import com.yaksha.training.property.service.PropertyService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

import javax.validation.Valid;

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
    public String listProperties(Model theModel) {
        theModel.addAttribute("properties", propertyService.getProperties());
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
}
