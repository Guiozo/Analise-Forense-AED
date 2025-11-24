package br.edu.icev.aed.forense;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SolucaoForense implements AnaliseForenseAvancada {
    public SolucaoForense() {
    }

    // DESAFIO 1: Encontrar Sessões Inválidas (Map<String, Deque<String>>)
    @Override
    public Set<String> encontrarSessoesInvalidas(String arquivo) throws IOException {
        Set<String> sessoesInvalidas = new HashSet<>();
        Map<String, Deque<String>> controleSessoes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length < 4) continue;

                String userId = dados[1].trim();
                String sessionId = dados[2].trim();
                String actionType = dados[3].trim();

                if (!actionType.equals("LOGIN") && !actionType.equals("LOGOUT")) {
                    continue;
                }
                Deque<String> pilhaUsuario = controleSessoes.computeIfAbsent(userId, k -> new ArrayDeque<>());

                if (actionType.equals("LOGIN")) {
                    if (!pilhaUsuario.isEmpty()) {
                        sessoesInvalidas.add(sessionId);
                    }
                    pilhaUsuario.push(sessionId);

                } else {
                    if (pilhaUsuario.isEmpty() || !pilhaUsuario.peek().equals(sessionId)) {
                        sessoesInvalidas.add(sessionId);
                    } else {
                        pilhaUsuario.pop();
                    }
                }
            }
        }
        for (Deque<String> pilha : controleSessoes.values()) {
            sessoesInvalidas.addAll(pilha);
        }
        return sessoesInvalidas;
    }

    // DESAFIO 2: Reconstruir Linha do Tempo (Queue<String>)
    @Override
    public List<String> reconstruirLinhaTempo(String arquivo, String sessionId) throws IOException {

        Queue<String> filaAcoes = new ArrayDeque<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",", 5);
                if (campos.length < 4) continue;
                String sessaoAtual = campos[2].trim();
                if (sessionId.equals(sessaoAtual)) {
                    String actionType = campos[3].trim();

                    filaAcoes.offer(actionType);
                }
            }
        }
        return new ArrayList<>(filaAcoes);
    }

    // DESAFIO 3: Priorizar Alertas (PriorityQueue<Alerta>)
    @Override
    public List<Alerta> priorizarAlertas(String arquivo, int n) throws IOException {
        if (n <= 0) return new ArrayList<>();

        PriorityQueue<Alerta> minHeap = new PriorityQueue<>(Comparator.comparingInt(Alerta::getSeverityLevel));
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",", 7);
                if (campos.length < 7) continue;

                try {
                    int severityLevel = Integer.parseInt(campos[5].trim());
                    Alerta alerta = new Alerta(
                            Long.parseLong(campos[0].trim()),
                            campos[1].trim(),
                            campos[2].trim(),
                            campos[3].trim(),
                            campos[4].trim(),
                            severityLevel,
                            campos[6].trim().isEmpty() ? 0 : Long.parseLong(campos[6].trim()));
                    if (minHeap.size() < n) {
                        minHeap.offer(alerta);
                    } else if (severityLevel > minHeap.peek().getSeverityLevel()) {
                        minHeap.poll();
                        minHeap.offer(alerta);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        List<Alerta> resultado = new ArrayList<>(minHeap);
        resultado.sort(Comparator.comparingInt(Alerta::getSeverityLevel).reversed());
        return resultado;
    }

    // DESAFIO 4: Encontrar Picos de Transferência (Stack<EventoTransferencia>)
    private static class EventoTransferencia {
        final long timestamp;
        final long bytes;

        EventoTransferencia(long timestamp, long bytes) {
            this.timestamp = timestamp;
            this.bytes = bytes;
        }
    }
    @Override
    public Map<Long, Long> encontrarPicosTransferencia(String arquivo) throws IOException {
        List<EventoTransferencia> eventos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length < 7) continue;

                try {
                    long bytesTransferred = campos[6].trim().isEmpty() ? 0 : Long.parseLong(campos[6].trim());
                    if (bytesTransferred > 0) {
                        long timestamp = Long.parseLong(campos[0].trim());
                        eventos.add(new EventoTransferencia(timestamp, bytesTransferred));
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }


        Map<Long, Long> resultado = new HashMap<>();
        Deque<EventoTransferencia> pilha = new ArrayDeque<>();

        for (int i = eventos.size() - 1; i >= 0; i--) {
            EventoTransferencia eventoAtual = eventos.get(i);

            while (!pilha.isEmpty() && pilha.peek().bytes <= eventoAtual.bytes) {
                pilha.pop();
            }
            if (!pilha.isEmpty()) {
                resultado.put(eventoAtual.timestamp, pilha.peek().timestamp);
            }
            pilha.push(eventoAtual);
        }
        return resultado;
    }

    // DESAFIO 5: Rastrear Contaminação (Map<String, List<String>> + BFS)
    private Map<String, List<String>> construirGrafo(String arquivo) throws IOException {
        Map<String, List<String>> grafo = new HashMap<>();
        Map<String, List<String>> recursosPorSessao = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length < 5) continue;

                String sessionId = campos[2].trim();
                String targetResource = campos[4].trim();

                if (!targetResource.isEmpty()) {
                    recursosPorSessao.computeIfAbsent(sessionId, k -> new ArrayList<>())
                            .add(targetResource);
                }
            }
        }
        for (List<String> recursos : recursosPorSessao.values()) {
            for (int i = 0; i < recursos.size() - 1; i++) {
                String de = recursos.get(i);
                String para = recursos.get(i + 1);

                if (!de.equals(para)) {
                    grafo.computeIfAbsent(de, k -> new ArrayList<>());
                    if (!grafo.get(de).contains(para)) {
                        grafo.get(de).add(para);
                    }
                }
            }
        }
        return grafo;
    }

    @Override
    public Optional<List<String>> rastrearContaminacao(String arquivo, String origem, String destino) throws IOException {
        Map<String, List<String>> grafo = construirGrafo(arquivo);

        if (origem.equals(destino)) {
            if (grafo.containsKey(origem) || grafo.values().stream()
                    .anyMatch(list -> list.contains(origem))) {
                return Optional.of(List.of(origem));
            }
            return Optional.empty();
        }

        Queue<String> fila = new LinkedList<>();
        Map<String, String> predecessores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        fila.offer(origem);
        visitados.add(origem);
        predecessores.put(origem, null);

        while (!fila.isEmpty()) {
            String atual = fila.poll();

            if (atual.equals(destino)) {
                List<String> caminho = new ArrayList<>();
                String no = destino;
                while (no != null) {
                    caminho.add(no);
                    no = predecessores.get(no);
                }
                Collections.reverse(caminho);
                return Optional.of(caminho);
            }
            if (grafo.containsKey(atual)) {
                for (String vizinho : grafo.get(atual)) {
                    if (!visitados.contains(vizinho)) {
                        visitados.add(vizinho);
                        predecessores.put(vizinho, atual);
                        fila.offer(vizinho);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
