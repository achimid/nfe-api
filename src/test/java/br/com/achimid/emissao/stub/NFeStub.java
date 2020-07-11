package br.com.achimid.emissao.stub;

import br.com.achimid.emissao.nfe.cancelamento.NFeCancelamento;
import br.com.achimid.emissao.nfe.consulta.NFeConsultaStatus;
import br.com.achimid.emissao.nfe.download.NFeDownload;
import br.com.achimid.emissao.nfe.envio.entity.*;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;

import java.util.Arrays;

public class NFeStub {

    public static String chaveNFe_Valida() { return "NFe35200636606999000164550020000000091000000097"; }

    public static NFeIde NFe_Ide_Valid() {
        var ide = new NFeIde();

        ide.setNatOp("NOTA FISCAL CONSUMIDOR ELETRONICA");
        ide.setTpEmis("1");
        ide.setTpNF("1");
        ide.setIdDest("1");
        ide.setMod(DocumentoEnum.NFE.getModelo());
        ide.setCMunFG("5219753");
        ide.setTpImp("1");
        ide.setFinNFe("1");
        ide.setIndFinal("1");
        ide.setIndPres("1");
        ide.setProcEmi("0");
        ide.setVerProc("1.0");

        return ide;
    }

    public static NFeDestinatarioEndereco NFe_DestinatarioEndereco_Valid() {
        var destinatarioEndereco = new NFeDestinatarioEndereco();

        destinatarioEndereco.setXLgr("Rua: Teste");
        destinatarioEndereco.setNro("0");
        destinatarioEndereco.setXBairro("TESTE");
        destinatarioEndereco.setCMun("4109708");
        destinatarioEndereco.setXMun("IBAITI");
        destinatarioEndereco.setUF("SP");
        destinatarioEndereco.setCEP("84900000");
        destinatarioEndereco.setCPais("1058");
        destinatarioEndereco.setXPais("Brasil");
        destinatarioEndereco.setFone("4845454545");

        return destinatarioEndereco;
    }

    public static NFeDestinatario NFe_Destinatario_Valid() {
        var destinatario = new NFeDestinatario();

        destinatario.setEmail("teste@test");
        destinatario.setIndIEDest("9");

        destinatario.setEndereco(NFe_DestinatarioEndereco_Valid());

        return destinatario;

    }

    public static NFeProdutoItem NFe_ProdutoItem_Valid() {
        var produtoItem = new NFeProdutoItem();

        produtoItem.setCProd("7898480650104");
        produtoItem.setCEAN("7898480650104");
        produtoItem.setXProd("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        produtoItem.setNCM("27101932");
        produtoItem.setCEST("0600500");
        produtoItem.setIndEscala("S");
        produtoItem.setCFOP("5405");
        produtoItem.setUCom("UN");
        produtoItem.setQCom("1.0000");
        produtoItem.setVUnCom("13.0000");
        produtoItem.setVProd("13.00");
        produtoItem.setCEANTrib("7898480650104");
        produtoItem.setUTrib("UN");
        produtoItem.setQTrib("1.0000");
        produtoItem.setVUnTrib("13.0000");
        produtoItem.setIndTot("1");

        return produtoItem;
    }

    public static NFeProdutoImposto NFe_ProdutoImposto_Valid() {
        var produtoImposto = new NFeProdutoImposto();

        var produtoImpostoICMS = new NFeProdutoImpostoICMS();

        var produtoImpostoPIS = new NFeProdutoImpostoPIS();
        var produtoImpostoPISAliquota = new NFeProdutoImpostoPISAliquota();

        var produtoImpostoCOFINS = new NFeProdutoImpostoCOFINS();
        var produtoImpostoCOFINSAliquota = new NFeProdutoImpostoCOFINSAliquota();


        produtoImpostoICMS.setOrig("0");
        produtoImpostoICMS.setCST("00");
        produtoImpostoICMS.setModBC("0");
        produtoImpostoICMS.setVBC("13.00");
        produtoImpostoICMS.setPICMS("7.00");
        produtoImpostoICMS.setVICMS("0.91");


        produtoImpostoPISAliquota.setCST("01");
        produtoImpostoPISAliquota.setVBC("13.00");
        produtoImpostoPISAliquota.setPPIS("1.65");
        produtoImpostoPISAliquota.setVPIS("0.21");


        produtoImpostoCOFINSAliquota.setCST("01");
        produtoImpostoCOFINSAliquota.setVBC("13.00");
        produtoImpostoCOFINSAliquota.setPCOFINS("7.60");
        produtoImpostoCOFINSAliquota.setVCOFINS("0.99");

        produtoImpostoCOFINS.setConfisAliquota(produtoImpostoCOFINSAliquota);
        produtoImpostoPIS.setPisAliquota(produtoImpostoPISAliquota);

        produtoImposto.setCofins(produtoImpostoCOFINS);
        produtoImposto.setIcms(produtoImpostoICMS);
        produtoImposto.setPis(produtoImpostoPIS);

        return produtoImposto;
    }

    public static NFeProduto NFe_Produto_Valid() {
        var produto = new NFeProduto();

        produto.setItem(NFe_ProdutoItem_Valid());
        produto.setImposto(NFe_ProdutoImposto_Valid());

        return produto;
    }


    public static NFeTotal NFe_Total_Valid() {
        var total = new NFeTotal();
        var totalICMS = new NFeTotalICMS();

        totalICMS.setVBC("13.00");
        totalICMS.setVICMS("0.91");
        totalICMS.setVICMSDeson("0.00");
        totalICMS.setVFCP("0.00");
        totalICMS.setVFCPST("0.00");
        totalICMS.setVFCPSTRet("0.00");
        totalICMS.setVBCST("0.00");
        totalICMS.setVST("0.00");
        totalICMS.setVProd("13.00");
        totalICMS.setVFrete("0.00");
        totalICMS.setVSeg("0.00");
        totalICMS.setVDesc("0.00");
        totalICMS.setVII("0.00");
        totalICMS.setVIPI("0.00");
        totalICMS.setVIPIDevol("0.00");
        totalICMS.setVPIS("0.21");
        totalICMS.setVCOFINS("0.99");
        totalICMS.setVOutro("0.00");
        totalICMS.setVNF("13.00");

        total.setIcmsTotal(totalICMS);

        return total;
    }

    public static NFePagamento NFe_Pagamento_Valid() {
        var pagamento = new NFePagamento();

        pagamento.setTPag("01");
        pagamento.setVPag("13.00");

        return pagamento;
    }


    public static NFeTransporte NFe_Transporte_Valid() {
        var transporte = new NFeTransporte();

        transporte.setModFrete("9");

        return transporte;
    }


    public static NFe NFe_Valid() {
        var nfe = new NFe();

        nfe.setIde(NFe_Ide_Valid());
        nfe.setDestinatario(NFe_Destinatario_Valid());
        nfe.setProdutos(Arrays.asList(NFe_Produto_Valid()));
        nfe.setTransporte(NFe_Transporte_Valid());
        nfe.setPagamento(NFe_Pagamento_Valid());
        nfe.setTotal(NFe_Total_Valid());

        return nfe;
    }

    public static NFe NFe_Invalida_Null() {
        return null;
    }

    public static NFe NFe_Valida_Ide_Null() {
        var nfe = NFe_Valid();
        nfe.setIde(null);
        return nfe;
    }

    public static NFe NFe_Valida_Destinatario_Null() {
        var nfe = NFe_Valid();
        nfe.setDestinatario(null);
        return nfe;
    }

    public static NFe NFe_Valida_Produtos_Null() {
        var nfe = NFe_Valid();
        nfe.setProdutos(null);
        return nfe;
    }

    public static NFe NFe_Valida_Transporte_Null() {
        var nfe = NFe_Valid();
        nfe.setTransporte(null);
        return nfe;
    }

    public static NFe NFe_Valida_Pagamento_Null() {
        var nfe = NFe_Valid();
        nfe.setPagamento(null);
        return nfe;
    }

    public static NFe NFe_Valida_Total_Null() {
        var nfe = NFe_Valid();
        nfe.setTotal(null);
        return nfe;
    }


    public static NFeDownload NFeDownload_Valid() {
        var nfeDownload = new NFeDownload();
        nfeDownload.setChave(NFeStub.chaveNFe_Valida());
        return nfeDownload;
    }

    public static NFeCancelamento NFeCancelamento_Valid() {
        var nfeCancelamento = new NFeCancelamento();
        return nfeCancelamento;
    }

    public static NFeConsultaStatus NFeConsultaStatus() {
        var consultaStatus = new NFeConsultaStatus();
        consultaStatus.setChave(NFeStub.chaveNFe_Valida());
        return consultaStatus;
    }


}
