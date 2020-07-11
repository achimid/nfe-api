package br.com.achimid.emissao.emitente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmitenteEndereco {

    private String xLgr;
    private String nro;
    private String xCpl;
    private String xBairro;
    private String cMun;
    private String xMun;
    private String UF;
    private String CEP;
    private String cPais;
    private String xPais;
    private String fone;

}
