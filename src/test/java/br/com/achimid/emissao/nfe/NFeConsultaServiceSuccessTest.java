package br.com.achimid.emissao.nfe;

import br.com.achimid.emissao.emitente.EmitenteService;
import br.com.achimid.emissao.nfe.cancelamento.NFeCancelamentoService;
import br.com.achimid.emissao.nfe.consulta.NFeConsultaService;
import br.com.achimid.emissao.nfe.download.NFeDownloadService;
import br.com.achimid.emissao.nfe.envio.NFeEnvioService;
import br.com.achimid.emissao.stub.NFeStub;
import br.com.achimid.emissao.util.AbstractTest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
public class NFeConsultaServiceSuccessTest extends AbstractTest {

    @Autowired
    protected NFeEnvioService envioService;

    @Autowired
    protected NFeDownloadService downloadService;

    @Autowired
    protected NFeCancelamentoService cancelamentoService;

    @Autowired
    protected NFeConsultaService consultaService;

    @Autowired
    protected EmitenteService emitenteService;


    @Test
    @DisplayName("Enviando NFe valida, deve ocorrer com sucesso.")
    public void enviaNFe_Sucesso(){
        var nfe = NFeStub.NFe_Valid();
        envioService.enviaNFe(nfe);

        Assert.notNull(nfe, "nfe não pode ser nullo");
        Assert.isTrue(nfe.isSucesso(), "deve retornar sucesso");
    }

    @Test
    @DisplayName("Download NFe por chave, deve ocorrer com sucesso.")
    public void downloadNFeByChave_Sucesso(){
        var nfeDownload = NFeStub.NFeDownload_Valid();
        downloadService.downloadNFe(nfeDownload);

        Assert.notNull(nfeDownload, "nfeDownload não pode ser nullo");
        Assert.isTrue(nfeDownload.isSucesso(), "deve retornar sucesso");
    }

    @Test
    @DisplayName("Cancelando NFe valida, deve ocorrer com sucesso.")
    public void cancelaNFe_Sucesso(){
        var nfeCancelamento = NFeStub.NFeCancelamento_Valid();
        cancelamentoService.cancelaNFe(nfeCancelamento);

        Assert.notNull(nfeCancelamento, "nfeCancelamento não pode ser nullo");
        Assert.isTrue(nfeCancelamento.isSucesso(), "deve retornar sucesso");
    }


    @Test
    @DisplayName("Consultando o status da Nota Fiscal, deve ocorrer com sucesso.")
    public void consultaStatusNFe_Sucesso(){
        var consultaStatus = NFeStub.NFeConsultaStatus();
        consultaService.consultaStatusNFe(consultaStatus);

        Assert.notNull(consultaStatus, "consultaStatus não pode ser nullo");
        Assert.isTrue(consultaStatus.isSucesso(), "deve retornar sucesso");
    }



    @Test
    @DisplayName("Consultando o status do Emitente na Sefaz, deve ocorrer com sucesso.")
    public void consultaStatusEmitente_Sucesso(){
        var statusEmitente = emitenteService.consultaStatusEmitente();
        Assert.notNull(statusEmitente, "status não pode ser nullo");
    }

}
