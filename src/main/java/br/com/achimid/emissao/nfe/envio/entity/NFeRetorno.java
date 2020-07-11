package br.com.achimid.emissao.nfe.envio.entity;

import br.com.swconsultoria.nfe.schema_4.enviNFe.TProtNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;
import lombok.Data;

@Data
public class NFeRetorno {

    private String tpAmb;
    private String verAplic;
    private String cStat;
    private String xMotivo;
    private String cuf;
    private String dhRecbto;
    private TRetEnviNFe.InfRec infRec;
    private TProtNFe protNFe;
    private String versao;

}
