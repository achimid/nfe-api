package br.com.achimid.emissao.nfe.consulta;

import br.com.achimid.emissao.emitente.Emitente;
import br.com.achimid.emissao.integration.NFeIntegration;
import br.com.achimid.emissao.nfe.mapper.NFeServiceMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NFeConsultaService {

    private final Emitente emitente;
    private final NFeIntegration integration;
    private final NFeServiceMapper mapper;

    public NFeConsultaStatus consultaStatusNFe(@NonNull final NFeConsultaStatus consultaStatus) {
        log.info("Consultando status da NFe", consultaStatus.getChave());

        var consultaStatusVO = mapper.from(consultaStatus, emitente);

        try {

            integration.consultaSituacaoNFe(consultaStatusVO);
            consultaStatus.setSucesso(true);

        } catch (Throwable e) {
            log.info("Erro ao consultar status NFe", consultaStatus.getUuid(), e);
            consultaStatus.setError(e);
        }

        mapper.merge(consultaStatus, consultaStatusVO);

        return consultaStatus;
    }

}
