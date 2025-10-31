import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int id = 1;
        Scanner entrada = new Scanner(System.in);
        Urna eleicao_2025 = new Urna();
        Populacao sta = new Populacao();
        int opcao = 199;
        boolean sair = false;

        while(sair != true) {
            //MENU DE OPCOES
            System.out.println("Escolha uma opcao: \n#1 -> Cadastrar candidato \n#2 -> Cadastrar eleitor" + 
            "\n#3 -> Listar eleitores \n#4 -> Listar candidatos \n#5 -> Listar populacao \n#6 -> Buscar eleitor" +
            "\n#7 -> Votar \n#-1 -> Sair ");
            opcao = entrada.nextInt();
            entrada.nextLine(); //LIMPA O BUFFER

            switch (opcao) {
                //CADASTRAR CANDIDATO
                case 1:
                    System.out.println("Digite o nome do candidato(a): ");
                    String nome = entrada.nextLine();

                    System.out.println("Digite a idade do candidato(a): ");
                    int idade = entrada.nextInt();
                    entrada.nextLine();

                    System.out.println("Digite o sexo do candidato(a): ");
                    String sexo = entrada.next();

                    System.out.println("Digite o partido do candidato(a): ");
                    String partido = entrada.next();

                    System.out.println("Digite o numero do candidato(a): ");
                    String num = entrada.next();

                    Candidato cand = new Candidato(nome, idade, sexo, partido, num);
                    sta.add_pessoa(cand);
                    break;

                //CADASTRAR ELEITOR    
                case 2:
                    System.out.println("Digite o nome do eleitor(a): ");
                    String nome_elei = entrada.nextLine();
                    
                    System.out.println("Digite a idade do eleitor(a): ");
                    int idade_elei = entrada.nextInt();
                    entrada.nextLine();

                    System.out.println("Digite o sexo do eleitor(a): ");
                    String sexo_elei = entrada.nextLine();

                    System.out.println("Digite o numero do titulo do eleitor(a): ");
                    String titulo = entrada.nextLine();

                    Eleitor eleito = new Eleitor(nome_elei, idade_elei, sexo_elei, titulo);
                    sta.add_pessoa(eleito);
                    break;
                
                //LISTAR ELEITORES
                case 3:
                    sta.listar_eleitores();
                    break;

                //LISTAR CANDIDATOS
                case 4:
                    sta.listar_candidatos();
                    break;

                //LISTAR TODA POPULACAO
                case 5:
                    sta.listar();
                    break;

                //BUSCAR ELEITOR    
                case 6:
                    System.out.println("Digite o nome do eleitor que desja buscar");
                    String buscado = entrada.nextLine();
                    Pessoa econtrado = sta.buscar_eleitor(buscado);
                    econtrado.toString();
                    break;

                //VOTAR    
                case 7:
                    System.out.println("Digite o nome de quem vai votar");
                    String votador = entrada.nextLine();
                    Eleitor aux = sta.buscar_eleitor(votador);
                    
                    if(aux.get_votou() == false){
                        Voto voto = new Voto(id);
                        id++;
                        System.out.println("Digite o reitor em que deseja votar");
                        String voto_reit = entrada.nextLine();
                        System.out.println("Digite o vice em que deseja votar");
                        String voto_vice = entrada.nextLine();
                        voto.set_reitor(voto_reit);
                        voto.set_vice(voto_vice);
                        eleicao_2025.add_voto(voto);
                        aux.votar();
                        System.out.println("Voto registrado com sucesso \n");
                    } else System.out.println("Esta pessoa ja votou");
                    break;

                //SAIR    
                case -1:
                    System.out.println("Até mais");
                    sair = true;
                    break;
                
                //MENSAGEM PADRAO    
                default:
                    System.out.println("Opcao invalida !\n");
                    break;
            }
        }
        entrada.close();
    }   
}  