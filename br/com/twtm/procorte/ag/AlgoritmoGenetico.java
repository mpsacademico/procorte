package br.com.twtm.procorte.ag;

import java.util.Arrays;
import java.util.Random;

public class AlgoritmoGenetico {

    /**
     * ******************************************************
     * PARÂMETROS GENÉTICOS
     * ******************************************************
     */
    public static final int TP = 20; //tamanho da população
    public static final double TC = 0.6; //taxa de cruzamento
    public static final double TM = 0.2; //taxa de mutação
    public static final double IG = 0.2; //intervalo de geração
    public static final double NG = 10; //número de gerações     
    //quantidades geradas com base nas taxas
    static double QC = TP * TC; //quantidade de cruzamentos
    static double QM = TP * TM; //quantidade de mutações
    static double QP = TP * IG; //quantidade da nova população

    public static void main(String[] args) {

        /**
         * ******************************************************
         * Criação e preparação das estruturas de dados
         * ******************************************************
         */
        int p[][] = new int[3][3]; //produtos: 0 - d1; 1 - d2; 2 - qtd 
        int qtp = p.length; //quantidade de tipos de produtos

        p[0][0] = 2; //altura
        p[0][1] = 2; //largura
        p[0][2] = 3; //quantidade

        p[1][0] = 4;
        p[1][1] = 4;
        p[1][2] = 5;

        p[2][0] = 6;
        p[2][1] = 6;
        p[2][2] = 1;

        //dimensões da matéria-prima
        int d1mp = 10; //altura
        int d2mp = 10; //largura

        int pop[][][] = new int[TP][d1mp][d2mp]; //armazena população inicial
        double fa[] = new double[TP + 1]; //armazena frequência absoluta e seu somatório
        double fr[] = new double[TP + 1]; //armazena frequência relativa e seu somatório

        //preenche matriz de matéria-prima com algum valor (valor escolhido = 8)
        for (int x = 0; x < TP; x++) {
            for (int i = 0; i < d1mp; i++) {
                for (int j = 0; j < d2mp; j++) {
                    pop[x][i][j] = 8;
                }
            }
        }

        /**
         * ******************************************************
         * POPULAÇÃO INICIAL: gera a população inicial
         * ******************************************************
         */
        Random ran = new Random();
        int pa = 0; //produto aleatório

        //método de iniciação: randômica uniforme
        for (int x = 0; x < TP; x++) {
            pa = ran.nextInt(qtp);
            for (int i = 0; i < p[pa][0]; i++) {
                for (int j = 0; j < p[pa][1]; j++) {
                    if (pop[x][i][j] == 8) {
                        pop[x][i][j] = pa;
                    } else {
                        break;
                    }
                }
            }
        }

        /**
         * ******************************************************
         * APTIDÃO (FITNESS): realiza a função de aptidão
         * ******************************************************
         */
        //obtém o somatório da frequência absoluta
        float somatorio = 0;
        for (int x = 0; x < TP; x++) {
            int c = 0;
            double v = 0;
            for (int i = 0; i < d1mp; i++) {
                for (int j = 0; j < d2mp; j++) {
                    if (pop[x][i][j] == 8) {
                        c++;
                    }
                }
            }
            v = 1.0 / c;
            fa[x] = v;
            //System.out.println("FA["+x+"] = " + v);
            somatorio += v;
        }
        fa[20] = somatorio;
        //System.out.println("Soma Frequência Absoluta: " + somatorio);

        //obtém a frequência relativa
        double z = 0;
        for (int x = 0; x < TP; x++) {
            int c = 0;
            double v = 0;
            for (int i = 0; i < d1mp; i++) {
                for (int j = 0; j < d2mp; j++) {
                    if (pop[x][i][j] == 8) {
                        c++;
                    }
                }
            }
            v = (1.0 / c) / somatorio;
            z += v;
            fr[x] = v;
            //System.out.println("FIT["+x+"]: " + v);
        }
        fr[20] = z;
        //System.out.println("Soma Frequência Relativa: " + z);

        /**
         * ******************************************************
         * Exibe a população inicial
         * ******************************************************
         */
        System.out.println("*****POPULAÇÃO INICIAL*****");
        for (int x = 0; x < TP; x++) {
            for (int i = 0; i < d1mp; i++) {
                for (int j = 0; j < d2mp; j++) {
                    //System.out.print("["+i+"]["+j+"]");
                    System.out.print(pop[x][i][j] + " ");
                }
                System.out.println("");
            }
            //System.out.println("");
        }

        /**
         * ******************************************************
         * Exibe as frequências absoluta e relativa
         * ******************************************************
         */
        System.out.println("*****FREQUÊNCIAS*****");
        for (int i = 0; i <= TP; i++) {
            System.out.printf("%f %f \n", fa[i], fr[i]);
            //para imprimir as casas decimais com precisão:
            //System.out.printf("%.10f %.10f \n",fa[i], fr[i]);
        }

        /*
         //ordena a fitness para obter os melhores valores
         Arrays.sort(fa);
         //imprime em ordem crescente
         System.out.println("Em ordem crescente: ");
         for (double i : fa) {
         System.out.println(i);
         }*/
        //nova população
        int npop[][][] = new int[TP][d1mp][d2mp];
        int t = 0;

        /**
         * ******************************************************
         * CRUZAMENTO
         * ******************************************************
         */
        System.out.println("*****QUANTIDADES*****");
        System.out.println("Quantidade de cruzamentos (QC): " + QC);
        while (t <= QC) {
            int filho1[][] = new int[d1mp][d2mp];
            int filho2[][] = new int[d1mp][d2mp];
            int pai1[][] = pop[seleciona()];
            int pai2[][] = pop[seleciona()];
            for (int i = 0; i < d1mp / 2; i++) {
                for (int j = 0; j < d2mp / 2; j++) {
                    filho1[i][j] = pai1[i][j];
                    filho2[i][j] = pai2[i][j];
                }
            }
            for (int i = d1mp / 2; i < d1mp; i++) {
                for (int j = d2mp / 2; j < d2mp / 2; j++) {
                    filho1[i][j] = pai2[i][j];
                    filho2[i][j] = pai1[i][j];
                }
            }
            npop[t] = filho1;
            t++;
            npop[t] = filho2;
            t++;
        }

        /**
         * ******************************************************
         * MUTAÇÃO
         * ******************************************************
         */
        System.out.println("Quantidade de mutações (QM): " + QM);
        while (t <= QM) {
            int sel = seleciona();
            int ind[][] = pop[sel];
            pa = ran.nextInt(qtp);
            for (int i = 0; i < d1mp; i++) {
                for (int j = 0; j < d2mp; j++) {
                    ind[i][j] = 8;
                }
            }
            for (int i = 0; i < p[pa][0]; i++) {
                for (int j = 0; j < p[pa][1]; j++) {
                    if (ind[i][j] == 8) {
                        ind[i][j] = pa;
                    } else {
                        break;
                    }
                }
            }
            npop[t] = ind;
            t++;
        }

        /**
         * ******************************************************
         * NOVA GERAÇÃO
         * ******************************************************
         */
        System.out.println("Quantidade da nova população (QP): " + QP);
        System.out.println("*****NOVA POPULAÇÃO*****");
        while (t <= QP) {
            int sel = seleciona();
            int ind[][] = pop[sel];
            npop[t] = ind;
            t++;
        }
        for (int x = 0; x < TP; x++) {
            for (int i = 0; i < d1mp; i++) {
                for (int j = 0; j < d2mp; j++) {
                    System.out.print(npop[x][i][j] + " ");
                }
                System.out.println("");
            }
        }
    }

    /**
     * ******************************************************
     * SELEÇÃO 
     * ******************************************************
     */
    public static int seleciona() {
        Random ran = new Random();
        int va = ran.nextInt(TP);
        return va;
    }

}
