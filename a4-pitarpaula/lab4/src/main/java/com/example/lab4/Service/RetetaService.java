package com.example.lab4.Service;


import Domain.Ingredient;
import Domain.Reteta;
import com.example.lab4.Repository.DuplicateEntityException;
import com.example.lab4.Repository.EntityNotFoundException;
import com.example.lab4.Repository.IRepository;
import com.example.lab4.Repository.RepositoryException;

import java.util.ArrayList;
import java.util.Collection;

public class RetetaService {
    IRepository<Reteta> repository;

    public RetetaService(IRepository repository){
        this.repository = repository;
    }

    public void add(int id, ArrayList<Ingredient> ingredients) throws DuplicateEntityException, RepositoryException {
        this.repository.add(new Reteta(id,time, ingredients, type));
    }

    public void remove(int id) throws EntityNotFoundException, RepositoryException {
        this.repository.remove(id);
    }

    public Reteta find(int id){return repository.find(id);}

    public void update(int id,int time, String type) throws EntityNotFoundException, RepositoryException {
        if(find(id)==null)
            throw new EntityNotFoundException("Entity doesn t exist!");
        //find(id).setDate(date);
        ArrayList<Ingredient> ingredients = find(id).getIngrediente();
        repository.update(id,new Reteta(id,time, ingredients,type));
    }

    public Collection<Reteta> getAll() throws RepositoryException {
        return repository.getAll();
    }
}

