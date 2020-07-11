package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeProdutoImpostoPIS {

    // Existem varios outros tipos de impostos omitidos
    private NFeProdutoImpostoPISAliquota pisAliquota;

}
