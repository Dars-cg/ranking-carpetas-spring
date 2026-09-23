
package com.proyecto.ranking_carpetas.repository;

import com.proyecto.ranking_carpetas.model.CarpetaRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarpetaRepository extends JpaRepository<CarpetaRegistro, Long> {

    // Spring crea el SQL: SELECT * FROM carpetas_ranking ORDER BY total_archivos DESC LIMIT 10;
    List<CarpetaRegistro> findTop10ByOrderByTotalArchivosDesc();

    // Consulta todo el ranking completo ordenado
    List<CarpetaRegistro> findAllByOrderByTotalArchivosDesc();
}