package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeProdutoImpostoICMS {

    private String orig;
    private String CST;
    private String modBC;
    private String VBC;
    private String PICMS;
    private String VICMS;

}
