import assets.FlowEdge;

import java.io.BufferedReader;
import java.io.FileReader;

public class BaseballElimination {
    private static int INFINITY = Integer.MAX_VALUE;
    private static int N;
    private int[] wins;
    private int[] losses;
    private int[][] remaining;
    private boolean[] eliminado;
    private String[] teams;

    public BaseballElimination(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String linha = br.readLine();
            N = Integer.valueOf(linha);
            wins = new int[N];
            losses = new int[N];
            remaining = new int[N][];
            teams = new String[N];
            eliminado = new boolean[N];
            int c = 0;
            while (true){
                linha = br.readLine();
                if(linha == null){
                    break;
                }
                adicionandoDados(linha, c);
                c++;
            }
        } catch (Exception erro){
            System.out.println("Erro na leitura do arquivo: "+erro);
        }

        //DEFININDO QUAIS TIMES FORAM ELIMINADOS
        if(N == 1){
            eliminado[0] = false;
        }else if(N == 2){
            eliminado[0] = false;
            eliminado[1] = false;
            //Comparação direta entre 2 times
            if(wins[0] + remaining[0][0] < wins[1]){
                eliminado[0] = true;
            }else if(wins[1] + remaining[1][0] < wins[0]){
                eliminado[1] = true;
            }
        }else{
            int V = 2 + ((N - 1) * (N - 2))/ 2 + (N - 1);
            for(int i = 0; i < teams.length; i++){
                eliminado[i] = verificacaoEliminacao(V, i);
            }
        }
    }

    public int numberOfTeams(){
        return N;
    }

    private Integer time(String team){
        for(int i = 0; i < teams.length; i++){
            if(team.equals(teams[i])){
                return i;
            }
        }
        return null;
    }

    public String time(int i){
        return teams[i];
    }

    public int wins(String team){
        return wins[time(team)];
    }

    public int losses(String team){
        return losses[time(team)];
    }

    public int remaining(String team){
        return remaining[time(team)][0];
    }

    public int against(String team1, String team2){
        return 0;
    }

    public boolean isEliminated(String team){
        return eliminado[time(team)];
    }


    private void adicionandoDados(String linha, int c){
        String[] palavras = linha.trim().split("\\s+");
        teams[c] = palavras[0];
        wins[c] = Integer.valueOf(palavras[1]);
        losses[c] = Integer.valueOf(palavras[2]);
        int[] remaining = new int[N + 1];
        for(int i = 3; i < palavras.length; i++){
            remaining[i - 3] = Integer.valueOf(palavras[i]);
        }
        this.remaining[c] = remaining;
    }



    private boolean verificacaoEliminacao(int V, int time){
        FlowNetwork fn = new FlowNetwork(V);
        int[] times = new int[N-1];
        int c2 = 0;
        for(int i = 0; i < N; i++){
            if(i != time){
                times[c2] = i;
                c2++;
            }
        }
        int n_partidas = ((N - 1) * (N - 2)) / 2;
        int[][] partidas =  new int[n_partidas][];
        int c = 0;
        for(int i = 0; i < N; i ++){
            if(i != time){
                for(int j = 1; j < remaining[i].length; j++){
                    if(j-1 != time && i != j-1){
                        if(c == n_partidas){
                            break;
                        }
                        if(verificarPartidas(partidas, new int[]{i, j-1}, c)){
                            int[] partida = {i, j-1, remaining[i][j]};
                            partidas[c] = partida;
                            c++;
                        }
                    }
                }
            }
        }

        int somaRemaingGames = 0;
        //adicionando as arestas das partidas e dos times
        for(int i = 1; i < n_partidas+1; i++){
            //adicionando as arestas das partidas
            somaRemaingGames += partidas[i-1][2];
            fn.addEdge(new FlowEdge(0, i, partidas[i-1][2]));

            //adicionando as arestas dos times
            int w1 = partidas[i-1][0];
            int w2 = partidas[i-1][1];
            fn.addEdge(new FlowEdge(i, w1+n_partidas+1, INFINITY));
            fn.addEdge(new FlowEdge(i, w2+n_partidas+1, INFINITY));
        }
        //adicionando as ultimas arestas
        for(int i = n_partidas+1; i < V-1; i ++){
            int capacidade = wins[time] + remaining[time][0] - wins[i - n_partidas - 1];
            if(capacidade < 0){
                return true;
            }
            fn.addEdge(new FlowEdge(i, V-1, capacidade));
        }

        FordFulkerson ff = new FordFulkerson(fn, 0, V-1);
        if(ff.value() >= somaRemaingGames){
            return false;
        }else{
            return true;
        }
    }


    private boolean verificarPartidas(int[][] partidas, int[] partida, int c){
        for(int i = 0; i < c; i++){
            if(partidas[i][0] == partida[0] && partidas[i][1] == partida[1]){
                return false;
            }
            if(partidas[i][0] == partida[1] && partidas[i][1] == partida[0]){
                return false;
            }
        }
        return true;
    }
}