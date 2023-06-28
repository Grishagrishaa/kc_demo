package ru.clevertec.kc_demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * This interface defines common CRUD (Create, Read, Update, Delete) operations for a service.
 *
 * @param <C> the type of the CREATE DTO (Data Transfer Object)
 * @param <R> the type of the READ DTO (Data Transfer Object)
 */
public interface CrudService<C, R> {

    /**
     * Creates a new entity based on the provided create DTO.
     *
     * @param createDto the createDTO containing the data for the new entity
     * @return the created entity as a read DTO
     */
    R create(C createDto);

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity to find
     * @return the found entity as a read DTO
     */
    R findById(Long id);

    /**
     * Retrieves all entities, paginated, based on the provided pageable parameters.
     *
     * @param pageable the pageable parameters for pagination and sorting
     * @return a page of entities as read DTOs
     */
    Page<R> findAll(Pageable pageable);

    /**
     * Updates an entity identified by its ID with the data from the provided create DTO.
     *
     * @param id        the ID of the entity to update
     * @param createDto the create DTO containing the updated data
     * @return the updated entity as a read DTO
     */
    R updateById(Long id, C createDto);

    /**
     * Deletes an entity identified by its ID.
     *
     * @param id the ID of the entity to delete
     */
    void deleteById(Long id);
}

