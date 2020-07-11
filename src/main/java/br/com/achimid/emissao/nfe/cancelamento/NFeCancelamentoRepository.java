package br.com.achimid.emissao.nfe.cancelamento;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFeCancelamentoRepository extends MongoRepository<NFeCancelamento, String> {
}
