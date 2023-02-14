package com.publica.tuanuncio.controller;

import com.publica.tuanuncio.dto.FiltroDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionBandaDTO;
import com.publica.tuanuncio.dto.get.GetPublicacionMusicoDTO;
import com.publica.tuanuncio.service.IPublicacionBandaService;
import com.publica.tuanuncio.service.IPublicacionMusicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

	@Autowired
	private IPublicacionBandaService bandaService;

	@Autowired
	private IPublicacionMusicoService musicoService;

	// MOSTRAR PUBLICACIONES CREADAS POR 'ROLE_MUSICO', PAGINADAS Y/o FILTRADAS
	@PreAuthorize("hasAnyRole ('BANDA', 'MUSICO', 'ADMIN')")
	@GetMapping("/musicos")
	@Operation(summary = "Devuelve una lista de publicaciones paginadas y/o filtradas. La utilización de los filtros es opcional.")
	public ResponseEntity<Page<GetPublicacionMusicoDTO>> verPublicacionesDeMusicos(
			@Parameter(description = "Las busquedas solo se pueden filtrar por fechaPublicacion, generoMusical, "
					+ "provincia, localidad y/o instrumento. Si se coloca un valor null se ignora el filtrado.") @RequestBody FiltroDTO filtro,
			Pageable pageable) {
		return ResponseEntity.ok(bandaService.verPublicaciones(filtro, pageable));
	}

	// MOSTRAR PUBLICACIONES CREADAS POR 'ROLE_BANDA', PAGINADAS Y/o FILTRADAS
	@PreAuthorize("hasAnyRole ('MUSICO', 'BANDA', 'ADMIN')")
	@GetMapping("/bandas")
	@Operation(summary = "Devuelve una lista de publicaciones paginadas y/o filtradas. La utilización de los filtros es opcional.")
	public ResponseEntity<Page<GetPublicacionBandaDTO>> verPublicacionesDeBandas(
			@Parameter(description = "Las busquedas solo se pueden filtrar por fechaPublicacion, generoMusical, "
					+ "provincia y/o localidad. Si se coloca un valor null se ignora el filtrado.") @RequestBody FiltroDTO filtro,
			Pageable pageable) {
		return ResponseEntity.ok(musicoService.verPublicaciones(filtro, pageable));
	}

}
