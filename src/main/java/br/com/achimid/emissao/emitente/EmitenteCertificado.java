package br.com.achimid.emissao.emitente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmitenteCertificado {

    private String caminho;
    private String senha;

}
