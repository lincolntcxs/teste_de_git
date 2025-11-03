public class Voto {
    private int id;
    private String reitor;
    private String vice;

    public Voto(int id, String reitor, String vice){
        this.id = id;
        this.reitor = reitor;
        this.vice = vice;
    }

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
}