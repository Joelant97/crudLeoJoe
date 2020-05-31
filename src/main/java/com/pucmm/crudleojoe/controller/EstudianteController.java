package com.pucmm.crudleojoe.controller;


import com.pucmm.crudleojoe.model.Estudiante;
import com.pucmm.crudleojoe.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;


@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    EstudianteRepository estudianteRepository;

    @GetMapping("/")
    public String showEstudiantes(Estudiante estudiante, Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addEstudiante(@Valid Estudiante estudiante, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        estudianteRepository.save(estudiante);
        model.addAttribute("estudiantes", estudianteRepository.findAll());
            return "index";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid estudiante Id:" + id));

        model.addAttribute("estudiante", estudiante);
        return "edit";
    }


    @PostMapping("/update/{id}")
    public String updateEstudiante(@PathVariable("id") long id, @Valid Estudiante estudiante,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            estudiante.setId(id);
            return "redirect:/estudiantes/";
        }

        estudianteRepository.save(estudiante);
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "index";
    }


    @GetMapping("/delete/{id}")
    public String deleteEstudiante(@PathVariable("id") long id, Model model) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid estudiante Id:" + id));
        estudianteRepository.delete(estudiante);
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "redirect:/estudiantes/";
    }
}
