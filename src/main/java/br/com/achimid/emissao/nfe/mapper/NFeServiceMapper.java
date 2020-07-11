package br.com.achimid.emissao.nfe.mapper;

import br.com.achimid.emissao.emitente.Emitente;
import br.com.achimid.emissao.emitente.EmitenteEndereco;
import br.com.achimid.emissao.emitente.EmitenteStatus;
import br.com.achimid.emissao.nfe.cancelamento.NFeCancelamento;
import br.com.achimid.emissao.nfe.consulta.NFeConsultaStatus;
import br.com.achimid.emissao.nfe.download.NFeDownload;
import br.com.achimid.emissao.nfe.envio.entity.*;
import br.com.achimid.emissao.nfe.vo.NFeCancelamentoVO;
import br.com.achimid.emissao.nfe.vo.NFeConsultaStatusVO;
import br.com.achimid.emissao.nfe.vo.NFeDownloadVO;
import br.com.achimid.emissao.nfe.vo.NFeEnvioVO;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEvento;
import br.com.swconsultoria.nfe.schema_4.enviNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Imposto.PIS.PISAliq;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Det.Prod;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe.Total.ICMSTot;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TProcEvento;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetCancNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import lombok.NonNull;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface NFeServiceMapper {

    @Mapping(target = "status", source = "CStat")
    @Mapping(target = "motivo", source = "XMotivo")
    EmitenteStatus from(TRetConsStatServ retornoStatus);

    NFeConsultaStatus from(TRetConsSitNFe retornoStatus);

    TNFe.InfNFe.Emit from(@NonNull final Emitente emitente);

    TEnderEmi from(@NonNull final EmitenteEndereco emitenteEndereco);

    default NFeEnvioVO from(@NonNull final NFe nfe, @NonNull final Emitente emitente) {

        // Informações do emitente
        val emit = this.from(emitente);
        val emitEnd = this.from(emitente.getEndereco());
        emit.setEnderEmit(emitEnd);

        // Cabecalho da nota fiscal
        val ide = this.from(nfe.getIde());

        // Informações do destinatario
        val destinatario = this.from(nfe.getDestinatario());
        val destinatarioEnd = this.from(nfe.getDestinatario().getEndereco());
        destinatario.setEnderDest(destinatarioEnd);

        // Informações dos produtos
        val produtos = nfe.getProdutos().stream()
                .map(prod -> this.from(prod))
                .collect(Collectors.toUnmodifiableList());

        // Informações do total da nota
        val total = this.from(nfe.getTotal());

        // Informações do transporte
        val transporte = this.from(nfe.getTransporte());

        // Informações do pagamento
        val pagamento = this.from(nfe.getPagamento());

        val vo = NFeEnvioVO.builder()
            .cnpj(emitente.getCNPJ())
            .modelo(DocumentoEnum.NFE.getModelo())
            .config(emitente.getConfig())
                .emitente(emit)
                .ide(ide)
                .produtos(produtos)
                .total(total)
                .transporte(transporte)
                .pagamento(pagamento)
            .build();

        return vo;
    }

    InfNFe.Ide from(@NonNull final NFeIde ide);

    InfNFe.Dest from(@NonNull final NFeDestinatario destinatario);

    TEndereco from(@NonNull final NFeDestinatarioEndereco destinatarioEndereco);

    Prod from(@NonNull final NFeProdutoItem produtoItem);

    default Det from(@NonNull final NFeProduto produto) {
        val prod = new Det();

        prod.setProd(this.from(produto.getItem()));
        prod.setImposto(this.from(produto.getImposto()));

        return prod;
    }

    // O imposto informado esta fixo, precisa ser melhorado a maneira com que isso é feita
    default Imposto from(@NonNull final NFeProdutoImposto produtoImposto) {
        Imposto imposto = new Imposto();

        ICMS icms = new ICMS();

        ICMS.ICMS00 icms00 = new ICMS.ICMS00();
        icms00.setOrig(produtoImposto.getIcms().getOrig());
        icms00.setCST(produtoImposto.getIcms().getCST());
        icms00.setModBC(produtoImposto.getIcms().getModBC());
        icms00.setVBC(produtoImposto.getIcms().getVBC());
        icms00.setPICMS(produtoImposto.getIcms().getPICMS());
        icms00.setVICMS(produtoImposto.getIcms().getVICMS());

        icms.setICMS00(icms00);

        PIS pis = new PIS();
        PISAliq pisAliq = new PISAliq();
        pisAliq.setCST(produtoImposto.getPis().getPisAliquota().getCST());
        pisAliq.setVBC(produtoImposto.getPis().getPisAliquota().getVBC());
        pisAliq.setPPIS(produtoImposto.getPis().getPisAliquota().getPPIS());
        pisAliq.setVPIS(produtoImposto.getPis().getPisAliquota().getVPIS());
        pis.setPISAliq(pisAliq);

        COFINS cofins = new COFINS();
        COFINSAliq cofinsAliq = new COFINSAliq();
        cofinsAliq.setCST(produtoImposto.getCofins().getConfisAliquota().getCST());
        cofinsAliq.setVBC(produtoImposto.getCofins().getConfisAliquota().getVBC());
        cofinsAliq.setPCOFINS(produtoImposto.getCofins().getConfisAliquota().getPCOFINS());
        cofinsAliq.setVCOFINS(produtoImposto.getCofins().getConfisAliquota().getVCOFINS());
        cofins.setCOFINSAliq(cofinsAliq);

        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoICMS(icms));
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoPIS(pis));
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoCOFINS(cofins));

        return imposto;
    }

    // O imposto informado esta fixo, precisa ser melhorado a maneira com que isso é feita
    default InfNFe.Total from(@NonNull final NFeTotal nfeTotal) {

        Total total = new Total();
        ICMSTot icmstot = new ICMSTot();
        icmstot.setVBC(nfeTotal.getIcmsTotal().getVBC());
        icmstot.setVICMS(nfeTotal.getIcmsTotal().getVICMS());
        icmstot.setVICMSDeson(nfeTotal.getIcmsTotal().getVICMSDeson());
        icmstot.setVFCP(nfeTotal.getIcmsTotal().getVFCP());
        icmstot.setVFCPST(nfeTotal.getIcmsTotal().getVFCPST());
        icmstot.setVFCPSTRet(nfeTotal.getIcmsTotal().getVFCPSTRet());
        icmstot.setVBCST(nfeTotal.getIcmsTotal().getVBCST());
        icmstot.setVST(nfeTotal.getIcmsTotal().getVST());
        icmstot.setVProd(nfeTotal.getIcmsTotal().getVProd());
        icmstot.setVFrete(nfeTotal.getIcmsTotal().getVFrete());
        icmstot.setVSeg(nfeTotal.getIcmsTotal().getVSeg());
        icmstot.setVDesc(nfeTotal.getIcmsTotal().getVDesc());
        icmstot.setVII(nfeTotal.getIcmsTotal().getVII());
        icmstot.setVIPI(nfeTotal.getIcmsTotal().getVIPI());
        icmstot.setVIPIDevol(nfeTotal.getIcmsTotal().getVIPIDevol());
        icmstot.setVPIS(nfeTotal.getIcmsTotal().getVPIS());
        icmstot.setVCOFINS(nfeTotal.getIcmsTotal().getVCOFINS());
        icmstot.setVOutro(nfeTotal.getIcmsTotal().getVOutro());
        icmstot.setVNF(nfeTotal.getIcmsTotal().getVNF());
        total.setICMSTot(icmstot);

        return total;
    }

    InfNFe.Transp from(@NonNull final NFeTransporte transporte);

    // Existem outras formas de pagamento
    default InfNFe.Pag from(@NonNull final NFePagamento pagamento) {

        InfNFe.Pag pag = new InfNFe.Pag();
        InfNFe.Pag.DetPag detPag = new InfNFe.Pag.DetPag();
        detPag.setTPag(pagamento.getTPag());
        detPag.setVPag(pagamento.getVPag());
        pag.getDetPag().add(detPag);

        return pag;
    }


    default NFe merge(@NonNull final NFe nfe, @NonNull final NFeEnvioVO nfeEnvioVO) {
        nfe.setChaveNF(nfeEnvioVO.getChaveNF());
        nfe.setDataEmissao(nfeEnvioVO.getDataEmissao());
        nfe.setCnf(nfeEnvioVO.getCnf());

        nfe.setIdLote(nfeEnvioVO.getEnviNFe().getIdLote());
        nfe.setIndSinc(nfeEnvioVO.getEnviNFe().getIndSinc());

        nfe.setRetorno(this.from(nfeEnvioVO.getRetorno()));

        return nfe;
    }

    NFeRetorno from(@NonNull final TRetEnviNFe retEnviNFe);

    NFeCancelamentoVO from(@NonNull final NFeCancelamento nfeCancelamento);

    default NFeCancelamentoVO from(@NonNull final NFeCancelamento nfeCancelamento, @NonNull final Emitente emitente) {
        val cancelamentoVO = this.from(nfeCancelamento);

        cancelamentoVO.setConfig(emitente.getConfig());
        cancelamentoVO.setCNPJ(emitente.getCNPJ());
        nfeCancelamento.setCNPJ(emitente.getCNPJ());

        return cancelamentoVO;
    }

    default NFeCancelamento merge(@NonNull final NFeCancelamento nfeCancelamento, @NonNull final NFeCancelamentoVO cancelamentoVO) {

        nfeCancelamento.setIdLote(cancelamentoVO.getRetorno().getIdLote());
        nfeCancelamento.setTpAmb(cancelamentoVO.getRetorno().getTpAmb());
        nfeCancelamento.setVerAplic(cancelamentoVO.getRetorno().getVerAplic());
        nfeCancelamento.setCOrgao(cancelamentoVO.getRetorno().getCOrgao());
        nfeCancelamento.setCStat(cancelamentoVO.getRetorno().getCStat());
        nfeCancelamento.setXMotivo(cancelamentoVO.getRetorno().getXMotivo());
        nfeCancelamento.setVersao(cancelamentoVO.getRetorno().getVersao());
        nfeCancelamento.setDataCancelamento(cancelamentoVO.getDataCancelamento());
        nfeCancelamento.setProtocolo(cancelamentoVO.getProtocolo());

        return nfeCancelamento;
    }

    NFeDownloadVO from(@NonNull final NFeDownload nfeDownload);

    default NFeDownloadVO from(@NonNull final NFeDownload nfeDownload, @NonNull final Emitente emitente) {
        val downloadVO = this.from(nfeDownload);

        downloadVO.setConfig(emitente.getConfig());
        downloadVO.setCNPJ(emitente.getCNPJ());
        nfeDownload.setCNPJ(emitente.getCNPJ());

        return downloadVO;
    }

    default NFeDownload merge(@NonNull final NFeDownload nfeDownload, @NonNull final NFeDownloadVO downloadVO) {

        val first = downloadVO.getXmlList().stream().findFirst();
        first.ifPresent(dowlxml -> {
            nfeDownload.setXml(dowlxml.getXml());
            nfeDownload.setSchema(dowlxml.getSchema());
        });

        return nfeDownload;
    }


    NFeConsultaStatusVO from(@NonNull final NFeConsultaStatus consultaStatus);

    default NFeConsultaStatusVO from(@NonNull final NFeConsultaStatus consultaStatus, @NonNull final Emitente emitente) {
        val consultaVO = this.from(consultaStatus);

        consultaVO.setConfig(emitente.getConfig());

        return consultaVO;
    }

    default NFeConsultaStatus merge(@NonNull final NFeConsultaStatus consultaStatus, @NonNull final NFeConsultaStatusVO consultaStatusVO) {

        consultaStatus.setTpAmb(consultaStatusVO.getRetorno().getTpAmb());
        consultaStatus.setVerAplic(consultaStatusVO.getRetorno().getVerAplic());
        consultaStatus.setCStat(consultaStatusVO.getRetorno().getCStat());
        consultaStatus.setXMotivo(consultaStatusVO.getRetorno().getXMotivo());
        consultaStatus.setCuf(consultaStatusVO.getRetorno().getCUF());
        consultaStatus.setDhRecbto(consultaStatusVO.getRetorno().getDhRecbto());
        consultaStatus.setChNFe(consultaStatusVO.getRetorno().getChNFe());
        consultaStatus.setVersao(consultaStatusVO.getRetorno().getVersao());

        return consultaStatus;
    }
}
