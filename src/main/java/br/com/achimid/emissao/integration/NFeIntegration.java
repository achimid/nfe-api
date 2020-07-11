package br.com.achimid.emissao.integration;


import br.com.achimid.emissao.nfe.download.NFeDownload;
import br.com.achimid.emissao.nfe.vo.NFeCancelamentoVO;
import br.com.achimid.emissao.nfe.vo.NFeConsultaStatusVO;
import br.com.achimid.emissao.nfe.vo.NFeDownloadVO;
import br.com.achimid.emissao.nfe.vo.NFeEnvioVO;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.ConsultaDFeEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.PessoaEnum;
import br.com.swconsultoria.nfe.dom.enuns.StatusEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import br.com.swconsultoria.nfe.schema_4.enviNFe.*;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNFe.InfNFe;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;

import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import br.com.swconsultoria.nfe.util.ChaveUtil;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;
import br.com.swconsultoria.nfe.util.CancelamentoUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@Transactional(propagation = Propagation.NEVER)
public class NFeIntegration {

    public TRetConsStatServ consultaStatusSefaz(@NonNull final ConfiguracoesNfe config) {
        try {
            val statusServico = Nfe.statusServico(config, DocumentoEnum.NFE);
            log.info("# Status: % - %", statusServico.getCStat(), statusServico.getXMotivo());

            return statusServico;
        } catch (NfeException e) {
            log.error("Erro ao consultar status na Sefaz", e);
            throw new RuntimeException(e);
        }
    }

    public void consultaSituacaoNFe(@NonNull final NFeConsultaStatusVO consultaStatusVO) {

        val config = consultaStatusVO.getConfig();
        val chave = consultaStatusVO.getChave();

        try {
            val situacaoNFe = Nfe.consultaXml(config, chave, DocumentoEnum.NFE);
            log.info("# Status: % - %", situacaoNFe.getCStat(), situacaoNFe.getXMotivo());

            consultaStatusVO.setRetorno(situacaoNFe);
        } catch (Exception e) {
            log.error("Erro ao consultar situacao da NFe", e);
            throw new RuntimeException(e);
        }
    }

    public void enviaNFe(@NonNull final NFeEnvioVO nfeEnvioVO) {
        try {
            val numeroNfe = nfeEnvioVO.getNumeroNfe();
            val serie = nfeEnvioVO.getSerie();
            val cnpj = nfeEnvioVO.getCnpj();
            val config = nfeEnvioVO.getConfig();

            val ide = nfeEnvioVO.getIde();

            val tipoEmissao = ide.getTpEmis();
            val modelo = ide.getMod();

            val dataEmissao = LocalDateTime.now();
            val cnf = ChaveUtil.completarComZerosAEsquerda(String.valueOf(numeroNfe), 8);

            // MontaChave a NFe
            ChaveUtil chaveUtil = new ChaveUtil(config.getEstado(), cnpj, modelo, serie, numeroNfe, tipoEmissao, cnf, dataEmissao);
            val chave = chaveUtil.getChaveNF();
            val cdv = chaveUtil.getDigitoVerificador();

            InfNFe infNFe = new InfNFe();
            infNFe.setId(chave);
            infNFe.setVersao(ConstantesUtil.VERSAO.NFE);

            nfeEnvioVO.setChaveNF(chave);
            nfeEnvioVO.setDataEmissao(dataEmissao);
            nfeEnvioVO.setCnf(cnf);

            //Preenche o IDE baseado nas informações ja existentes

            ide.setCDV(cdv);
            ide.setCNF(cnf);
            ide.setCUF(config.getEstado().getCodigoUF());
            ide.setTpAmb(config.getAmbiente().getCodigo());
            ide.setSerie(String.valueOf(serie));
            ide.setNNF(String.valueOf(numeroNfe));
            ide.setDhEmi(XmlNfeUtil.dataNfe(dataEmissao));
            ide.setDhSaiEnt(XmlNfeUtil.dataNfe(dataEmissao));

            //Preenche IDE
            infNFe.setIde(ide);

            //Preenche Emitente
            infNFe.setEmit(nfeEnvioVO.getEmitente());

            //Preenche o Destinatario
            infNFe.setDest(nfeEnvioVO.getDestinatario());

            //Preenche os dados do Produto da Nfe e adiciona a Lista
            infNFe.getDet().addAll(nfeEnvioVO.getProdutos());

            //Preenche totais da NFe
            infNFe.setTotal(nfeEnvioVO.getTotal());

            //Preenche os dados de Transporte
            infNFe.setTransp(nfeEnvioVO.getTransporte());

            // Preenche dados Pagamento
            infNFe.setPag(nfeEnvioVO.getPagamento());

            TNFe nfe = new TNFe();
            nfe.setInfNFe(infNFe);

            // Monta EnviNfe
            TEnviNFe enviNFe = new TEnviNFe();
            enviNFe.setVersao(ConstantesUtil.VERSAO.NFE);
            enviNFe.setIdLote("1");
            enviNFe.setIndSinc("1");
            enviNFe.getNFe().add(nfe);

            // Monta e Assina o XML
            log.info("Montando NFe");
            enviNFe = Nfe.montaNfe(config, enviNFe, true);

            // Adiciona NFe no VO
            nfeEnvioVO.setEnviNFe(enviNFe);

            // Envia a Nfe para a Sefaz
            log.info("Enviando NFe");
            TRetEnviNFe retorno = Nfe.enviarNfe(config, enviNFe, DocumentoEnum.NFE);

            // Adiciona retorno no VO
            nfeEnvioVO.setRetorno(retorno);

            //Valida se o Retorno é Assincrono
            if (RetornoUtil.isRetornoAssincrono(retorno)) {
                //Pega o Recibo
                String recibo = retorno.getInfRec().getNRec();
                int tentativa = 0;
                TRetConsReciNFe retornoNfe = null;

                //Define Numero de tentativas que irá tentar a Consulta
                while (tentativa < 15) {
                    retornoNfe = Nfe.consultaRecibo(config, recibo, DocumentoEnum.NFE);
                    if (retornoNfe.getCStat().equals(StatusEnum.LOTE_EM_PROCESSAMENTO.getCodigo())) {
                        log.info("INFO: Lote Em Processamento, vai tentar novamente apos 1 Segundo.");
                        Thread.sleep(1000);
                        tentativa++;
                    } else {
                        break;
                    }
                }

                RetornoUtil.validaAssincrono(retornoNfe);
                log.info("# Status: " + retornoNfe.getProtNFe().get(0).getInfProt().getCStat() + " - " + retornoNfe.getProtNFe().get(0).getInfProt().getXMotivo());
                log.info("# Protocolo: " + retornoNfe.getProtNFe().get(0).getInfProt().getNProt());
                log.info("# XML Final: " + XmlNfeUtil.criaNfeProc(enviNFe, retornoNfe.getProtNFe().get(0)));

            } else {
                //Valida Retorno Sincrono
                RetornoUtil.validaSincrono(retorno);
                log.info("# Status: " + retorno.getProtNFe().getInfProt().getCStat() + " - " + retorno.getProtNFe().getInfProt().getXMotivo());
                log.info("# Protocolo: " + retorno.getProtNFe().getInfProt().getNProt());
                log.info("# Xml Final :" + XmlNfeUtil.criaNfeProc(enviNFe, retorno.getProtNFe()));
            }
        } catch (NfeException | JAXBException | InterruptedException e) {
            log.error("Erro ao enviar NFe", e);
            throw new RuntimeException(e);
        }
    }

    public void cancelaNFe(@NonNull final NFeCancelamentoVO nfeCancelamentoVO) {
        try {
            val config = nfeCancelamentoVO.getConfig();

            //Agora o evento pode aceitar uma lista de cancelaemntos para envio em Lote.
            Evento cancela = new Evento();

            //Informe a chave da Nota a ser Cancelada
            cancela.setChave(nfeCancelamentoVO.getChave());

            //Informe o protocolo da Nota a ser Cancelada
            cancela.setProtocolo(nfeCancelamentoVO.getProtocolo());

            //Informe o CNPJ do emitente
            cancela.setCnpj(nfeCancelamentoVO.getCNPJ());

            //Informe o Motivo do Cancelamento
            cancela.setMotivo(nfeCancelamentoVO.getMotivo());

            //Informe a data do Cancelamento
            cancela.setDataEvento(LocalDateTime.now());
            nfeCancelamentoVO.setDataCancelamento(cancela.getDataEvento());

            //Monta o Evento de Cancelamento
            TEnvEvento enviEvento = CancelamentoUtil.montaCancelamento(cancela, config);

            //Envia o Evento de Cancelamento
            TRetEnvEvento retorno = Nfe.cancelarNfe(config, enviEvento, true, DocumentoEnum.NFE);

            //Adicionando retorno no VO
            nfeCancelamentoVO.setRetorno(retorno);

            //Valida o Retorno do Cancelamento
            RetornoUtil.validaCancelamento(retorno);

            //Resultado
            retorno.getRetEvento().forEach( resultado -> {
                log.info("# Chave: " + resultado.getInfEvento().getChNFe());
                log.info("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
                log.info("# Protocolo: " + resultado.getInfEvento().getNProt());
            });

            //Cria ProcEvento de Cacnelamento
            String proc = CancelamentoUtil.criaProcEventoCancelamento(config, enviEvento, retorno.getRetEvento().get(0));

            //Adiciona ProcEvento no VO
            nfeCancelamentoVO.setProcEvento(proc);

            log.info("# ProcEvento : " + proc);
        } catch (NfeException | JAXBException e) {
            log.error("Erro ao cancelar NFe");
        }
    }

    public void downloadNFe(@NonNull final NFeDownloadVO nfeDownloadVO) {
        try {
            val config = nfeDownloadVO.getConfig();
            val cnpj = nfeDownloadVO.getCNPJ();

            val retorno = Nfe.distribuicaoDfe(config, PessoaEnum.JURIDICA, cnpj, ConsultaDFeEnum.CHAVE, nfeDownloadVO.getChave());
            nfeDownloadVO.setRetorno(retorno);

            if (StatusEnum.DOC_LOCALIZADO_PARA_DESTINATARIO.getCodigo().equals(retorno.getCStat())) {

                log.info("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
                log.info("# NSU Atual: " + retorno.getUltNSU());
                log.info("# Max NSU: " + retorno.getMaxNSU());
                log.info("# Versao: " + retorno.getVersao());

                //Aqui Recebe a Lista De XML (No Maximo 50 por Consulta)
                val listaDoc = retorno.getLoteDistDFeInt().getDocZip();
                nfeDownloadVO.setListaDoc(listaDoc);

                for (RetDistDFeInt.LoteDistDFeInt.DocZip docZip : listaDoc) {
                    log.info("# Schema: " + docZip.getSchema());

                    switch (docZip.getSchema()) {
                        case "resNFe_v1.01.xsd":
                            log.info("# Este é o XML em resumo, deve ser feito a Manifestação para o Objeter o XML Completo.");
                            break;
                        case "procNFe_v4.00.xsd":
                            log.info("# XML Completo.");
                            break;
                        case "procEventoNFe_v1.00.xsd":
                            log.info("# XML Evento.");
                            break;
                    }

                    //Transforma o GZip em XML
                    val xml = XmlNfeUtil.gZipToXml(docZip.getValue());
                    nfeDownloadVO.setXml(xml, docZip.getSchema(), docZip);
                    log.info("# XML: " + xml);
                }
            }
        } catch (NfeException | IOException e) {
            log.error("Erro ao efetuar NFe Download");
        }
    }
}
