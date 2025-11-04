package br.edu.icev.aed.forense;

import java.util.*;
import java.io.IOException;

/**
 * Interface para análise forense avançada de logs de sistema.
 * Esta interface define os contratos para os 5 desafios do trabalho de AED.
 */
public interface AnaliseForenseAvancada {
    
    /**
     * Desafio 1: Encontrar Sessões Inválidas
     * 
     * Identifica sessões que tiveram problemas de LOGIN/LOGOUT,
     * como logins aninhados ou logouts sem login correspondente.
     * 
     * @param caminhoArquivo Caminho para o arquivo CSV de logs
     * @return Set contendo as SESSION_IDs das sessões inválidas
     * @throws IOException Se houver erro ao ler o arquivo
     */
    Set<String> encontrarSessoesInvalidas(String caminhoArquivo) throws IOException;
    
    /**
     * Desafio 2: Reconstruir Linha do Tempo
     * 
     * Reconstrói a sequência cronológica de ações de uma sessão específica.
     * 
     * @param caminhoArquivo Caminho para o arquivo CSV de logs
     * @param sessionId ID da sessão a ser analisada
     * @return Lista ordenada cronologicamente dos ACTION_TYPEs da sessão
     * @throws IOException Se houver erro ao ler o arquivo
     */
    List<String> reconstruirLinhaTempo(String caminhoArquivo, String sessionId) throws IOException;
    
    /**
     * Desafio 3: Priorizar Alertas
     * 
     * Retorna os N alertas de maior severidade do arquivo de logs.
     * 
     * @param caminhoArquivo Caminho para o arquivo CSV de logs
     * @param n Número de alertas a retornar
     * @return Lista dos N alertas com maior SEVERITY_LEVEL
     * @throws IOException Se houver erro ao ler o arquivo
     */
    List<Alerta> priorizarAlertas(String caminhoArquivo, int n) throws IOException;
    
    /**
     * Desafio 4: Encontrar Picos de Transferência
     * 
     * Para cada evento de transferência, encontra o próximo evento
     * com BYTES_TRANSFERRED maior.
     * 
     * @param caminhoArquivo Caminho para o arquivo CSV de logs
     * @return Mapa onde chave=timestamp do evento atual, valor=timestamp do próximo evento com mais bytes
     * @throws IOException Se houver erro ao ler o arquivo
     */
    Map<Long, Long> encontrarPicosTransferencia(String caminhoArquivo) throws IOException;
    
    /**
     * Desafio 5: Rastrear Contaminação
     * 
     * Encontra o caminho mais curto entre dois recursos usando BFS,
     * baseado nas transições entre recursos dentro das sessões.
     * 
     * @param caminhoArquivo Caminho para o arquivo CSV de logs
     * @param recursoInicial Recurso de origem
     * @param recursoAlvo Recurso de destino
     * @return Optional contendo o caminho mais curto, ou empty se não houver caminho
     * @throws IOException Se houver erro ao ler o arquivo
     */
    Optional<List<String>> rastrearContaminacao(String caminhoArquivo, String recursoInicial, String recursoAlvo) throws IOException;
}
