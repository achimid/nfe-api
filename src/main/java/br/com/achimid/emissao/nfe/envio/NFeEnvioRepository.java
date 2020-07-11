package br.com.achimid.emissao.nfe.envio;

import br.com.achimid.emissao.nfe.envio.entity.NFe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NFeEnvioRepository extends MongoRepository<NFe, UUID> {

    Optional<NFe> findTopByOrderBySerieDescNumeroNfeDesc();

}
