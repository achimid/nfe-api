package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeProdutoImpostoCOFINS {

    // Existem varios outros tipos de impostos omitidos
    private NFeProdutoImpostoCOFINSAliquota confisAliquota;

}
