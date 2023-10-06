package org.example.controllers;

import jakarta.validation.Valid;
import org.example.models.Book;
import org.example.models.Reader;
import org.example.repositories.ReadersRepository;
import org.example.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/readers")
public class ReadersController {

    public final ReadersRepository readersRepository;
    public final ReaderService readerService;

    @Autowired
    public ReadersController(ReadersRepository readersRepository, ReaderService readerService) {
        this.readersRepository = readersRepository;
        this.readerService = readerService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("readers", readerService.findAll());
        return "readers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerService.findOne(id));
        model.addAttribute("books", readerService.getBooksByReaderId(id));
        return "readers/show";
    }

    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader){
        return "readers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "readers/new";

        readerService.save(reader);
        return "redirect:/readers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("reader", readerService.findOne(id));
        return "readers/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "readers/edit";

        readerService.update(id, reader);
        return "redirect:/readers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        readerService.delete(id);
        return "redirect:/readers";
    }




}

