package com.ascencio.api_cuentas.repository;

import com.ascencio.api_cuentas.model.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {

    Optional<Cuenta> findByCliente(String cliente);

}
