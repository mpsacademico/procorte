package br.com.twtm.procorte.bl;

import java.util.Random;

public class Procorte {

    int p[][];
    int qtp;
    int d1mp = 10;
    int d2mp = 10;
    int mp[][];

    public Procorte(int p[][], int mp[][]) {    
        this.p = p;
        this.mp = mp;
        this.qtp = p.length;
        for (int i = 0; i < d1mp; i++) {
            for (int j = 0; j < d2mp; j++) {
                mp[i][j] = 8;
            }
        }
    }

    public int[][] geraSolucaoInicial() {
        Random ran = new Random();
        int pa = ran.nextInt(qtp); //produto aleatório
        System.out.println("Produto Aleatório: " + pa);
        for (int i = 0; i < p[pa][0]; i++) {
            for (int j = 0; j < p[pa][1]; j++) {
                if (mp[i][j] == 8) {
                    mp[i][j] = pa;
                } else {
                    break;
                }
            }
        }
        return mp;
    }

    public int avalia(int mp[][]) {
        int conta = 0;
        for (int i = 0; i < d1mp; i++) {
            for (int j = 0; j < d2mp; j++) {
                if (mp[i][j] == 8) {
                    conta++;
                }
            }
        }
        return conta;
    }

    public void imprime() {
        for (int i = 0; i < d1mp; i++) {
            for (int j = 0; j < d2mp; j++) {
                //System.out.print("["+i+"]["+j+"]");
                System.out.print(mp[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int[][] geraSucessores() {
        int sucessor[][] = null;
        int resto;
        int restoMenor = 0;
        for (int i = 0; i < qtp; i++) {
            resto = (d1mp * d2mp) - (p[i][0] * p[i][1]);
            if(resto<restoMenor){
                sucessor = geraSolucaoInicial();
            }
        }
        return sucessor;
    }

}
