package br.com.achimid.emissao.emitente;

import br.com.achimid.emissao.integration.NFeIntegration;
import br.com.achimid.emissao.nfe.mapper.NFeServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmitenteService {

    private final Emitente emitente;
    private final NFeIntegration integration;
    private final NFeServiceMapper mapper;

    public EmitenteStatus consultaStatusEmitente() {
        log.info("Consultando status do emitente");
        return mapper.from(integration.consultaStatusSefaz(emitente.getConfig()));
    }
}
