package com.example.lab4.Repository;

import Domain.Ingredient;
import Domain.Reteta;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RetetaDbRepository extends MemoryRepository<Reteta> {

    private String JDBC_URL = "jdbc:sqlite:retete.db";

    private Connection connection;

    public RetetaDbRepository() {
        openConnection();
        createTable();
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

        try (final java.sql.Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS retete(id int primary key, int time, String type);");
            stmt.execute("CREATE TABLE IF NOT EXISTS retete_ingrediente(id INT AUTO_INCREMENT PRIMARY KEY,id_reteta int, id_ingredient int,foreign key(id_reteta) references retete(id), foreign key(id_ingredient) references ingrediente(id));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Reteta reteta) throws RepositoryException, DuplicateEntityException {
        try (java.sql.PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders values (?,?);")) {
            stmt.setInt(1, reteta.getID());
            //stmt.setDate(2, reteta.getTime()));
            //stmt.setDate(3, reteta.getType()))
            stmt.executeUpdate();
            try (java.sql.PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO retete_ingrediente(id_reteta, id_ingredient) values (?,?);")) {
                for (int i = 0; i < reteta.getIngrediente().size(); i++) {
                    stmt2.setInt(1, reteta.getID());
                    stmt2.setInt(2, reteta.getIngrediente().get(i).getID());
                    stmt2.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /*public void update(int id, Reteta order){
        try (java.sql.PreparedStatement stmt = connection.prepareStatement("UPDATE orders SET date=? WHERE id=?;")) {
            stmt.setDate(1, Date.valueOf(order.getDate()));
            stmt.setInt(2, order.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void remove(int id) {

        try (java.sql.PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders WHERE id = ?;")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            java.sql.PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM order_cakes WHERE id_order = ?;");
            stmt2.setInt(1, id);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    public Reteta find(int id) {
        try {
            // Query to fetch order details
            String retetaQuery = "SELECT * FROM retete WHERE id = ?";
            try (PreparedStatement orderStmt = connection.prepareStatement(retetaQuery)) {
                PreparedStatement retetaStmt;
                retetaStmt.setInt(1, id);
                ResultSet retetaRs = retetaStmt.executeQuery();

                if (retetaRs.next()) {
                    int time  = retetaRs.getTime("timpul");
                    String type = retetaRs.getString("type");

                    // Query to fetch associated cakes
                    String Query = "SELECT c.id, c.type FROM order_cakes oc " +
                            "JOIN cakes c ON oc.id_cake = c.id " +
                            "WHERE oc.id_order = ?";
                    try (PreparedStatement cakeStmt = connection.prepareStatement(cakeQuery)) {
                        cakeStmt.setInt(1, id);
                        ResultSet cakeRs = cakeStmt.executeQuery();

                        ArrayList<Ingredient> ingredients = new ArrayList<>();
                        while (cakeRs.next()) {
                            int cakeId = cakeRs.getInt("id");
                            String cakeType = cakeRs.getString("type");
                            Ingredient ingredient = new Ingredient(cakeId, cakeType);
                            ingredients.add(ingredient);
                        }

                        return new Reteta(id, ingredients, orderDate);
                    }
                }
            }
        } catch (SQLException e) {
            // Log the exception using a logging framework
            throw new RuntimeException("Error finding Reteta with ID " + id, e);
        }

        return null;
    }

    public List<Reteta> getAll() {
        List<Reteta> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM orders")){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                orders.add(find(id));
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static List<Ingredient> generateRandomCakes(List<Ingredient> allIngredients, int numberOfCakes) {
        List<Ingredient> selectedIngredients = new ArrayList<>();
        int totalCakes = allIngredients.size();

        // Limit the number of cakes to a maximum of 5
        numberOfCakes = Math.min(numberOfCakes, 5);

        List<Integer> indexes = new ArrayList<>();
        for (int i = 1; i <= totalCakes; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i = 0; i < numberOfCakes; i++) {
            int randomIndex = indexes.get(i);
            Ingredient randomIngredient = allIngredients.get(randomIndex - 1); // Adjust index to be zero-based
            selectedIngredients.add(randomIngredient);
        }

        return selectedIngredients;
    }



    public void insertRandomData(IRepository<Ingredient> cakeRepository) {
        if (getAll().size() == 0) {
            try {
                Random random = new Random();
                for (int i = 1; i <= 100; i++) {
                    int numberOfCakes = random.nextInt(5) + 1; // +1 pt ca nu vrem sa fie 0 si vrem sa fie maxim 5
                    List<Ingredient> randomIngredients = generateRandomCakes((List<Ingredient>) cakeRepository.getAll(), numberOfCakes);
                    LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(365));
                    Reteta order = new Reteta(i, new ArrayList<>(randomIngredients), randomDate);
                    add(order);
                }
            } catch (RepositoryException | DuplicateEntityException e) {
                e.printStackTrace();
            }
        }
    }




}


