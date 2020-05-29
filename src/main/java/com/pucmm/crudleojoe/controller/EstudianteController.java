package com.pucmm.crudleojoe.controller;


import com.pucmm.crudleojoe.model.Estudiante;
import com.pucmm.crudleojoe.repository.EstudianteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class EstudianteController {

    EstudianteRepository estudianteRepository;

    @GetMapping("/signup")
    public String showSignUpForm(Estudiante estudiante) {
        return "add-estudiante";
    }

    @PostMapping("/addestudiante")
    public String addEstudiante(@Valid Estudiante estudiante, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-estudiante";
        }

        estudianteRepository.save(estudiante);
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "index";
    }

    // additional CRUD methods

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid estudiante Id:" + id));

        model.addAttribute("estudiante", estudiante);
        return "update-estudiante";
    }

    @PostMapping("/update/{id}")
    public String updateEstudiante(@PathVariable("id") long id, @Valid Estudiante estudiante,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            estudiante.setId(id);
            return "update-estudiante";
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
        return "index";
    }

}
