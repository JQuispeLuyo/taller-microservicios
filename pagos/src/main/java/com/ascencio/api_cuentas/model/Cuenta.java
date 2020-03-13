package com.ascencio.api_cuentas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cuenta")
public class Cuenta {

    @Id
    private String _id;
    private String cliente;
    private Double saldo;
}
