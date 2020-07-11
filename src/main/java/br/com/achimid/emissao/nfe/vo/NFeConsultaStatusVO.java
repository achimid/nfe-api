package br.com.achimid.emissao.nfe.vo;

import br.com.achimid.emissao.nfe.NFeErroStatus;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NFeConsultaStatusVO extends NFeErroStatus {

    private String chave;

    private ConfiguracoesNfe config;
    private TRetConsSitNFe retorno;

}
