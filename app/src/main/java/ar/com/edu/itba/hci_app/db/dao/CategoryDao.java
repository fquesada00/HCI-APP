package ar.com.edu.itba.hci_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.com.edu.itba.hci_app.db.entity.CategoryEntity;

@Dao
public abstract class CategoryDao {
    @Query("SELECT * FROM categories")
    public abstract LiveData<List<CategoryEntity>> getCategories();

    @Query("SELECT * FROM categories WHERE id = :id")
    public abstract LiveData<CategoryEntity> getCategoryByIdId(int id);

    @Query("SELECT * FROM categories WHERE name = :name")
    public abstract LiveData<CategoryEntity> getCategoryByName(int name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CategoryEntity... categories);

    @Update
    public abstract void update(CategoryEntity category);

    @Delete
    public abstract void delete(CategoryEntity category);

    @Query("DELETE FROM categories")
    public abstract void deleteAll();
}
