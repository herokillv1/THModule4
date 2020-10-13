package com.example.demo.controller;


import com.example.demo.model.City;
import com.example.demo.model.Country;
import com.example.demo.service.CityService;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class Citycontroller {

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @ModelAttribute("countries")
    public Iterable<Country> countries() {
        return countryService.findAll();
    }

    @GetMapping("/")
    public ModelAndView listCustomers(@RequestParam("search") Optional<String> search,@SortDefault(value = {"id"},direction = Sort.Direction.ASC) @PageableDefault(value = 3) Pageable pageable){
        Page<City> cities;
        if(search.isPresent()){
            cities = cityService.findAllByNameContaining(search.get(), pageable);
        } else {
            cities = cityService.fillAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("search", search.orElse(""));
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/list-city/{id}")
    public ModelAndView listCity(@PathVariable("id") Long id){
        City city = cityService.findById(id);
        if(city == null){
            return new ModelAndView("error.404");
        }
        ModelAndView modelAndView = new ModelAndView("city/list");
        modelAndView.addObject("city", city);
        return modelAndView;
    }

    @GetMapping("/create-city")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("city/create");
        modelAndView.addObject("city", new City());
        return modelAndView;
    }

    @PostMapping("/create-city")
    public ModelAndView saveCustomer(@ModelAttribute("customer") City city , @RequestParam("search") Optional<String> search,@SortDefault(value = {"id"},direction = Sort.Direction.ASC) @PageableDefault(value = 3) Pageable pageable){
        cityService.save(city);
        Page<City> cities;
        if(search.isPresent()){
            cities = cityService.findAllByNameContaining(search.get(), pageable);
        } else {
            cities = cityService.fillAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("message", "Thêm thành công");
        modelAndView.addObject("search", search.orElse(""));
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/edit-city/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        City city = cityService.findById(id);
        if(city != null) {
            ModelAndView modelAndView = new ModelAndView("city/edit");
            modelAndView.addObject("city", city);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-city")
    public ModelAndView updateCustomer(@ModelAttribute("city") City city , @RequestParam("search") Optional<String> search,@SortDefault(value = {"id"},direction = Sort.Direction.ASC) @PageableDefault(value = 3) Pageable pageable){
        cityService.save(city);
        Page<City> cities;
        if(search.isPresent()){
            cities = cityService.findAllByNameContaining(search.get(), pageable);
        } else {
            cities = cityService.fillAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("message", "Cập nhật thành công");
        modelAndView.addObject("search", search.orElse(""));
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/delete-city/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        City city = cityService.findById(id);
        if(city != null) {
            ModelAndView modelAndView = new ModelAndView("city/delete");
            modelAndView.addObject("city", city);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-city")
    public String deleteCustomer(@ModelAttribute("city") City city){
        cityService.remove(city.getId());
        return "redirect:";
    }
}
