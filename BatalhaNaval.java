import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {

    // Construtor
    private BatalhaNaval() {
        Scanner ler = new Scanner(System.in);

        // A matriz é 9x9 pois a primeira linha e coluna servirão para numeração
        String matriz[][] = new String[9][9];

        // Variáveis que irão determinar a quantidade de navios e tentativas de jogadas,
        // respectivamente.
        int quantNavios = 1;
        int quantTentativas = 1;

        // Variáveis auxiliares.
        int tentativas = 0;
        int naviosAbatidos = 0;

        // Vetores para definir quais serão as coordenadas dos navios
        int posicoesNavios[][] = new int[2][quantNavios];
        posicoes(posicoesNavios, quantNavios);

        // Mensagem inicial e imprime o campo de batalha
        introducao(matriz, quantNavios, quantTentativas);

        // Fluxo de repetição
        do {
            String coordenadas = coordenadasJogador(ler, quantNavios);
            boolean abate = validaAbate(matriz, coordenadas, posicoesNavios, quantNavios);
            if (abate) {
                naviosAbatidos++;
                tentativas++;
                if (naviosAbatidos == quantNavios) {
                    System.out.println("\n\t*** PARABÉNS, VOCÊ GANHOU!! ***\n");
                    // Por algum motivo que não consegui identificar, ao satisfazer as condições, o fluxo não era finalizado, adicionado break para a quebra do mesmo.
                    break;
                }
            } else {
                tentativas++;
                if (tentativas == quantTentativas) {
                    System.out.println("\n\t*** FIM DE JOGO, VOCÊ PERDEU!! ***\n");
                    // Por algum motivo que não consegui identificar, ao satisfazer as condições, o fluxo não era finalizado, adicionado break para a quebra do mesmo.
                    break;
                }
            }

            // Irá apresentar o status de tentativas e abates.
            System.out.println("\n- STATUS: ");
            System.out.printf("Abates: %d\n", naviosAbatidos);
            System.out.printf("Tentativas restantes: %d\n", (quantTentativas - tentativas));
        } while (tentativas < quantTentativas || naviosAbatidos < quantNavios);
        ler.close();
    }

    // Método utilizado para gerar posições de linha e coluna aleatórias de 1 a 8.
    private void posicoes(int posicoes[][], int quantidadeNavios) {
        Random gerador = new Random();
        // Fluxo de repetição para ser gerado um número entre 1 e 8 para coordenada das linhas (0) e coluna (1).
        for (int i = 0; i < quantidadeNavios; i++) {
            posicoes[0][i] = gerador.nextInt(8) + 1;
            posicoes[1][i] = gerador.nextInt(8) + 1;

            System.out.print(posicoes[0][i] + ", ");
            System.out.print(posicoes[1][i]);
            System.out.println("");
        }
    }

    // Método para apresentar o texto de introdução ao jogador, com algumas orientações.
    private void introducao(String matriz[][], int quantNavios, int quantTentativas) {
        System.out.println("\n\t\t**** BEM VINDO AO JOGO - BATALHA NAVAL ****");
        System.out.println("\n\tOrientações:");
        System.out.printf("- Há %d navios escondidos, o objetivo do jogo é o abate de todos os navios;", quantNavios);
        System.out.printf("\n- Você possui %d tentativas, para abater todos os navios;\n", quantTentativas);
        System.out.println("\n\tDiretrizes:");
        System.out.println("- Preencha as coordenadas com valores entre 1 e 8, tanto para a linha quanto para a coluna;");
        System.out.println("- Separe as coordenadas por vírgula ou ponto;");
        System.out.printf("\n\t\t\t*** BOA SORTE! ***\n\n");
        System.out.println("-- CAMPO DE BATALHA -- \n");
        imprimeMatriz(matriz, 0, 0, "");
    }

    // Método que é utilizado para o jogador inserir as coordenadas a cada tentativa.
    private String coordenadasJogador(Scanner ler, int quantNavios) {
        // Variáveis auxiliares
        String coordenadas;
        int coordenadaLinha = 0;
        int coordenadaColuna = 0;

        // Fluxo de repetição para solicitar coordenadas ao jogador e só seguirá adiante se estiverem de acordo com as diretrizes.
        do {
            System.out.print("\nInforme as coordenadas de ataque(Linha e coluna, respectivamente, separadas por vírgula ou ponto): ");
            coordenadas = ler.next();

            // Onde setamos as coordenadas para variáveis temporárias para que possa ser feita a validação das diretrizes.
            if (coordenadas.length() == 3) {
                coordenadaLinha = Character.getNumericValue(coordenadas.charAt(0));
                coordenadaColuna = Character.getNumericValue(coordenadas.charAt(2));
            }

            // Valida se as coordenadas inseridas estão dentro do limite de 1 a 8 e separadas por vírgula ou ponto.
            if (coordenadas.length() != 3 || coordenadaLinha == 0 || coordenadaLinha == 9 || coordenadaColuna == 0 || coordenadaColuna == 9) {
                System.out.println("ERROR: As coordenadas inseridas são inválidas, valide se a mesma foi separado por vígula ou ponto;");
            }
        } while (coordenadas.length() != 3 || coordenadaLinha == 0 || coordenadaLinha == 9 || coordenadaColuna == 0 || coordenadaColuna == 9);
        
        // Sout serve apenas para espaçamento, é apenas um capricho.
        System.out.println("");

        return coordenadas;
    }

    // Método para imprimir a matriz inicialmente e após cada tiro.
    private void imprimeMatriz(String matriz[][], int linha, int coluna, String tiro) {
        // Fluxo de repetição para imprimir cada posição da matriz
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (linha == 0 && coluna == 0 && tiro == "") {
                    // Utilizado apenas para preencher espaços da matriz com "~".
                    matriz[i][j] = "~";
                } else {
                    // Quando o for dado o tiro, o método atualizaMatriz irá se adequar ao if
                    // substituindo a coordendada por "X" ou "O".
                    matriz[linha][coluna] = tiro;
                }
                for (int k = 1; k < matriz[i].length; k++) {
                    // Serve para retirar o ~ da posição 0,0 é apenas um capricho.
                    matriz[0][0] = " ";
                    // Os dois códigos abaixo irão gerar a numeração de 1 a 8 para as linhas e
                    // colunas.
                    matriz[k][0] = "" + k;
                    matriz[0][k] = "" + k;
                }
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    // Método para validar se o tiro acertou o navio ou não.
    private boolean validaAbate(String matriz[][], String coordenadasJogador, int posicoesNavios[][], int quantNavios) {
        // Variável auxiliar.
        boolean abate = true;

        // Os dois códigos abaixo servem para extrairmos as coordenadas da String.
        int coordenadaLinha = Character.getNumericValue(coordenadasJogador.charAt(0));
        int coordenadaColuna = Character.getNumericValue(coordenadasJogador.charAt(2));

        // Com base nas coordenadas, será setado true ou false para o abate, que adiante é utilizado para atualizar a matriz.
        for (int i = 0; i < quantNavios; i++) {
            if (posicoesNavios[0][i] == coordenadaLinha && posicoesNavios[1][i] == coordenadaColuna) {
                abate = true;
                
                // Alterando a posição de cada jogada para 0,0 após cada jogada garante que o jogador não repita aquela mesma jogada.
                posicoesNavios[0][i] = 0;
                posicoesNavios[1][i] = 0;
                break;
            } else {
                abate = false;
            }
        }

        // Validação utilizada para atualizar a matriz, infomando se houve ou não abate.
        if (abate) {
            System.out.println("Você abateu um navio!! ;)\n");
            imprimeMatriz(matriz, coordenadaLinha, coordenadaColuna, "X");
        } else {
            System.out.println("Ops, você errou o tiro. :(\n");
            imprimeMatriz(matriz, coordenadaLinha, coordenadaColuna, "O");
        }
        return abate;
    }

    public static void main(String[] args) {
        new BatalhaNaval();
    }
}