package com.example.sara.applications;

import com.example.sara.domain.dto.AltaDTO;
import com.example.sara.domain.dto.ContratoDto;
import com.example.sara.domain.dto.RazonSocialDTO;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Repository
public class ContratosApplication {

  public String generarContratoIndividual(AltaDTO altaDTO) throws IOException {

    String fileName = "contratoIndividual.html";
    String fileNameContrato = "contratoIndividual.pdf";

    String anexo = "";

    switch (altaDTO.getPuesto().toUpperCase()){
      case "PROMOTOR":
        anexo = "anexoPromotor.html";
        break;
      case "ASESOR EMPRESARIAL":
        anexo = "anexoAsesorEmpresarial.html";
        break;
      case "LIDER ACTIVACION":
        anexo = "anexoLiderActivacion.html";
        break;
      case "LIDER ASESOR EMPRESARIAL":
        anexo = "anexoLiderAsesorEmpresarial.html";
        break;
      default:
        anexo = "anexoGestor.html";
    }

    byte[] bytes = Files.readAllBytes(Paths.get(fileName));
    String content = new String(bytes, StandardCharsets.UTF_8);

    byte[] bytesAnexo = Files.readAllBytes(Paths.get(anexo));
    String contentAnexo = new String(bytesAnexo, StandardCharsets.UTF_8);

    //Files.deleteIfExists(Paths.get(fileNameContrato));

    String domicilioProspecto = altaDTO.getDomicilioColaborador() + " " + altaDTO.getColoniaColaborador();
    String ciudadEstado = altaDTO.getPoblacionColaborador() + ", " + altaDTO.getEstadoColaborador();


    LocalDate fechaNac = altaDTO.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate fechaAlta = altaDTO.getFechaAlta().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate ahora = LocalDate.now();

    Period periodo = Period.between(fechaNac, ahora);
    Integer edadProspecto = periodo.getYears();

    Locale locale = new Locale("es","MX");

    RazonSocialDTO razonSocialDTO = obtenerRazonSocial(altaDTO.getRazonSocial());

    content = content
      .replace("var_anexo",contentAnexo)
      .replace("var_ciudadEstadoDomicilio",ciudadEstado)
      .replace("var_rfcEmpresa",razonSocialDTO.getRfc())
      .replace("var_nombreCortoFiscal",razonSocialDTO.getNombreCorto())
      .replace("var_ciudad",altaDTO.getCiudadContratacion())
      .replace("var_estadoCivil",altaDTO.getEstadoCivil())
      .replace("var_estado",altaDTO.getEstadoContratacion())
      .replace("var_diasPrueba",altaDTO.getDiasPruebaContrato())
      .replace("var_dia",String.valueOf(fechaAlta.getDayOfMonth()))
      .replace("var_mes",fechaAlta.getMonth().getDisplayName(TextStyle.FULL,locale))
      .replace("var_anio",String.valueOf(fechaAlta.getYear()))
      .replace("var_nombreEmpleado",altaDTO.getNombreColaborador())
      .replace("var_domicilioEmpleado",domicilioProspecto)
      .replace("var_puesto",altaDTO.getPuesto())
      .replace("var_nacionalidad",altaDTO.getNacionalidad())
      .replace("var_edad",edadProspecto.toString())
      .replace("var_sexo",altaDTO.getSexo())
      .replace("var_domicilioFiscal",razonSocialDTO.getDomicilioFiscal())
      .replace("var_rfc",altaDTO.getRfcEmpleado())
      .replace("var_curp",altaDTO.getCurpEmpleado())
      .replace("var_sueldoDiario",altaDTO.getSueldoDiario().toString())
      .replace("var_numeroCuenta",altaDTO.getCuentaBancomer())
      .replace("var_correoEmpleado",altaDTO.getCorreoColaborador())
      .replace("var_estudios",altaDTO.getEstudiosColaborador())
      .replace("var_experiencia",altaDTO.getExperienciaLaboral())
      .replace("var_razonSocial",razonSocialDTO.getRazonSocial());

    OutputStream fileOutputStream = new FileOutputStream(fileNameContrato);
    HtmlConverter.convertToPdf(content, fileOutputStream);

    System.getProperty("0");
    System.getenv("0");

    return fileNameContrato;
  }

  public byte[] generarDocumentacion(AltaDTO altaDTO, HttpServletResponse httpServletResponse) throws IOException {
    List<String> list = new ArrayList<>();
    list.add(generarAltaColaborador(altaDTO));
    list.add(generarContratoIndividual(altaDTO));
    list.add(generarContratoConfidencialidad(altaDTO));
    list.add(generarArchivoPLD(altaDTO));
    return zipDocuments(list,altaDTO.getNombreColaborador(),httpServletResponse);
  }

  public String generarAltaColaborador(AltaDTO altaDTO) throws IOException {
    String fileAlta = "altaColaborador.pdf";
    String fileNameAlta = "altaColaborador.html";

    byte[] bytesAlta = Files.readAllBytes(Paths.get(fileNameAlta));
    String contentAlta = new String(bytesAlta, StandardCharsets.UTF_8);

    //Files.deleteIfExists(Paths.get(fileAlta));

    String fechaFormato = new SimpleDateFormat("dd/MM/yyyy").format(altaDTO.getFechaAlta());
    String fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").format(altaDTO.getFechaNacimiento());
    String fechaNacimientoBeneficiario = new SimpleDateFormat("dd/MM/yyyy").format(altaDTO.getFechaNacimientoBeneficiario());

    contentAlta = contentAlta
            .replace("var_nombreBeneficiario",altaDTO.getNombreBeneficiario().toUpperCase())
            .replace("var_fechaNacimientoBeneficiario",fechaNacimientoBeneficiario)
            .replace("var_parentestoBeneficiario",altaDTO.getParentescoBeneficiario().toUpperCase())
            .replace("var_sucursal",altaDTO.getSucursal().toUpperCase())
            .replace("var_fechaAlta",fechaFormato)
            .replace("var_nombreEmpleado",altaDTO.getNombreColaborador().toUpperCase())
            .replace("var_fechaNacimiento",fechaNacimiento)
            .replace("var_paisOrigen",altaDTO.getPaisOrigen().toUpperCase())
            .replace("var_nacionalidad",altaDTO.getNacionalidad().toUpperCase())
            .replace("var_sexo",altaDTO.getSexo().toUpperCase())
            .replace("var_ocupacionActual",altaDTO.getOcupacionActual().toUpperCase())
            .replace("var_nombrePadre",altaDTO.getNombrePadre().toUpperCase())
            .replace("var_nombreMadre",altaDTO.getNombreMadre().toUpperCase())
            .replace("var_rfc",altaDTO.getRfcEmpleado().toUpperCase().toUpperCase())
            .replace("var_curp",altaDTO.getCurpEmpleado().toUpperCase(Locale.ROOT))
            .replace("var_numeroAfiliacion",altaDTO.getNumeroSeguro())
            .replace("var_estadoCivil",altaDTO.getEstadoCivil().toUpperCase())
            .replace("var_puesto",altaDTO.getPuesto().toUpperCase())
            .replace("var_sueldoDiario",altaDTO.getSueldoDiario().toString())
            .replace("var_sueldoBrutoMensual",altaDTO.getSueldoBrutoMensual().toString())
            .replace("var_domicilioEmpleado",altaDTO.getDomicilioColaborador().toUpperCase())
            .replace("var_colonia",altaDTO.getColoniaColaborador().toUpperCase())
            .replace("var_codigoPostal",altaDTO.getCpColaborador())
            .replace("var_poblacion",altaDTO.getPoblacionColaborador().toUpperCase())
            .replace("var_estado",altaDTO.getEstadoColaborador().toUpperCase())
            .replace("var_telefonoParticular",altaDTO.getTelefonoColaborador())
            .replace("var_telefonoOficina",altaDTO.getTelefonoOficina())
            .replace("var_extension",altaDTO.getExtensionOficina())
            .replace("var_celular",altaDTO.getCelularColaborador())
            .replace("var_correoEmpleado",altaDTO.getCorreoColaborador().toUpperCase())
            .replace("var_numeroCredito",altaDTO.getNumeroInfonavit())
            .replace("var_factorDescuento",altaDTO.getFactorDescuento() != null ? altaDTO.getFactorDescuento().toString() : "")
            .replace("var_numeroCuenta",altaDTO.getCuentaBancomer())
            .replace("var_talla",altaDTO.getTallaColaborador());

    //Files.deleteIfExists(Paths.get(fileNameAlta));

    OutputStream fileOutputStreamAlta = new FileOutputStream(fileAlta);
    HtmlConverter.convertToPdf(contentAlta, fileOutputStreamAlta);

    return fileAlta;
  }

  public String generarContratoConfidencialidad(AltaDTO altaDTO) throws IOException {
    String fileConfindecialidad = "contratoConfidencionalidad.pdf";
    String fileNameConfindecialidad = "contratoConfidencialidad.html";

    byte[] bytesConfidencionalidad = Files.readAllBytes(Paths.get(fileNameConfindecialidad));
    String contentConfidencialidad = new String(bytesConfidencionalidad, StandardCharsets.UTF_8);

    RazonSocialDTO razonSocialDTO = obtenerRazonSocial(altaDTO.getRazonSocial());

    LocalDate fechaAlta = altaDTO.getFechaAlta().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    String fechaFormato = new SimpleDateFormat("dd/MM/yyyy").format(altaDTO.getFechaNacimiento());

    String direccionColaborador =
            String.join(" ",altaDTO.getDomicilioColaborador(),altaDTO.getColoniaColaborador()
                    ,altaDTO.getPoblacionColaborador()).toUpperCase();

    direccionColaborador += ", " + altaDTO.getEstadoColaborador().toUpperCase();

    Locale locale = new Locale("es","MX");

    //Files.deleteIfExists(Paths.get(fileConfindecialidad));

    contentConfidencialidad =
            contentConfidencialidad
                    .replace("var_rfcEmpresa",razonSocialDTO.getRfc())
                    .replace("var_nombreCortoFiscal",razonSocialDTO.getNombreCorto())
                    .replace("var_ciudad",altaDTO.getCiudadContratacion())
                    .replace("var_estado",altaDTO.getEstadoContratacion())
                    .replace("var_dia",String.valueOf(fechaAlta.getDayOfMonth()))
                    .replace("var_mes",fechaAlta.getMonth().getDisplayName(TextStyle.FULL,locale))
                    .replace("var_fechaNacimiento",fechaFormato)
                    .replace("var_anio",String.valueOf(fechaAlta.getYear()))
                    .replace("var_curp",altaDTO.getCurpEmpleado())
                    .replace("var_nombreEmpleado",altaDTO.getNombreColaborador().toUpperCase())
                    .replace("var_domicilioEmpleado",direccionColaborador)
                    .replace("var_puesto",altaDTO.getPuesto())
                    .replace("var_domicilioFiscal",razonSocialDTO.getDomicilioFiscal())
                    .replace("var_razonSocial",razonSocialDTO.getRazonSocial());

    OutputStream fileOutputStreamConfidencialidad = new FileOutputStream(fileConfindecialidad);
    HtmlConverter.convertToPdf(contentConfidencialidad, fileOutputStreamConfidencialidad);

    return fileConfindecialidad;
  }

  public String generarArchivoPLD(AltaDTO altaDTO) throws IOException {
    String filePLD = "PLD.pdf";
    String fileNamePLD = "PLD.html";

    byte[] bytesPLD = Files.readAllBytes(Paths.get(fileNamePLD));
    String contentPLD = new String(bytesPLD, StandardCharsets.UTF_8);

    RazonSocialDTO razonSocialDTO = obtenerRazonSocial(altaDTO.getRazonSocial());

    LocalDate fechaAlta = altaDTO.getFechaAlta().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    String[] domicilio = altaDTO.getDomicilioColaborador().split(" ");
    String numeroCasa = domicilio[domicilio.length - 1];
    String calle = altaDTO.getDomicilioColaborador().replace(numeroCasa, "");


    //Files.deleteIfExists(Paths.get(fileConfindecialidad));

    Locale locale = new Locale("es","MX");

    contentPLD =
            contentPLD
                    .replace("var_ciudadEmpleado",altaDTO.getPoblacionColaborador())
                    .replace("var_estadoEmpleado",altaDTO.getEstadoColaborador())
                    .replace("var_ciudad",altaDTO.getCiudadContratacion())
                    .replace("var_estado",altaDTO.getEstadoContratacion())
                    .replace("var_dia",String.valueOf(fechaAlta.getDayOfMonth()))
                    .replace("var_mes",fechaAlta.getMonth().getDisplayName(TextStyle.FULL,locale))
                    .replace("var_anio",String.valueOf(fechaAlta.getYear()))
                    .replace("var_nombreEmpleado",altaDTO.getNombreColaborador().toUpperCase())
                    .replace("var_calleEmpleado",calle)
                    .replace("var_coloniaEmpleado",altaDTO.getColoniaColaborador())
                    .replace("var_cpEmpleado",altaDTO.getCpColaborador())
                    .replace("var_numeroCasaEmpleado",numeroCasa)
                    .replace("var_razonSocial",razonSocialDTO.getRazonSocial());

    OutputStream fileOutputStreamConfidencialidad = new FileOutputStream(filePLD);
    HtmlConverter.convertToPdf(contentPLD, fileOutputStreamConfidencialidad);

    return filePLD;
  }

  public RazonSocialDTO obtenerRazonSocial(String razonSocialString){
    Objects.requireNonNull(razonSocialString,"La razon social no debe ser nulo");

    RazonSocialDTO razonSocialDTO = new RazonSocialDTO();

    razonSocialDTO.setRazonSocial("FIN UTIL, S.A. DE C.V SOFOM ER.");
    razonSocialDTO.setRfc("FUT070924EQ6");
    razonSocialDTO.setDomicilioFiscal("Avenida Independencia, número 821, Colonia Centro Sinaloa, " +
            "Código Postal 80128 en la ciudad de Culiacán, Sinaloa.");
    razonSocialDTO.setNombreCorto("FIN UTIL");

    if ( razonSocialString.equalsIgnoreCase("COMFU")){
      razonSocialDTO.setRfc("COM150804TQ9");
      razonSocialDTO.setRazonSocial("COMFU S.A. DE C.V.");
      razonSocialDTO.setNombreCorto("COMFU");
      razonSocialDTO.setDomicilioFiscal("Rafael Buelna 162 ote col Centro CP 80000 en la ciudad de " +
              "Culiacán, Sinaloa");
    }

    return razonSocialDTO;
  }

  public byte[] zipDocuments(List<String> documentos, String nombreColaborador, HttpServletResponse response ) throws IOException {
    String nombreArchivoZip = nombreColaborador.trim().toUpperCase()+".zip";
    File fileZip = new File(nombreArchivoZip);
    FileOutputStream out = new FileOutputStream(fileZip);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byteArrayOutputStream.writeTo(out);
    ZipOutputStream zout = new ZipOutputStream(out);

    for(String documento: documentos) {
      File fileToZip = new File(documento);
      FileInputStream fileInputStream = new FileInputStream(fileToZip);
      ZipEntry zip = new ZipEntry(fileToZip.getName());
      zout.putNextEntry(zip);
      byte[] bytes = new byte[1024];
      int length;
      while((length = fileInputStream.read(bytes)) >= 0){
        zout.write(bytes, 0, length);
      }
      fileInputStream.close();

      zout.closeEntry();
    }
    zout.close();

    Path path = Paths.get(fileZip.getName());
    byte[] bytesResponse = Files.readAllBytes(path);


    response.setContentType("application/zip");
    response.setContentLength(bytesResponse.length);
    response.setHeader("Content-Disposition",
            "attachment; "
                    + String.format("filename*=" + StandardCharsets.UTF_8.name() + "''%s", nombreArchivoZip));
    ServletOutputStream outputStream = response.getOutputStream();
    FileCopyUtils.copy(bytesResponse,outputStream);
    outputStream.close();

    return bytesResponse;
    //Files.deleteIfExists(Paths.get(nombreArchivoZip));
  }
}
