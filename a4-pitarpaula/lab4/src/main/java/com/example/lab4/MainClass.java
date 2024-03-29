package com.example.lab4;


import Domain.*;
import com.example.lab4.GUI.HelloApplication;
import com.example.lab4.Repository.*;
import com.example.lab4.Service.IngredientService;
import com.example.lab4.Service.RetetaService;

import com.example.lab4.UI.Console;

import java.util.Objects;

public class MainClass {
    public static void main(String[] args){


        IEntityConverter<Ingredient> ingredientConverter = new IngredientConverter();
        IEntityConverter<Reteta> retetaConverter = new RetetaConverter();
        IRepository<Ingredient> repositoryIngredient= null;
        IRepository<Reteta> repositoryReteta = null;


        Settings setari = Settings.getInstance();
        if(Objects.equals(setari.getRepoType(), "database")){
            repositoryIngredient = new IngredientDbRepository();
            repositoryReteta = new RetetaDbRepository();
            ((IngredientDbRepository) repositoryIngredient).openConnection();
            ((RetetaDbRepository) repositoryReteta).openConnection();
            ((RetetaDbRepository) repositoryReteta).insertRandomData(repositoryIngredient);

        }
        if (Objects.equals(setari.getRepoType(), "memory")) {
            repositoryIngredient = new MemoryRepository<>();
            repositoryReteta = new MemoryRepository<>();
        }
        if (Objects.equals(setari.getRepoType(), "text")){
            repositoryIngredient = new TextFileRepository<>(setari.getRepoCake(), ingredientConverter);
            repositoryReteta = new TextFileRepository<>(setari.getRepoOrder(), retetaConverter);
        }
        if (Objects.equals(setari.getRepoType(), "binary")){
            repositoryIngredient = new BinaryFileRepository<>(setari.getRepoCake());
            repositoryReteta = new BinaryFileRepository<>(setari.getRepoOrder());
        }







        IngredientService ingredientService = new IngredientService(repositoryIngredient);
        RetetaService retetaService = new RetetaService(repositoryReteta);
        Console console = new Console(ingredientService, retetaService);


        //console.runMenu();

         HelloApplication.main(args);
    }
}
