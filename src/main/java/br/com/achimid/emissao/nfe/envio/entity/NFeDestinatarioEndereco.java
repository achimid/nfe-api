package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeDestinatarioEndereco {

    private String xLgr;
    private String nro;
    private String xBairro;
    private String cMun;
    private String xMun;
    private String UF;
    private String CEP;
    private String cPais;
    private String xPais;
    private String fone;

}
