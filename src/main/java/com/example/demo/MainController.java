package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    PersonRepository personRepository;

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
        personRepository.save(person);
        return "redirect:/showpeople";
    }

    @RequestMapping("/showpeople")
    public String showPeople(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "listpeople";
    }

    @RequestMapping("/detail/{id}")
    public String showPerson(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personRepository.findById(id).get());
        return "showperson";
    }
    @RequestMapping("/update/{id}")
    public String updatePerson( @PathVariable("id") long id, Model model){
        model.addAttribute("person", personRepository.findById(id).get());
        return "addperson";
    }

    @RequestMapping("/delete/{id}")
    public  String deletePerson(@PathVariable("id") long id){
        personRepository.deleteById(id);
        return "listpeople";
    }

    @RequestMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search", searchString);
        model.addAttribute("people",
                personRepository.findAllByFoodContainingIgnoreCase(searchString));
        return "listpeople";
    }
}