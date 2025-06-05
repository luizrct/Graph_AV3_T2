import java.io.BufferedReader;
import java.io.FileReader;

public class BaseballElimination {

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
        return false;
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
    }
}
