public class Voto {
    private int id;
    private String reitor;
    private String vice;

    Voto(int id){
        this.id = id;
    }

    public int get_id(){
        return this.id;
    }

    public void set_reitor(String reito){
        this.reitor = reito;
    }

    public void set_vice(String vic){
        this.vice = vic;
    }

    public String get_reitor(){
        return reitor;
    }

    public String get_vice(){
        return vice;
    }
    
    public String toString(){
        return "ID do voto: " + get_id() + "\nReitor: " + get_reitor() + "\nVice: " + get_vice();
    }

    public static void main(String[] args){
    System.out.println("AQUI");
    Voto carlos = new Voto(1);
    carlos.set_reitor("Demostenes");
    carlos.set_vice("Schneider");
    System.out.println(carlos.get_reitor());
    System.out.println(carlos.get_id());
    System.out.println(carlos.toString());
    }
}