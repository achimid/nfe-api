package br.com.achimid.emissao.nfe.cancelamento;

import br.com.achimid.emissao.emitente.Emitente;
import br.com.achimid.emissao.integration.NFeIntegration;
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
public class NFeCancelamentoService {

    private final Emitente emitente;
    private final NFeIntegration integration;
    private final NFeServiceMapper mapper;
    private final NFeCancelamentoRepository repository;

    public NFeCancelamento cancelaNFe(@NonNull final NFeCancelamento nfeCancelamento) {
        log.info("Preparando cancelamento de NFe", nfeCancelamento.getUuid());

        // Salva pre cancelamento
        repository.save(nfeCancelamento);

        var cancelamentoVO = mapper.from(nfeCancelamento, emitente);

        // Efetua o envio da nota e salva o sucesso
        try {
            log.info("Efetuando cancelamento da NFe", nfeCancelamento.getUuid());

            integration.cancelaNFe(cancelamentoVO);
            nfeCancelamento.setSucesso(true);

            log.info("Envio da NFe efetuado com sucesso", nfeCancelamento.getUuid());
        } catch (Throwable e) {
            log.info("Erro ao cancelar NFe", nfeCancelamento.getUuid(), e);
            nfeCancelamento.setError(e);
        }

        mapper.merge(nfeCancelamento, cancelamentoVO);

        return repository.save(nfeCancelamento);
    }

}
