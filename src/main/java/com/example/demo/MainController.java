package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

    @Controller
    public class MainController {
        @Autowired
        PersonRepository people;

        @RequestMapping("/")
        public String displayHome() {
            return "index";
        }


        @GetMapping("/addperson")
        public String addPerson(Model model) {
            model.addAttribute("person", new Person());
            return "addperson";
        }

        @PostMapping("/process")
        public String processForm(@Valid Person person, BindingResult result) {
            if (result.hasErrors()) {
                return "addperson";
            }
            people.save(person);
            return "redirect:/showpeople";
        }

        @RequestMapping("/showpeople")
        public String showPeople(Model model) {
            model.addAttribute("people", people.findAll());
            return "listpeople";
        }

        @RequestMapping("/detail/{id}")
        public @ResponseBody String showJob(@PathVariable("id") long id, Model model) {
            model.addAttribute("person", people.findById(id).get());
            return "show";
        }
        @RequestMapping("/update/{id}")
        public String updatePerson ( @PathVariable("id") long id, Model model){
            model.addAttribute("aPerson", people.findById(id).get());
            return "addperson";
        }

        @RequestMapping("/delete/{id}")
        public  String delCourse(@PathVariable("id") long id){
            people.deleteById(id);
            return "listpeople";
        }
    }
