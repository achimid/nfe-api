package br.com.achimid.emissao.emitente;

import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Emitente {

    private String CNPJ;
    private String xNome;
    private String IE;
    private String CRT;
    private String estado;

    private EmitenteEndereco endereco;
    private EmitenteCertificado certificado;
    private EmitenteMetadados metadados;

    private transient ConfiguracoesNfe config;

}
