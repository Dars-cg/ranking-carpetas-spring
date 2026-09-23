package com.proyecto.ranking_carpetas.controller;

import com.proyecto.ranking_carpetas.model.CarpetaRegistro;
import com.proyecto.ranking_carpetas.repository.CarpetaRepository;
import com.proyecto.ranking_carpetas.service.ZipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CarpetaWebController {

    private final ZipService zipService;
    private final CarpetaRepository carpetaRepository;

    public CarpetaWebController(ZipService zipService, CarpetaRepository carpetaRepository) {
        this.zipService = zipService;
        this.carpetaRepository = carpetaRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("ranking", carpetaRepository.findTop10ByOrderByTotalArchivosDesc());
        return "index";
    }

    @PostMapping("/subir")
    public String procesarDesdeWeb(@RequestParam("archivo") MultipartFile archivo, 
                                   RedirectAttributes redirectAttributes) {
        if (archivo == null || archivo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor selecciona un archivo ZIP.");
            return "redirect:/";
        }

        try {
            CarpetaRegistro nuevo = zipService.procesarYGuardar(archivo);
            redirectAttributes.addFlashAttribute("mensaje", 
                    "¡Analizado con éxito! La carpeta '" + nuevo.getNombre() + 
                    "' tiene " + nuevo.getTotalArchivos() + " archivos.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el archivo: " + e.getMessage());
        }

        return "redirect:/";
    }
}