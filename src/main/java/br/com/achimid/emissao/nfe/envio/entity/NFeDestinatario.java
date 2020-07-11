package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeDestinatario {

    private String CNPJ;
    private String XNome;
    private String Email;
    private String IndIEDest;

    private NFeDestinatarioEndereco endereco;

}
