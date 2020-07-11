package br.com.achimid.emissao.nfe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Data
public class NFeErroStatus {

    protected boolean sucesso;
    protected String erroMensagem;

    @JsonIgnore
    protected String erro;

    public void setError(@NonNull Throwable t) {
        this.sucesso = false;
        this.erro = ExceptionUtils.getStackTrace(t);
        this.erroMensagem = t.getCause().getMessage();
    }

}
