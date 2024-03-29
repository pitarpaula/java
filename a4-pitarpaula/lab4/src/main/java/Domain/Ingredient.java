package Domain;

public class Ingredient extends Entity {

    private static final long serialVersionUID = 1000L;
    private String type;

    public Ingredient(int ID, String type){
        super(ID);
        this.type = type;
    }

    public Ingredient(int id){
        super(id);
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return "Ingredient{" +
                "ID= "+ID+
                ", type= "+ type+
                "}";
    }
}
