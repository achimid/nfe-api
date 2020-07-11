package br.com.achimid.emissao.nfe.vo;

import br.com.achimid.emissao.nfe.NFeErroStatus;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class NFeDownloadVO extends NFeErroStatus {

    private String CNPJ;
    private String NSU;
    private String chave;

    private ConfiguracoesNfe config;
    private RetDistDFeInt retorno;
    private List<RetDistDFeInt.LoteDistDFeInt.DocZip> listaDoc;

    private List<NFeDownloadXml> xmlList = new ArrayList<>();

    public void setXml(String xml, String schema, RetDistDFeInt.LoteDistDFeInt.DocZip docZip) {
        this.getXmlList().add(new NFeDownloadXml(xml, schema, docZip));
    }

    @Data
    @AllArgsConstructor
    public class NFeDownloadXml {

        private String xml;
        private String schema;
        private RetDistDFeInt.LoteDistDFeInt.DocZip docZip;

    }

}
