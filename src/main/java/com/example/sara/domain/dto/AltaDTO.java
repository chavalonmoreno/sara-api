package com.example.sara.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AltaDTO {
    private String sucursal;
    private String razonSocial;
    private String nombreColaborador;
    private Date fechaNacimiento;
    private String paisOrigen;
    private String nacionalidad;
    private String sexo;
    private String ocupacionActual;
    private String nombrePadre;
    private String nombreMadre;
    private String rfcEmpleado;
    private String curpEmpleado;
    private String numeroSeguro;
    private String estadoCivil;
    private String puesto;
    private Double sueldoDiario;
    private Double sueldoBrutoMensual;
    private String domicilioColaborador;
    private String coloniaColaborador;
    private String cpColaborador;
    private String poblacionColaborador;
    private String estadoColaborador;
    private String telefonoColaborador;
    private String telefonoOficina;
    private String extensionOficina;
    private String celularColaborador;
    private String correoColaborador;
    private String numeroInfonavit;
    private Double factorDescuento;
    private String cuentaBancomer;
    private String tallaColaborador;


    private Date fechaAlta;
    private String estadoContratacion;
    private String ciudadContratacion;
    private String estudiosColaborador;
    private String experienciaLaboral;
    private String diasPruebaContrato;

    private String nombreBeneficiario;
    private Date fechaNacimientoBeneficiario;
    private String parentescoBeneficiario;
}
