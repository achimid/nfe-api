package br.com.achimid.emissao.nfe.cancelamento;

import br.com.achimid.emissao.nfe.consulta.NFeConsultaService;
import br.com.achimid.emissao.nfe.consulta.NFeConsultaStatus;
import br.com.achimid.emissao.nfe.envio.NFeEnvioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nfe/cancelamento")
@RequiredArgsConstructor
public class NFeCancelamentoController {

    private final NFeCancelamentoService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public NFeCancelamento cancelarNFe(@RequestBody NFeCancelamento request) {
        return service.cancelaNFe(request);
    }

}
