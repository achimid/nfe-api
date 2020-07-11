package br.com.achimid.emissao.nfe.cancelamento;

import br.com.achimid.emissao.nfe.NFeErroStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Document
@EqualsAndHashCode(callSuper=false)
public class NFeCancelamento extends NFeErroStatus {

    @Id
    private String uuid = UUID.randomUUID().toString();

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private String chave;
    private String protocolo;
    private String motivo;
    private String CNPJ;

    private String idLote;
    private String tpAmb;
    private String verAplic;
    private String cOrgao;
    private String cStat;
    private String xMotivo;
    private String versao;
    private LocalDateTime dataCancelamento;

}
