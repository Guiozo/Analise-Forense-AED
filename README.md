# Trabalho Final - AED 2025.2
## Análise Forense Avançada de Logs

**Prazo de Entrega:** [Data definida pelo professor]  
**Valor:** 40% da P2 da disciplina  
**Formato:** Trabalho em grupo

---

## 🎯 Objetivo

Sistemas críticos foram comprometidos! Como especialista em perícia digital, você deve usar estruturas de dados para analisar logs de sistema, identificar padrões anômalos e reconstruir a cadeia de ataque.

Sua missão é implementar uma classe Java que resolva **5 desafios de análise forense**, cada um utilizando uma estrutura de dados específica.

## 📋 O que você deve entregar

### 1. Arquivo JAR (Obrigatório)
- **Nome:** `matricula1_matricula2_matricula3.jar`
- **Tipo:** "Fat JAR" (com todas as dependências)
- **Exemplo:** `45789_454889_2025487.jar`

### 2. Arquivo README.txt (Obrigatório)
- **Conteúdo:** Nome completo da sua classe implementadora
- **Exemplo:** `br.edu.icev.aed.forense.MinhaAnaliseForense`

### 3. Sua implementação deve:
- ✅ Implementar a interface `AnaliseForenseAvancada`
- ✅ Resolver todos os 5 desafios
- ✅ Ser **importável** como biblioteca externa
- ✅ Usar as estruturas de dados corretas

## 🤖 Como Funciona o Validador Automático

### O validador executará seu código da seguinte forma:

1. **Importa seu JAR** como dependência em um projeto Java
2. **Lê o README.txt** para descobrir o nome da sua classe
3. **Instancia sua classe** dinamicamente:
```java
// O validador fará algo assim:
Class<?> clazz = Class.forName("br.edu.icev.aed.forense.SuaClasse");
AnaliseForenseAvancada analyzer = (AnaliseForenseAvancada) clazz.getDeclaredConstructor().newInstance();

// Testa cada método com diferentes arquivos CSV
Set<String> resultado1 = analyzer.encontrarSessoesInvalidas("teste1.csv");
List<String> resultado2 = analyzer.reconstruirLinhaTempo("teste2.csv", "session-x");
// ... e assim por diante
```

### ⚠️ Requisitos Críticos para o JAR:

- **JAR deve ser "Fat JAR"**: Contém todas as dependências
- **Classe deve ter construtor público sem parâmetros**
- **Implementação deve ser thread-safe** (validador pode executar em paralelo)
- **Não deve ter dependências externas** além da API fornecida
- **Não deve fazer System.exit()** ou finalizar a JVM

## 🏗️ Configuração do Projeto

### 1. Baixe as dependências
- Baixe o arquivo `analise-forense-api.jar` (fornecido em lib/ desde repositório)
- Coloque na pasta `lib/` do seu projeto

### 2. Configure sua IDE
- **IntelliJ**: Clique direito no JAR → "Add as Library"
- **Eclipse**: Build Path → Libraries → Add JARs
- **VS Code**: Java Projects → Referenced Libraries → +

### 3. Importe as classes
```java
import br.edu.icev.aed.forense.AnaliseForenseAvancada;
import br.edu.icev.aed.forense.Alerta;
```

## 🔥 Os 5 Desafios

### Desafio 1: Encontrar Sessões Inválidas 🔍
**Estrutura:** `Map<String, Stack<String>>`  
**Problema:** Detectar LOGINs aninhados e LOGOUTs sem LOGIN correspondente  

### Desafio 2: Reconstruir Linha do Tempo ⏰  
**Estrutura:** `Queue<String>`  
**Problema:** Obter sequência cronológica de ações de uma sessão  

### Desafio 3: Priorizar Alertas 🚨  
**Estrutura:** `PriorityQueue<Alerta>`  
**Problema:** Retornar os N alertas mais críticos  

### Desafio 4: Encontrar Picos de Transferência 📈  
**Estrutura:** `Stack<EventoTransferencia>`  
**Problema:** Para cada evento, encontrar o próximo com mais bytes  

### Desafio 5: Rastrear Contaminação 🕸️  
**Estrutura:** `Map<String, List<String>>` + BFS  
**Problema:** Encontrar caminho mais curto entre recursos  


## 📊 Formato dos Dados

### Estrutura do CSV
```csv
TIMESTAMP,USER_ID,SESSION_ID,ACTION_TYPE,TARGET_RESOURCE,SEVERITY_LEVEL,BYTES_TRANSFERRED
1700000000,alice,session-a-01,LOGIN,/usr/bin/sshd,5,0
1700000012,alice,session-a-01,COMMAND_EXEC,/bin/ls,3,1024
1700000025,bob,session-b-01,LOGIN,/usr/bin/sshd,5,0
```

### Campos do CSV
| Campo | Tipo | Descrição | Validação |
|-------|------|-----------|-----------|
| TIMESTAMP | long | Unix epoch time | Ordem crescente |
| USER_ID | String | ID do usuário | Não vazio |
| SESSION_ID | String | ID da sessão | Não vazio |
| ACTION_TYPE | String | Tipo de ação | Valores específicos* |
| TARGET_RESOURCE | String | Recurso alvo | Não vazio |
| SEVERITY_LEVEL | int | Nível severidade | 1-10 |
| BYTES_TRANSFERRED | long | Bytes transferidos | ≥0, pode estar vazio |

**\* Tipos de ação válidos:**
- `LOGIN`, `LOGOUT`, `COMMAND_EXEC`, `FILE_ACCESS`, `DATA_TRANSFER`, `PERMISSION_DENIED`

## 📝 Passo a Passo para Implementação

### 1. Configurar Ambiente
```bash
# Criar estrutura de pastas
mkdir -p src/br/edu/icev/aed/forense
mkdir -p lib
mkdir -p build

# Colocar analise-forense-api.jar em lib/
```

### 2. Implementar sua Classe
```java
package br.edu.icev.aed.forense;

import java.io.*;
import java.util.*;

public class MinhaAnaliseForense implements AnaliseForenseAvancada {
    
    @Override
    public Set<String> encontrarSessoesInvalidas(String arquivo) throws IOException {
        // Implementar usando Map<String, Stack<String>>
    }
    
    @Override
    public List<String> reconstruirLinhaTempo(String arquivo, String sessionId) throws IOException {
        // Implementar usando Queue<String>
    }
    
    @Override
    public List<Alerta> priorizarAlertas(String arquivo, int n) throws IOException {
        // Implementar usando PriorityQueue<Alerta>
    }
    
    @Override
    public Map<Long, Long> encontrarPicosTransferencia(String arquivo) throws IOException {
        // Implementar usando Stack (Next Greater Element)
    }
    
    @Override
    public Optional<List<String>> rastrearContaminacao(String arquivo, String origem, String destino) throws IOException {
        // Implementar usando BFS em grafo
    }
}
```

### 3. Compilar e Gerar JAR
```bash
# 1. Compilar todos os arquivos Java
javac -d build -cp "lib/*:src" src/br/edu/icev/aed/forense/*.java

# 2. Extrair dependências da API para o JAR final
cd build
jar xf ../lib/analise-forense-api.jar

# 3. Criar manifesto (OPCIONAL - sem Main-Class)
echo "Manifest-Version: 1.0" > MANIFEST.MF

# 4. Gerar JAR fat com tudo incluído
jar cfm ../123456_789012_345678.jar MANIFEST.MF br/ *

# 5. Voltar ao diretório raiz
cd ..

# 6. Criar README.txt
echo "br.edu.icev.aed.forense.MinhaImplementacao" > README.txt
```

### 💡 Dica: Use o script fornecido
```bash
# Adapte o script build.sh para sua implementação
bash build.sh
```

### 4. Testar Antes da Entrega

#### Teste Local de Importação:
```java
// Crie um projeto separado para testar se seu JAR funciona
// 1. Adicione seu JAR como dependência
// 2. Teste se consegue importar e usar sua classe:

import br.edu.icev.aed.forense.AnaliseForenseAvancada;
import br.edu.icev.aed.forense.MinhaImplementacao;

public class TesteValidacao {
    public static void main(String[] args) throws Exception {
        // Simula o que o validador fará
        AnaliseForenseAvancada impl = new MinhaImplementacao();
        
        Set<String> invalidas = impl.encontrarSessoesInvalidas("arquivo_logs.csv");
        System.out.println("Funciona! Encontradas: " + invalidas.size() + " sessões inválidas");
    }
}
```

#### Checklist Final:
- [ ] JAR é "fat JAR" (contém todas as dependências)
- [ ] README.txt tem nome exato da classe
- [ ] JAR pode ser importado em outro projeto
- [ ] Classe tem construtor público padrão
- [ ] Todos os 5 métodos estão implementados
- [ ] Testado com arquivos CSV reais

## ⚠️ Critérios de Avaliação

### 📊 Pontuação (máximo 120 pontos)
- **Corretude (70 pts):** Algoritmos funcionam corretamente
- **Eficiência (40 pts):** Performance comparada com outras soluções  
- **Qualidade (10 pts):** Código limpo e bem estruturado

### 🏆 Grupo Vencedor
- Maior pontuação **E** ≥ 95 pontos = Nota 100
- Demais grupos: nota proporcional ao vencedor

### ❌ Critérios de Anulação (Nota 0)
- Plágio ou desonestidade acadêmica
- JAR não executável
- Não implementa a interface corretamente  
- Resultados "hardcoded" (predefinidos)

## 📚 Recursos Úteis
- [Java Collections Tutorial](https://docs.oracle.com/javase/tutorial/collections/)
- [Stack](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Stack.html) | [Queue](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Queue.html) | [PriorityQueue](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/PriorityQueue.html)
- [BFS Algorithm](https://en.wikipedia.org/wiki/Breadth-first_search)
- [Next Greater Element](https://www.geeksforgeeks.org/next-greater-element/)

---

## 🧪 Testando seu JAR como Biblioteca

### Cenário: Como o Validador Usará seu JAR

O validador automático **NÃO** executará seu JAR como aplicação. Em vez disso:

1. **Adicionará seu JAR ao classpath** de um projeto Java de teste
2. **Carregará sua classe dinamicamente** usando reflection
3. **Instanciará sua classe** e testará os métodos

### Exemplo de Teste de Validação:

```java
// Este é um exemplo de como o validador testará seu código:

import java.lang.reflect.Constructor;
import br.edu.icev.aed.forense.AnaliseForenseAvancada;

public class SimuladorValidador {
    public static void main(String[] args) throws Exception {
        // 1. Ler nome da classe do README.txt
        String nomeClasse = "br.edu.icev.aed.forense.MinhaImplementacao";
        
        // 2. Carregar classe dinamicamente
        Class<?> clazz = Class.forName(nomeClasse);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        
        // 3. Instanciar sua implementação
        AnaliseForenseAvancada analyzer = (AnaliseForenseAvancada) constructor.newInstance();
        
        // 4. Testar todos os métodos
        testDesafio1(analyzer);
        testDesafio2(analyzer);
        testDesafio3(analyzer);
        testDesafio4(analyzer);
        testDesafio5(analyzer);
        
        System.out.println("✅ Todos os testes passaram!");
    }
    
    private static void testDesafio1(AnaliseForenseAvancada impl) throws Exception {
        Set<String> resultado = impl.encontrarSessoesInvalidas("teste_casos_1.csv");
        // Validar resultado esperado...
    }
    
    // ... outros métodos de teste
}
```

### ⚠️ Pontos de Atenção:

#### ✅ Faça isso:
- Implemente construtor público padrão: `public MinhaClasse() {}`
- Use apenas bibliotecas padrão do Java + API fornecida
- Trate exceções de IO adequadamente
- Retorne tipos exatos especificados na interface

#### ❌ Evite isso:
- **NÃO** use `System.exit()` - quebra o validador
- **NÃO** imprima diretamente no console (`System.out.println`)
- **NÃO** use caminhos absolutos para arquivos
- **NÃO** mantenha estado entre chamadas dos métodos
- **NÃO** use bibliotecas externas não fornecidas

### 🔧 Script de Teste Rápido:

Use este comando para testar se seu JAR é importável:

```bash
# Testar se JAR contém suas classes
jar tf seu_arquivo.jar | grep "br/edu/icev"

# Testar carregamento da classe
java -cp ".:seu_arquivo.jar" -c "
import br.edu.icev.aed.forense.*;
AnaliseForenseAvancada impl = new SuaClasse();
System.out.println(\"Classe carregada com sucesso!\");
"
```

