package com.example.lab4.Service;


import Domain.Ingredient;
import com.example.lab4.Repository.DuplicateEntityException;
import com.example.lab4.Repository.EntityNotFoundException;
import com.example.lab4.Repository.IRepository;
import com.example.lab4.Repository.RepositoryException;

import java.util.Collection;

public class IngredientService {
    IRepository<Ingredient> repository;

    public IngredientService(IRepository<Ingredient> repository){
        this.repository = repository;
    }

    public void add(int id, String type) throws DuplicateEntityException, RepositoryException {
        repository.add(new Ingredient(id,type));
    }

    public void delete(int id) throws EntityNotFoundException, RepositoryException {
        repository.remove(id);
    }

    public Ingredient find(int id){
        return repository.find(id);
    }

    public void update(int id, String type) throws EntityNotFoundException, RepositoryException {
        if(find(id)==null)
            throw new EntityNotFoundException("Entity doesn t exist!");
        //find(id).setType(type);
        repository.update(id, new Ingredient(id,type));
    }
    public Collection<Ingredient> getAll() throws RepositoryException {
        return repository.getAll();
    }
}

