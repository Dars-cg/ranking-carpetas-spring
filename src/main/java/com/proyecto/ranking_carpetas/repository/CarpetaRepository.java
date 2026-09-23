
package com.proyecto.ranking_carpetas.repository;

import com.proyecto.ranking_carpetas.model.CarpetaRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarpetaRepository extends JpaRepository<CarpetaRegistro, Long> {

    List<CarpetaRegistro> findTop10ByOrderByTotalArchivosDesc();

    List<CarpetaRegistro> findAllByOrderByTotalArchivosDesc();
}