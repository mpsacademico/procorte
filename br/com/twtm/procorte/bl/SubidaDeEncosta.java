package br.com.twtm.procorte.bl;

public class SubidaDeEncosta {

    public static void main(String args[]) {

        int p[][] = new int[3][3];
        int qtp = p.length;
        int d1mp = 10;
        int d2mp = 10;
        int mp[][] = new int[d1mp][d2mp];

        p[0][0] = 2; //altura
        p[0][1] = 2; //largura
        p[0][2] = 3; //quantidade
        p[1][0] = 3;
        p[1][1] = 3;
        p[1][2] = 5;
        p[2][0] = 4;
        p[2][1] = 4;
        p[2][2] = 1;

        Procorte pc = new Procorte(p, mp);
        int atual[][] = pc.geraSolucaoInicial();
        int va = pc.avalia(atual);
        int proximo[][];
        int vp;
        while (true) {
            proximo = pc.geraSucessores();
            vp = pc.avalia(proximo);
            if (vp >= va) {
                break;
            } else {
                atual = proximo;
                va = vp;
            }
        }

    }

}
