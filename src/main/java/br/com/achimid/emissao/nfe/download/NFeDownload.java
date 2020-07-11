package br.com.achimid.emissao.nfe.download;

import br.com.achimid.emissao.nfe.NFeErroStatus;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NFeDownload extends NFeErroStatus {

    public NFeDownload(@NonNull final String chave) {
        this.chave = chave;
    }

    @Id
    private String uuid = UUID.randomUUID().toString();

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private String chave;
    private String CNPJ;

    private String schema;
    private String xml;

    private ConfiguracoesNfe config;
    private RetDistDFeInt retorno;

}
