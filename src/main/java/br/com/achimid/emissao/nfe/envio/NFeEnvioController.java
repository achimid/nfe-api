package br.com.achimid.emissao.nfe.envio;

import br.com.achimid.emissao.nfe.envio.entity.NFe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nfe/envio")
@RequiredArgsConstructor
public class NFeEnvioController {

    private final NFeEnvioService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public NFe enviarNFe(@RequestBody NFe request) {
        return service.enviaNFe(request);
    }


}
