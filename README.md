# Programação Avançada | Lab5

:gb: [English version](README_EN.md)

## Objectivos
- Introdução ao ADT Graph;
- Modelação de informação utilizando grafos;
- Algoritmia simples com grafos.

## 1 - Introdução

Os grafos (ver Figura 1) são uma estrutura de dados fundamental na teoria da computação e matemática discreta, amplamente utilizada para modelar relações entre objetos. Consistem num conjunto de vértices (ou nós) conectados por arestas (ou arcos). Os grafos podem ser direcionados, onde as arestas têm uma direção, ou não direcionados, onde as arestas são bidirecionais. O exemplo da figura ilustra um grafo não direcionado.

Grafos são uma ferramenta versátil para resolver uma variedade de problemas em diferentes campos, como ciência da computação, engenharia, logística, redes sociais, biologia e muito mais. Eles são usados para representar conexões entre elementos, como redes de computadores, rotas de navegação, relações sociais, estruturas de dados, entre outros.

Existem várias propriedades e algoritmos associados aos grafos, como a busca em largura e em profundidade, algoritmos de menor caminho, análise de conectividade e coloração de grafos. Os grafos são uma base crucial para a resolução de problemas complexos, ajudando a otimizar processos, modelar sistemas do mundo real e identificar padrões em dados, tornando-se uma ferramenta essencial em muitos domínios da ciência e da tecnologia.

![Figura 1 - Exemplo de um grafo](images/fig01.png)

Figura 1 - Exemplo de um grafo

### ADT Graph

Os grafos, como estruturas de dados, podem ser implementados de diversas formas. Para a sua utilização vamo-nos abstrair desses pormenores e utilizá-los na forma de um ADT.

#### Representação

Este ADT Graph<V, E> é **caracterizado por modelar grafos** que possuem:
• Vértices do tipo Vertex<V>, que internamente guardam elementos do tipo genérico V, e;
• Arestas do tipo Edge<E, V>, que internamente guardam elementos do tipo genérico E e que conectam vértices do tipo Vertex<V>;
No caso da Figura 1, por exemplo, V será do tipo Character e E será do tipo Integer.

⚠️Os tipos Vertex<V> e Edge<E, V> são análogos ao tipo Position<T> do ADT Tree.

#### Operações
As operações disponibilizadas são as seguintes:
- **int numVertices()**: Retorna o número total de vértices no grafo.
- **int numEdges()**: Retorna o número total de arestas no grafo.
- **Collection<Vertex<V>> vertices()**: Retorna uma coleção de todos os vértices no grafo. Se não houver vértices, retorna uma coleção vazia.
- **Collection<Edge<E, V>> edges()**: Retorna uma coleção de todas as arestas no grafo. Se não houver arestas, retorna uma coleção vazia.
- **Collection<Edge<E, V>> incidentEdges(Vertex<V> v)**: Retorna uma coleção de arestas incidentes a um vértice especificado.
- **Vertex<V> opposite(Vertex<V> v, Edge<E, V> e)**: Retorna o vértice oposto a um vértice dado ao longo de uma aresta dada.
- **boolean areAdjacent(Vertex<V> u, Vertex<V> v)**: Verifica se dois vértices são adjacentes, ou seja, se há uma aresta que os conecta.
- **Vertex<V> insertVertex(V vElement)**: Insere um novo vértice com um elemento dado no grafo.
- **Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement)**: Insere uma nova aresta entre dois vértices existentes no grafo.
- **Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement)**: Insere uma nova aresta entre dois vértices existentes, especificados por seus elementos.
- **V removeVertex(Vertex<V> v)**: Remove um vértice e todas as arestas incidentes, retornando o elemento do vértice removido.
- **E removeEdge(Edge<E, V> e)**: Remove uma aresta, retornando o elemento da aresta removida.
- **V replace(Vertex<V> v, V newElement)**: Substitui o elemento de um vértice por um novo elemento e retorna o elemento anterior.
- **E replace(Edge<E, V> e, E newElement)**: Substitui o elemento de uma aresta por um novo elemento e retorna o elemento anterior.

#### Biblioteca

O ADT Graph, classes para visualização e implementações base estão contidas na biblioteca JavaFX SmartGraph, disponível em: 
https://github.com/brunomnsilva/JavaFXSmartGraph

🧐Pode e deve consultar o repositório para visualizar exemplos de utilização.

📝 Pode consultar o Javadoc da interface Graph<V,E> em:
https://javadoc.io/doc/com.brunomnsilva/smartgraph/latest/com/brunomnsilva/smartgraph/graph/Graph.html

⚙️ Esta biblioteca já faz parte do projeto clonado, através de uma dependência Maven.

## Parte 1 – Exemplo simples
1. Na classe FirstProgram complete o código por forma a modelar o grafo da Figura 1, utilizando obrigatoriamente os métodos:
- Vertex<V> insertVertex(V vElement)
- Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement)

2. Execute o programa e verifique que a visualização do grafo coincide com o pretendido.

## Parte 2 – Aplicação de modelação de voos

A classe FlightsProgram lança uma aplicação JavaFX para visualização e manipulação de um conjunto de aeroportos e voos entre estes.

1. Lance o programa e visualize a sua interface. Neste momento o grafo modelado deverá estar vazio e os botões ainda não manipulam corretamente o grafo. Adicionalmente, as estatísticas apresentadas no fundo da janela também ainda não estão a mostrar valores corretos.

### Modelação Inicial

2. No método FlightsView.createInitialModel() modele o grafo ilustrado na Figura 2. A tabela 1 contém a informação dos voos ilustrados.

![Figura 2 - Aeroportos americanos e voos respetivos](images/fig02.png)

Figura 2 - Aeroportos americanos e voos respetivos

| Aresta |Código|Distância (mil.)|Companhia|
|:------:|:----:|:----:|:----:|
|   F1   |UN3563|2555|United|
|   F2   |DT1597|2555|Delta|
|   F3   |UN9375|337|United|
|   F4   |AM4526|337|American|
|   F5   |UN4836|1743|United|
|   F6   |VA2001|1743|Virgin America|
|   F7   |UN1475|1843|United|
|   F8   |AL7854|1843|Alaska|
|   F9   |SP1020|1233|Spirit|
|  F10   |AM4582|1233|American|
|  F11   |UN4568|802|United|
|  F12   |SP4512|1387|Spirit|
|  F13   |AM4520|849|American|
|  F14   |UN7812|849|United|
|  F15   |FT1000|1205|Frontier|
|  F16   |FT4021|1099|Frontier|
|  F17   |AM1026|1099|American|
|  F18   |AM5267|1120|American|

Tabela 1 – Descrição dos voos da Figura 2.

3. Lance novamente a aplicação e verifique que o modelo mostrado corresponde ao esperado.

#### Manipulação

4. Implemente os métodos seguintes, manipulando a instância (atributo) existente de Graph<Airport, Flight>:

   1. FlightsView.addAirport(String airportCode)
   2. FlightsView .addFlight(Vertex<Airport> vertexFrom, Vertex<Airport> vertexTo, String code, String distance)
   3. removeFlight(Edge<Flight, Airport> edge)
   4. removeAirport(Vertex<Airport> vertex)

5. Lance novamente a aplicação e manipule livremente o modelo através da interação com o utilizador.

#### Estatísticas

6. Implemente o método FlightsView. updateStatistics(), responsável por popular as estatísticas no fundo da janela. As estatísticas a calcular/mostrar são:

   1. Número total de aeroportos;
   2. Número total de voos;
   3. Aeroporto com mais tráfego (vértice com maior grau);
   4. Número de ligações do aeroporto com mais tráfego (arestas incidentes)

7. Lance novamente a aplicação e verifique que as estatísticas são calculadas corretamente para os vários estados do grafo obtidos após livre manipulação.

#### Correção do programa

Teste adequadamente todas as situações de uso do programa e trate quaisquer erros que ocorram adequadamente.

(fim de enunciado)
