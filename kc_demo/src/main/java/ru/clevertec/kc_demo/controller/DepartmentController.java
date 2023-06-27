package ru.clevertec.kc_demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.kc_demo.dto.request.DepartmentCreateDto;
import ru.clevertec.kc_demo.dto.response.DepartmentReadDto;
import ru.clevertec.kc_demo.service.DepartmentService;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees/departments")
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    public ResponseEntity<DepartmentReadDto> create(@RequestBody @Valid DepartmentCreateDto createDto){
        return ResponseEntity.status(CREATED)
                .body(service.create(createDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentReadDto> findById(@PathVariable Long id){
        return ResponseEntity.status(OK)
                .body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentReadDto>> findAll(@PageableDefault Pageable pageable){
        return ResponseEntity.status(OK)
                .body(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentReadDto> updateById(@PathVariable Long id, @Valid @RequestBody DepartmentCreateDto createDto){
        return ResponseEntity.status(ACCEPTED)
                .body(service.updateById(id, createDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
