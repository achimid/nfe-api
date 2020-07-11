package br.com.achimid.emissao.nfe.vo;

import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NFeEnvioVO {

    private int numeroNfe;
    private int serie;
    private String cnpj;
    private String modelo;
    private LocalDateTime dataEmissao;
    private String cnf;
    private String chaveNF;

    private ConfiguracoesNfe config;
    private TNFe.InfNFe.Ide ide;
    private TNFe.InfNFe.Emit emitente;
    private TNFe.InfNFe.Dest destinatario;
    private List<TNFe.InfNFe.Det> produtos;
    private TNFe.InfNFe.Total total;
    private TNFe.InfNFe.Transp transporte;
    private TNFe.InfNFe.Pag pagamento;

    private TEnviNFe enviNFe;
    private TRetEnviNFe retorno;

}
