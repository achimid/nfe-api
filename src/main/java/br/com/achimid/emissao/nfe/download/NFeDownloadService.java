package br.com.achimid.emissao.nfe.download;

import br.com.achimid.emissao.emitente.Emitente;
import br.com.achimid.emissao.integration.NFeIntegration;
import br.com.achimid.emissao.nfe.cancelamento.NFeCancelamento;
import br.com.achimid.emissao.nfe.cancelamento.NFeCancelamentoRepository;
import br.com.achimid.emissao.nfe.mapper.NFeServiceMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NFeDownloadService {

    private final Emitente emitente;
    private final NFeIntegration integration;
    private final NFeServiceMapper mapper;
    private final NFeDownloadRepository repository;

    public NFeDownload downloadNFe(@NonNull final NFeDownload nfeDownload) {
        log.info("Preparando download de NFe", nfeDownload.getUuid());

        // Salva pre download
        repository.save(nfeDownload);

        var downloadVO = mapper.from(nfeDownload, emitente);

        // Efetua o envio da nota e salva o sucesso
        try {
            log.info("Efetuando cancelamento da NFe", nfeDownload.getUuid());

            integration.downloadNFe(downloadVO);
            nfeDownload.setSucesso(true);

            log.info("Envio da NFe efetuado com sucesso", nfeDownload.getUuid());
        } catch (Throwable e) {
            log.info("Erro ao cancelar NFe", nfeDownload.getUuid(), e);
            nfeDownload.setError(e);
        }

        mapper.merge(nfeDownload, downloadVO);

        return repository.save(nfeDownload);
    }

}
