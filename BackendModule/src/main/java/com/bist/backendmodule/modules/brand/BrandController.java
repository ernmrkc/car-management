package com.bist.backendmodule.modules.brand;

import com.bist.backendmodule.modules.brand.command.handlers.CreateBrandCommandHandler;
import com.bist.backendmodule.modules.brand.command.handlers.DeleteBrandCommandHandler;
import com.bist.backendmodule.modules.brand.command.handlers.UpdateBrandCommandHandler;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.models.UpdateBrandCommand;
import com.bist.backendmodule.modules.brand.query.handlers.GetAllBrandsQueryHandler;
import com.bist.backendmodule.modules.brand.query.handlers.GetBrandByIdQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling brand-related operations.
 */
@RestController
@RequestMapping("/brand")
@Tag(name = "Brand Controller", description = "Operations related to brands")
public class BrandController {
    private final CreateBrandCommandHandler createBrandCommandHandler;
    private final DeleteBrandCommandHandler deleteBrandCommandHandler;
    private final UpdateBrandCommandHandler updateBrandCommandHandler;
    private final GetAllBrandsQueryHandler getAllBrandsQueryHandler;
    private final GetBrandByIdQueryHandler getBrandByIdQueryHandler;

    public BrandController(CreateBrandCommandHandler createBrandCommandHandler,
                           DeleteBrandCommandHandler deleteBrandCommandHandler,
                           UpdateBrandCommandHandler updateBrandCommandHandler,
                           GetAllBrandsQueryHandler getAllBrandsQueryHandler,
                           GetBrandByIdQueryHandler getBrandByIdQueryHandler) {
        this.createBrandCommandHandler = createBrandCommandHandler;
        this.deleteBrandCommandHandler = deleteBrandCommandHandler;
        this.updateBrandCommandHandler = updateBrandCommandHandler;
        this.getAllBrandsQueryHandler = getAllBrandsQueryHandler;
        this.getBrandByIdQueryHandler = getBrandByIdQueryHandler;
    }

    /**
     * Endpoint to create a new brand.
     *
     * @param brand         The brand object to be created.
     * @param bindingResult The binding result for validation.
     * @return ResponseEntity with HTTP status.
     */
    @PreAuthorize("hasAuthority('PERMISSON_CREATE_BRAND')")
    @PostMapping
    @Operation(summary = "Create a new brand", description = "Creates a new brand with the given details.")
    public ResponseEntity<Void> createBrand(@Valid @RequestBody Brand brand, BindingResult bindingResult) {
        return createBrandCommandHandler.execute(brand, bindingResult);
    }

    /**
     * Endpoint to delete a brand by ID.
     *
     * @param id The ID of the brand to be deleted.
     * @return ResponseEntity with HTTP status.
     */
    @PreAuthorize("hasAuthority('PERMISSON_DELETE_BRAND')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a brand", description = "Deletes a brand by its ID.")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        return deleteBrandCommandHandler.execute(id, null);
    }

    /**
     * Endpoint to update an existing brand.
     *
     * @param id            The ID of the brand to be updated.
     * @param brand         The updated brand object.
     * @param bindingResult The binding result for validation.
     * @return ResponseEntity containing the updated brand.
     */
    @PreAuthorize("hasAuthority('PERMISSON_EDIT_BRAND')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a brand", description = "Updates an existing brand with the given details.")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @Valid @RequestBody Brand brand, BindingResult bindingResult) {
        UpdateBrandCommand updateBrandCommand = new UpdateBrandCommand(id, brand);
        return updateBrandCommandHandler.execute(updateBrandCommand, bindingResult);
    }

    /**
     * Endpoint to get all brands.
     *
     * @return ResponseEntity containing the list of all brands.
     */
    @PreAuthorize("hasAuthority('PERMISSON_GET_BRAND')")
    @GetMapping
    @Operation(summary = "Get all brands", description = "Retrieve a list of all brands.")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return getAllBrandsQueryHandler.execute(null);
    }

    /**
     * Endpoint to get a brand by ID.
     *
     * @param id The ID of the brand to be retrieved.
     * @return ResponseEntity containing the retrieved brand.
     */
    @PreAuthorize("hasAuthority('PERMISSON_GET_BRAND')")
    @GetMapping("/{id}")
    @Operation(summary = "Get brand by ID", description = "Retrieve a brand by its ID.")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return getBrandByIdQueryHandler.execute(id);
    }
}
