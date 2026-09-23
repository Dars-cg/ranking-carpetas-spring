package com.proyecto.ranking_carpetas.service;

import com.proyecto.ranking_carpetas.model.CarpetaRegistro;
import com.proyecto.ranking_carpetas.repository.CarpetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
@Service
public class ZipService {

    private final CarpetaRepository carpetaRepository;

    public ZipService(CarpetaRepository carpetaRepository) {
        this.carpetaRepository = carpetaRepository;
    }

    public CarpetaRegistro procesarYGuardar(MultipartFile archivo) throws IOException {
        int contadorArchivos = 0;

        try (ZipInputStream zis = new ZipInputStream(archivo.getInputStream())) {
            ZipEntry entrada;
            while ((entrada = zis.getNextEntry()) != null) {
                if (!entrada.isDirectory()) {
                    contadorArchivos++;
                }
                zis.closeEntry();
            }
        }

        String nombreOriginal = archivo.getOriginalFilename();
        String nombreCarpeta = (nombreOriginal != null && nombreOriginal.endsWith(".zip"))
                ? nombreOriginal.substring(0, nombreOriginal.lastIndexOf('.'))
                : nombreOriginal;

        CarpetaRegistro nuevoRegistro = new CarpetaRegistro(
                nombreCarpeta,
                contadorArchivos,
                LocalDateTime.now()
        );

        return carpetaRepository.save(nuevoRegistro);
    }
}