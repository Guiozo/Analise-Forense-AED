package br.edu.icev.aed.forense;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import br.edu.icev.aed.forense.*;
import java.util.*;

public interface ResolucaoForence{
    public static Set<String> encontrarSessoesInvalidas(String arquivo_LogsCsv) throws IOException{
        Set<String> sessoesInvalidas = new HashSet<>();
        Stack<String> pilhaSessoes = new Stack<>();


        List<String> linhas = Files.readAllLines(Paths.get(arquivo_LogsCsv));


        for (String linha : linhas.subList(1, linhas.size())){
            String[] partes = linha.split(",");
            if (partes.length < 3) continue;
        
        String sessionId =partes[1].trim();
        String acao = partes[2].trim().toUpperCase();

        switch (acao) {
            case "LOGIN":
            if(pilhaSessoes.contains((sessionId))){

                sessoesInvalidas.add(sessionId);
            } else {
                pilhaSessoes.push(sessionId);
            }    
                break;


            case "LOGOUT":
            if(pilhaSessoes.contains((sessionId))){
                sessoesInvalidas.remove(sessionId);
            } else {
                pilhaSessoes.add(sessionId);
            }    
                break;
        
        
    }
        }

        sessoesInvalidas.addAll(pilhaSessoes);
        return sessoesInvalidas;




    }
    public static void main(String[] args) {
        try {
        Set<String> invalidas = encontrarSessoesInvalidas("arquivo_logs.csv");
        System.err.println("Sessoes invalidas: " + invalidas);
        }catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " e.getMessage());
        }
    }
}

