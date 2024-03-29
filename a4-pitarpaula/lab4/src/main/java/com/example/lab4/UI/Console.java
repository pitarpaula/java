package com.example.lab4.UI;


import Domain.Ingredient;
import Domain.Reteta;
import com.example.lab4.Repository.DuplicateEntityException;
import com.example.lab4.Repository.EntityNotFoundException;
import com.example.lab4.Repository.RepositoryException;
import com.example.lab4.Service.IngredientService;
import com.example.lab4.Service.RetetaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Console {
    IngredientService ingredientService;
    RetetaService retetaService;

    public Console(IngredientService ingredientService, RetetaService retetaService){
        this.ingredientService = ingredientService;
        this.retetaService = retetaService;
    }

    private void addCake(Scanner scanner){
        try {
            System.out.println("Give the id: ");
            int id = scanner.nextInt();
            System.out.println("Give the type: ");
            String type = scanner.next();
            this.ingredientService.add(id, type);
        }
        catch (DuplicateEntityException e) {
            System.out.println(e.toString());
        } catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void deleteCake(Scanner scanner){
        try{
            System.out.println("Give the id of the cake you want to remove: ");int id = scanner.nextInt();
            ingredientService.delete(id);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private void updateCake(Scanner scanner){
        try{
            System.out.println("Give the id of the cake you want to update: ");
            int id= scanner.nextInt();
            System.out.println("Give the new type of the cake: ");
            String type = scanner.next();
            ingredientService.update(id, type);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
    private void addOrder(Scanner scanner) {

        try {
            System.out.println("Give the id of the order: ");
            int id = scanner.nextInt();
            System.out.println("The ingredients available are: ");
            showCakes();
            System.out.println("How many ingredients do you want to order? ");
            int nr = scanner.nextInt();
            if(nr<=0)
                throw new IllegalArgumentException("The order must have at least one cake!");
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < nr; i++) {
                System.out.println("Give the id of the ingredient you want to order: ");
                int id_cake = scanner.nextInt();
                Ingredient ingredient = ingredientService.find(id_cake);
                if(ingredient ==null)
                    throw new EntityNotFoundException("This ingredient is not available!");
                ingredients.add(ingredient);
            }
            System.out.println("Give the date of the order(yyyy-MM-dd): ");
            String dateString = scanner.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            retetaService.add(id, ingredients, date);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (DuplicateEntityException e) {
            System.out.println(e.toString());
        }
        catch (DateTimeParseException e) {
            System.out.println(e.toString());;
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private void deleteOrder(Scanner scanner){
        try{
            System.out.println("Give the id of the order you want to delete: ");
            int id= scanner.nextInt();
            retetaService.remove(id);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private void updateOrder(Scanner scanner){
        try{
            System.out.println("Give the id of the order you want to update: ");
            int id= scanner.nextInt();
            System.out.println("Give the new date of the order(yyyy-MM-dd): ");
            String dateString = scanner.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            retetaService.update(id, date);
        } catch (DateTimeParseException e) {
            System.out.println(e.toString());;
        } catch (EntityNotFoundException e) {
            System.out.println(e.toString());
        }catch (RepositoryException e){
            System.out.println(e.toString());
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void showCakes(){
        try{
            Collection<Ingredient> ingredients = ingredientService.getAll();
            if(ingredients.size()==0)
                System.out.println("There are no ingredients");
            for(Ingredient ingredient : ingredients)
                System.out.println(ingredient);
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void showOrders(){
        try{
            Collection<Reteta> orders = retetaService.getAll();
            if(orders.size()==0)
                System.out.println("There are no orders");
            for(Reteta order:orders)
                System.out.println(order);
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void addCakesDefault(){
        try{
            ingredientService.add(1, "chocolate");
            ingredientService.add(2, "vanilla");
            ingredientService.add(3, "cheesecake");
            ingredientService.add(4, "dark_chocolate");
            ingredientService.add(5, "lemon");
        }
        catch (DuplicateEntityException e) {
            System.out.println(e.toString());
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void nrCakesPerDay(){
        try{
            Collection<Reteta> orders = retetaService.getAll();
            Map<LocalDate, Long> cakesPerDay = orders.stream()
                    .collect(Collectors.groupingBy(
                            Reteta::getDate,
                            Collectors.summingLong(order -> order.getCakes().size())
                    ));
            cakesPerDay.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " cakes"));
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void nrCakesPerMonth(){
        try{
            Collection<Reteta> orders = retetaService.getAll();
            Map<String, Long> cakesPerMonth = orders.stream()
                    .collect(Collectors.groupingBy(
                            order -> order.getDate().getMonth().toString(),
                            Collectors.summingLong(order -> order.getCakes().size())
                    ));
            cakesPerMonth.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " cakes"));
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void mostOrderedCakes(){
        try{
            Collection<Reteta> orders = retetaService.getAll();
            Map<String, Long> cakesCount = orders.stream()
                    .flatMap(order -> order.getCakes().stream())
                    .collect(Collectors.groupingBy(
                            Ingredient::getType,
                            Collectors.counting()
                    ));
            cakesCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> {
                        String cakeType = entry.getKey();
                        long totalOrders = entry.getValue();
                        System.out.println("Ingredient: " + cakeType + ", Total number of orders: " + totalOrders);
                    });
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void runMenu(){
        //addCakesDefault();
        while(true){
            printMenu();
            String option;
            Scanner scanner = new Scanner(System.in);
            option = scanner.next();
            switch (option){
                case "1":{
                    addCake(scanner);
                    break;
                }
                case "2":{
                    deleteCake(scanner);
                    break;
                }
                case "3":{
                    updateCake(scanner);
                    break;
                }
                case "4": {
                    addOrder(scanner);
                    break;
                }
                case "5": {
                    deleteOrder(scanner);
                    break;
                }
                case "6": {
                    updateOrder(scanner);
                    break;
                }
                case "7": {
                    nrCakesPerDay();
                    break;
                }
                case "8": {
                    nrCakesPerMonth();
                    break;
                }
                case "9": {
                    mostOrderedCakes();
                    break;
                }
                case "c":{
                    showCakes();
                    break;
                }
                case "o":{
                    showOrders();
                    break;
                }
                case "x":{
                    return;
                }
                default:{
                    System.out.println("Wrong option. Retry: ");
                }
            }
        }
    }

    private void printMenu(){
        System.out.println("1. Add cake");
        System.out.println("2. Remove a cake");
        System.out.println("3. Update a cake");
        System.out.println("4. Add an order: ");
        System.out.println("5. Remove an order: ");
        System.out.println("6. Update the date of an order: ");
        System.out.println("7. Number of cakes ordered every day: ");
        System.out.println("8. Number of cakes ordered per month: ");
        System.out.println("9.Most ordered cakes: ");
        System.out.println("c. Show all cakes");
        System.out.println("o. Show all orders: " );
        System.out.println("x. EXIT");
    }
}

