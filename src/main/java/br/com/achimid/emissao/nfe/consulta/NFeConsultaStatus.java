package br.com.achimid.emissao.nfe.consulta;

import br.com.achimid.emissao.nfe.NFeErroStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NFeConsultaStatus extends NFeErroStatus {

    public NFeConsultaStatus(@NonNull final String chave) {
        this.chave = chave;
    }

    @Id
    private String uuid = UUID.randomUUID().toString();

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private String chave;

    private String tpAmb;
    private String verAplic;
    private String cStat;
    private String xMotivo;
    private String cuf;
    private String dhRecbto;
    private String chNFe;
    private String versao;

}
