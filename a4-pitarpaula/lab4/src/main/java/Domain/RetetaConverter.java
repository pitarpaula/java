package Domain;

import javax.swing.*;

import java.util.ArrayList;

public class RetetaConverter implements IEntityConverter<Reteta> {
    @Override
    public String toString(Reteta order) {
        StringBuilder result = new StringBuilder();
        result.append(order.getID()).append(";");

        result.append(order.getTime()).append(";");

        for (Ingredient ingredient : order.getIngrediente()) {
            result.append(ingredient.getID()).append(",").append(ingredient.getType()).append(",");
        }
        result.deleteCharAt(result.length() - 1); //stergem ultima virgula

        result.append(";").append(order.getType());

        return result.toString();
    }

    @Override
    public Reteta fromString(String line) {
        String[] parts = line.split(";");

        if (parts.length >= 3) {
            int retetaId = Integer.parseInt(parts[0]);
            int retetaTime = Integer.parseInt(parts[1]);
            ArrayList<Ingredient> ingrediente = new ArrayList<>();

            String[] ingredientInfo = parts[2].split(",");
            for (int i = 0; i < ingredientInfoInfo.length; i += 2) {
                int cakeId = Integer.parseInt(ingredientInfoInfo[i]);
                String cakeType = ingredientInfoInfo[i + 1];
                ingrediente.add(new Ingredient(cakeId, cakeType));
            }

            String  type = ImageIcon.parseInt(tokens[0], tokens[1]);

            return new Reteta(retetaId, retetaTime, ingrediente, type);
        } else {
            throw new IllegalArgumentException("Invalid input format for Reteta: " + line);
        }

    }
}
