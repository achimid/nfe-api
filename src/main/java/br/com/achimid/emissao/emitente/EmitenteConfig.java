package br.com.achimid.emissao.emitente;

import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TUfEmi;
import com.google.gson.Gson;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
public class EmitenteConfig {

    @Value("${pastaCertificado}")
    private String pastaCertificadoResource;

    @Value("${pastaSchemas}")
    private String pastaSchemasResource;

    @Value("classpath:emitente.json")
    private Resource emitenteJSON;

    @Autowired
    private Gson gson;

    @Bean
    public Emitente iniciarEmitenteProperties() throws IOException {
        val emitente = getEmitenteFromJSONResource();
        val metadados = EmitenteMetadados.builder().producao(false).build();

        emitente.setMetadados(metadados);

        return emitente;
    }

    @Bean
    public ConfiguracoesNfe iniciarConfiguralcaoEmitente(@NonNull final Emitente emitente) throws CertificadoException, IOException {
        val ambienteEnum = emitente.getMetadados().isProducao() ? AmbienteEnum.PRODUCAO : AmbienteEnum.HOMOLOGACAO;
        val estadosEnum = EstadosEnum.getByCodigoIbge(emitente.getEstado());

        val pastaSchemas = new ClassPathResource(pastaSchemasResource).getFile().getAbsolutePath();
        val caminhoCertificado = new ClassPathResource(pastaCertificadoResource).getFile().getAbsolutePath();

        val certificado = CertificadoService.certificadoPfx(caminhoCertificado, emitente.getCertificado().getSenha());
        val config = ConfiguracoesNfe.criarConfiguracoes(estadosEnum, ambienteEnum, certificado, pastaSchemas);

        if (certificado.getCnpjCpf() == null) certificado.setCnpjCpf(emitente.getCNPJ());

        emitente.setConfig(config);

        return config;
    }

    private Emitente getEmitenteFromJSONResource() throws IOException {
        String json = new String(Files.readAllBytes(emitenteJSON.getFile().toPath()));
        return gson.fromJson(json, Emitente.class);
    }

}
