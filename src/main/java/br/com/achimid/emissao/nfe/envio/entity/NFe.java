package br.com.achimid.emissao.nfe.envio.entity;

import br.com.achimid.emissao.emitente.Emitente;
import br.com.achimid.emissao.nfe.NFeErroStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document
@EqualsAndHashCode(callSuper=false)
public class NFe extends NFeErroStatus {

    @Id
    private String uuid = UUID.randomUUID().toString();

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private int numeroNfe;
    private int serie;
    private String cnpj;
    private String modelo;
    private LocalDateTime dataEmissao;
    private String cnf;
    private String chaveNF;
    private String idLote;
    private String indSinc;

    private Emitente emitente;
    private NFeIde ide;
    private NFeDestinatario destinatario;
    private List<NFeProduto> produtos;
    private NFeTotal total;
    private NFeTransporte transporte;
    private NFePagamento pagamento;

    private NFeRetorno retorno;
}
