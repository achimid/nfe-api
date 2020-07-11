package br.com.achimid.emissao.nfe.vo;

import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NFeCancelamentoVO {

    private String chave;
    private String protocolo;
    private String motivo;
    private String CNPJ;

    private ConfiguracoesNfe config;
    private LocalDateTime dataCancelamento;
    private TEnvEvento enviEvento;
    private TRetEnvEvento retorno;
    private String procEvento;

}
