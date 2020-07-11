package br.com.achimid.emissao.nfe;

import br.com.achimid.emissao.nfe.envio.NFeEnvioService;
import br.com.achimid.emissao.util.AbstractTest;
import br.com.achimid.emissao.stub.NFeStub;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NFeConsultaServiceErrorTest extends AbstractTest {

    @Autowired
    protected NFeEnvioService service;


    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe invalida, com parametro null, deve ocorrer uma exception.")
    public void enviaNFe_Invalida_Null(){
        service.enviaNFe(NFeStub.NFe_Invalida_Null());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe valida, com parametro idle null, deve ocorrer uma exception.")
    public void enviaNFe_Valida_Idle_Null(){
        service.enviaNFe(NFeStub.NFe_Valida_Ide_Null());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe valida, com parametro destinatario null, deve ocorrer uma exception.")
    public void enviaNFe_Valida_Destinatario_Null(){
        service.enviaNFe(NFeStub.NFe_Valida_Destinatario_Null());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe valida, com parametro produtos null, deve ocorrer uma exception.")
    public void enviaNFe_Valida_Produtos_Null(){
        service.enviaNFe(NFeStub.NFe_Valida_Produtos_Null());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe valida, com parametro transporte null, deve ocorrer uma exception.")
    public void enviaNFe_Valida_Transporte_Null(){
        service.enviaNFe(NFeStub.NFe_Valida_Transporte_Null());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe valida, com parametro pagamento null, deve ocorrer uma exception.")
    public void enviaNFe_Valida_Pagamento_Null(){
        service.enviaNFe(NFeStub.NFe_Valida_Pagamento_Null());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Enviando NFe valida, com parametro total null, deve ocorrer uma exception.")
    public void enviaNFe_Valida_Total_Null(){
        service.enviaNFe(NFeStub.NFe_Valida_Total_Null());
    }



}
