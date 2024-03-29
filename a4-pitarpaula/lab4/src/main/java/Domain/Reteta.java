package Domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reteta extends Entity {
    private ArrayList<Ingredient> ingrediente;
    private String type;
    private int time;
    private static final long serialVersionUID = 1000L;
    public Reteta(int id){
        super(id);
    }

    public Reteta(int id, int time, ArrayList<Ingredient> ingredients, String type){
        super(id);
        this.type = type;
        this.time = time;
        this.ingrediente = ingrediente;

    }



    public ArrayList<Ingredient> getIngrediente(){
        return this.ingrediente;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setIngrediente(ArrayList<Ingredient> ingrediente){
        this.ingrediente = ingrediente;
    }

    public int getTime(){return this.time;}
    public void setTime(int time){
        this.time = time;
    }

    @Override
    public String toString(){
        return "Reteta{" +
                "ID= "+ID+
                ",tip= "+type+
                ",timp= "+time+
                ", list of ingredients= "+ ingrediente +
                "}";
    }
}
