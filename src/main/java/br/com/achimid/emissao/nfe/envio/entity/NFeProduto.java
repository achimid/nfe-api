package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeProduto {

    private String nItem;
    private NFeProdutoItem item;
    private NFeProdutoImposto imposto;

}
