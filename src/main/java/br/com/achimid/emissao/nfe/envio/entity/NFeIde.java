package br.com.achimid.emissao.nfe.envio.entity;

import lombok.Data;

@Data
public class NFeIde {


    private String cuf;             /** Código da UF do emitente do Documento Fiscal */
    private String cnf;             /** Código Numérico que compõe a Chave de Acesso */
    private String natOp;           /** Descrição da Natureza da Operação -  Informar a natureza da operação de que decorrer a saída ou a     entrada, tais como: venda, compra, transferência, devolução,     importação, consignação, remessa (para fins de demonstração,     de industrialização ou outra), conforme previsto na alínea 'i',     inciso I, art. 19 do CONVÊNIO S/No, de 15 de dezembro de     1970.*/
    private String mod;             /** Código do Modelo do Documento Fiscal - 55=NF-e emitida em substituição ao modelo 1 ou 1A;     65=NFC-e, utilizada nas operações de venda no varejo (acritério da UF aceitar este modelo de documento). */
    private String serie;           /** Série do Documento Fiscal, preencher com zeros na hipótese */
    private String nnf;             /** Número do Documento Fiscal - Faixa: 1–999999999 */
    private String dhEmi;           /** Data e hora no formato UTC (Universal Coordinated Time):     AAAA-MM-DDThh:mm:ssTZD */
    private String dhSaiEnt;        /** Data e hora no formato UTC (Universal Coordinated Time):     AAAA-MM-DDThh:mm:ssTZD.     Não informar este campo para a NFC-e. */
    private String tpNF;            /** Tipo de Operação - 0=Entrada; 1=Saída */
    private String idDest;          /** Identificador de local de destino da operação - 0=Entrada; 1=Saída*/
    private String cMunFG;          /** Código do Município de Ocorrência do Fato     Gerador - Informar o município de ocorrência do fato gerador do ICMS.     Utilizar a Tabela do IBGE (Anexo IX - Tabela de UF, Município e     País) */
    private String tpImp;           /** Formato de Impressão do DANFE - 0=Sem geração de DANFE; 1=DANFE normal, Retrato; 2=DANFE normal, Paisagem; 3=DANFE Simplificado; 4=DANFE NFC-e; 5=DANFE NFC-e em mensagem eletrônica (o envio de mensagem eletrônica pode ser feita de forma simultânea com a impressão do DANFE; usar o tpImp=5 quando esta for a única forma de disponibilização do DANFE). */
    private String tpEmis;          /** Tipo de Emissão da NF-e - 1=Emissão normal (não em contingência); 2=Contingência FS-IA, com impressão do DANFE em formulário de segurança; 3=Contingência SCAN (Sistema de Contingência do Ambiente Nacional); 4=Contingência DPEC (Declaração Prévia da Emissão em Contingência); 5=Contingência FS-DA, com impressão do DANFE em formulário de segurança; 6=Contingência SVC-AN (SEFAZ Virtual de Contingência do AN); 7=Contingência SVC-RS (SEFAZ Virtual de Contingência do RS); */
    private String cdv;             /** Dígito Verificador da Chave de Acesso da NF-e - Informar o DV da Chave de Acesso da NF-e, o DV será     calculado com a aplicação do algoritmo módulo 11 (base 2,9) da     Chave de Acesso. (vide item 5 do Manual de Orientação) */
    private String tpAmb;           /** Identificação do Ambiente - 1=Produção/2=Homologação */
    private String finNFe;          /** Finalidade de emissão da NF-e - 1=NF-e normal;     2=NF-e complementar;     3=NF-e de ajuste;     4=Devolução de mercadoria. */
    private String indFinal;        /** Indica operação com Consumidor final - 0=Normal;  1=Consumidor final; */
    private String indPres;         /** Indicador de presença do comprador no  estabelecimento comercial no momento da operação - 0=Não se aplica (por exemplo, Nota Fiscal complementar ou de ajuste); 1=Operação presencial; 2=Operação não presencial, pela Internet; 3=Operação não presencial, Teleatendimento; 4=NFC-e em operação com entrega a domicílio; 9=Operação não presencial, outros. */
    private String procEmi;         /** Processo de emissão da NF-e - 0=Emissão de NF-e com aplicativo do contribuinte; 1=Emissão de NF-e avulsa pelo Fisco; 2=Emissão de NF-e avulsa, pelo contribuinte com seu certificado digital, através do site do Fisco; 3=Emissão NF-e pelo contribuinte com aplicativo fornecido pelo Fisco. */
    private String verProc;         /** Versão do Processo de emissão da NF-e */
    private String dhCont;          /** Data e Hora da entrada em contingência - Data e hora no formato UTC (Universal Coordinated Time): AAAA-MM-DDThh:mm:ssTZD */
    private String xJust;           /** Justificativa da entrada em contingência */

}
