package br.com.achimid.emissao.nfe.envio;

import br.com.achimid.emissao.emitente.Emitente;
import br.com.achimid.emissao.emitente.EmitenteStatus;
import br.com.achimid.emissao.integration.NFeIntegration;
import br.com.achimid.emissao.nfe.envio.entity.NFe;
import br.com.achimid.emissao.nfe.mapper.NFeServiceMapper;
import br.com.achimid.emissao.nfe.vo.NFeEnvioVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NFeEnvioService {

    private final Emitente emitente;
    private final NFeIntegration integration;
    private final NFeServiceMapper mapper;
    private final NFeEnvioRepository repository;

    public NFe enviaNFe(@NonNull final NFe nfe) {
        log.info("Preparando envio da NFe", nfe.getUuid());

        // Salva a nfe antes do envio
        repository.save(nfe);

        val nfeEnvioVO = mapper.from(nfe, emitente);

        geraProximoNumeroSerie(nfeEnvioVO);
        geraNumeroSequencialItemProdutos(nfeEnvioVO);

        // Efetua o envio da nota e salva o sucesso
        try {
            log.info("Efetuando envio da NFe", nfe.getUuid());

            integration.enviaNFe(nfeEnvioVO);
            nfe.setSucesso(true);

            log.info("Envio da NFe efetuado com sucesso", nfe.getUuid());
        } catch (Throwable e) {
            log.info("Erro ao enviar NFe", nfe.getUuid(), e);
            nfe.setError(e);
        }

        // Efetua o preenchimento das informações geradas no envio dentro do nfe
        log.info("Efetuando merge das informações do envio", nfe.getUuid());
        mapper.merge(nfe, nfeEnvioVO);

        // Salva a nfe depois do envio com os resultados
        log.info("Salvando retorno do envio da NFe", nfe.getUuid());
        return repository.save(nfe);
    }

    private void geraProximoNumeroSerie(@NonNull final NFeEnvioVO nfeEnvioVO) {

        log.info("Buscando a ultima NFe para incrementar");
        val ultimaNota = repository.findTopByOrderBySerieDescNumeroNfeDesc();

        if (ultimaNota.isEmpty()) {
            nfeEnvioVO.setNumeroNfe(1);
            nfeEnvioVO.setSerie(1);
            return;
        }

        val nFe = ultimaNota.get();

        var numero = nFe.getNumeroNfe();
        var serie = nFe.getSerie();

        // Regra de incremento do numero;
        if (numero >= 999_999) {
            numero = 1;
            serie++;
        } else {
            numero++;
        }

        nfeEnvioVO.setNumeroNfe(numero);
        nfeEnvioVO.setSerie(serie);

        log.info("Incrementando numero e serie", numero, serie);
    }

    private void geraNumeroSequencialItemProdutos(@NonNull final NFeEnvioVO nfeEnvioVO) {
        for (int i = 0; i < nfeEnvioVO.getProdutos().size(); i++) {
            val prod = nfeEnvioVO.getProdutos().get(i);
            prod.setNItem(String.valueOf(i+1));
        }
    }


    public EmitenteStatus consultaStatusEmitente() {
        log.info("Consultando status do emitente");
        return mapper.from(integration.consultaStatusSefaz(emitente.getConfig()));
    }
}
