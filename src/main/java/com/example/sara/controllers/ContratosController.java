package com.example.sara.controllers;

import com.example.sara.applications.ContratosApplication;
import com.example.sara.domain.dto.AltaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("api/contratos")
public class ContratosController {

  @Autowired
  private ContratosApplication contratosApplication;

  @PostMapping("")
  public void crearContrato(@RequestBody AltaDTO altaDTO) throws IOException {
    this.contratosApplication.generarContratoIndividual(altaDTO);
  }

  @PostMapping(value = "documentacion",produces = "application/zip")
  public byte[] crearDocumentacion(@RequestBody AltaDTO altaDTO, HttpServletResponse httpServletResponse) throws IOException {
    return this.contratosApplication.generarDocumentacion(altaDTO,httpServletResponse);
  }
}
