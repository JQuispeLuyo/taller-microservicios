package com.ascencio.api_cuentas.controller;

import com.ascencio.api_cuentas.model.Cuenta;
import com.ascencio.api_cuentas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CuentaCTest {

    @Autowired
    CuentaRepository cuentaRepository;

    @GetMapping("/getAllCuentas")
    public List<Cuenta> getAllCuentas() {

        return cuentaRepository.findAll();
    }

    @PostMapping(value = "/create")
    public Cuenta createCuenta(@Valid @RequestBody Cuenta cuenta) {

        return cuentaRepository.save(cuenta);
    }
    
}
