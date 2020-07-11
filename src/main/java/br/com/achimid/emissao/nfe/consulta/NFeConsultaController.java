package br.com.achimid.emissao.nfe.consulta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nfe/status")
@RequiredArgsConstructor
public class NFeConsultaController {

    private final NFeConsultaService service;

    @GetMapping("/{chave}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public NFeConsultaStatus consultarNFe(@PathVariable String chave) {
        return service.consultaStatusNFe(new NFeConsultaStatus(chave));
    }


}
