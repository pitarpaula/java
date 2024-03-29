package com.example.lab4.Repository;


import Domain.Ingredient;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IngredientDbRepository extends MemoryRepository<Ingredient> {
    private String JDBC_URL = "jdbc:sqlite:retete.db";

    private Connection connection;


    public IngredientDbRepository() {
        openConnection();
        createTable();
        insertRandomData();
    }

    public void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable() {
        try (final Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS ingrediente(id int primary key, type varchar(400));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Ingredient ingredient) throws RepositoryException, DuplicateEntityException {
        super.add(ingredient);
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO ingrediente values (?,?);")) {
            stmt.setInt(1, ingredient.getID());
            stmt.setString(2, ingredient.getType());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ingredient> getAll() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * from ingrediente;")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ingredient c = new Ingredient(rs.getInt(1), rs.getString(2));
                ingredients.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public void remove(int id) {

        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM ingrediente WHERE id = ?;")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Ingredient ingredient) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE ingrediente SET type = ? WHERE id = ?;")) {
            stmt.setString(1, ingredient.getType());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ingredient find(int id) {
        String sql = "SELECT * FROM ingrediente WHERE id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int cakeId = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    return new Ingredient(id, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratarea erorilor în mod adecvat în producție
        }

        return null;
    }


    private static final List<String> ingredienteTypes = List.of("ulei_de_masline", "usturoi", "oua", "sare", "piper", "lapte");
    public void insertRandomData(){
        if(getAll().size() == 0)
        {
            Random random = new Random();
            for (int i = 1; i <= 5; i++) {
                String type = ingredienteTypes.get(random.nextInt(ingredienteTypes.size()));
                int randomIdCake = i;
                try {
                    add(new Ingredient(randomIdCake, type));
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void clear(){
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM cakes;")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}








