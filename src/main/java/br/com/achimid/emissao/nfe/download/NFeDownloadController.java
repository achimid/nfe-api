package br.com.achimid.emissao.nfe.download;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nfe/download")
@RequiredArgsConstructor
public class NFeDownloadController {

    private final NFeDownloadService service;

    @GetMapping("/{chave}")
    @ResponseStatus(code = HttpStatus.OK)
    public NFeDownload consultarStatusNFe(@PathVariable String chave) {
        return service.downloadNFe(new NFeDownload(chave));
    }

}
