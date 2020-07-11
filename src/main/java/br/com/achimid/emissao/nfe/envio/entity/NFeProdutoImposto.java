package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeProdutoImposto {

    private NFeProdutoImpostoICMS icms;
    private NFeProdutoImpostoPIS pis;
    private NFeProdutoImpostoCOFINS cofins;

}
