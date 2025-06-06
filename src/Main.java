import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Main {
    public static void main(String[] args) {
        String[] arquivos = {"src/data/teams1.txt", "src/data/teams4.txt"};
        BaseballElimination be = new BaseballElimination(arquivos[1]);
        System.out.println("Número de times: "+be.numberOfTeams());
        System.out.println("INFORMAÇÃO GERAL DOS TIMES: ");

        for(int i = 0; i < be.numberOfTeams(); i++){
            String nomeTime = be.time(i);
            String eliminado = "";
            if(be.isEliminated(nomeTime)){
                eliminado = "sim";
            }else{
                eliminado = "não";
            }
            System.out.println("Time: "+i+"; Nome: "+nomeTime+"; Vitórias: "+be.wins(nomeTime)+"; Derrotas: "+be.losses(nomeTime)+"; Sobrando: "+be.remaining(nomeTime)+" Eliminado: "+eliminado);
            System.out.println(" ");
        }
    }
}