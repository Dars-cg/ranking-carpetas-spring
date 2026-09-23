package com.proyecto.ranking_carpetas.controller;

import com.proyecto.ranking_carpetas.model.CarpetaRegistro;
import com.proyecto.ranking_carpetas.repository.CarpetaRepository;
import com.proyecto.ranking_carpetas.service.ZipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/carpetas")
public class CarpetaRestController {

    private final ZipService zipService;
    private final CarpetaRepository carpetaRepository;

    public CarpetaRestController(ZipService zipService, CarpetaRepository carpetaRepository) {
        this.zipService = zipService;
        this.carpetaRepository = carpetaRepository;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> subirYAnalizar(@RequestParam("archivo") MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Debe enviar un archivo válido.");
        }

        String nombre = archivo.getOriginalFilename();
        if (nombre == null || !nombre.toLowerCase().endsWith(".zip")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Formato no admitido. Debe enviar un archivo con extensión .zip");
        }

        try {
            CarpetaRegistro nuevo = zipService.procesarYGuardar(archivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo ZIP: " + e.getMessage());
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<CarpetaRegistro>> obtenerRanking() {
        return ResponseEntity.ok(carpetaRepository.findTop10ByOrderByTotalArchivosDesc());
    }

    @GetMapping
    public ResponseEntity<List<CarpetaRegistro>> listarTodos() {
        return ResponseEntity.ok(carpetaRepository.findAllByOrderByTotalArchivosDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarpetaRegistro> obtenerPorId(@PathVariable Long id) {
        return carpetaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE: DELETE /api/carpetas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id) {
        if (!carpetaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carpetaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNombre(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoNombre = body.get("nombre");

        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            return ResponseEntity.badRequest().body("El campo 'nombre' es obligatorio.");
        }

        return carpetaRepository.findById(id)
                .map(registroExistente -> {
                    registroExistente.setNombre(nuevoNombre);
                    CarpetaRegistro actualizado = carpetaRepository.save(registroExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}