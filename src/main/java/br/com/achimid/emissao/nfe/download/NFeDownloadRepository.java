package br.com.achimid.emissao.nfe.download;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFeDownloadRepository extends MongoRepository<NFeDownload, String> {
}
