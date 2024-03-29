package Domain;

public class IngredientConverter implements IEntityConverter<Ingredient> {
    @Override
    public String toString(Ingredient object) {
        return object.getID() + "," + object.getType() ;
    }

    @Override
    public Ingredient fromString(String line) {
        String[] tokens = line.split(",");
        return new Ingredient(Integer.parseInt(tokens[0]), tokens[1]);
    }
}
