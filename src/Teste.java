public class Teste {
    public static void main(String[] args){
        String[] arquivos = {"src/data/teste" ,"src/data/teams1.txt", "src/data/teams4.txt", "src/data/teams5.txt", "src/data/teams60.txt"};
        BaseballElimination be = new BaseballElimination(arquivos[0]);
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
        //be.certificateOfElimination("Philadelphia");
        System.out.println(be.G);
    }
}
