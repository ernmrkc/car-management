import {Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import { CarService } from '../car.service';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';


interface Car {
  id?: number;
  brand: Brand;
  specification: string;
  engineLiter: number;
  isNew: boolean;
  price: number;
  releaseDateTime: string;
  image: { id: number; filename: string; fullPath: string };
}

interface Brand {
  id: number;
  name: string;
}

@Component({
  selector: 'app-main',
  standalone: true,
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class MainComponent implements OnInit{
  cars: Car[] = [];
  allCars: Car[] = [];
  brands: Brand[] = [];
  newBrandName: string = '';
  cart: Car[] = [];
  totalPrice: number = 0;
  filteredCars: Car[] = [];
  minPrice: number = 0;
  maxPrice: number = 0;

  // Panel visibility flags
  showNewCarPanel: boolean = false;
  showEditCarPanel: boolean = false;
  showFilterPanel: boolean = false;
  showBrandsPanel: boolean = false;

  // New car data
  newCar: Partial<Car & { brandId: number }> = {};
  selectedFile: File | null = null;
  editCarIndex: number | null = null;

  // Sort variables
  sortDirection: 'asc' | 'desc' = 'asc';
  sortByKey: keyof Car | 'brand' | '' = '';

  // Paging variables
  currentPage: number = 0;
  pageSize: number = 5;
  totalPages: number = 0;
  totalElements: number = 0;

  // Filter variables
  brandFilter: string = '';
  specificationFilter: string = '';
  engineLiterFilter: number | null = null;
  isNewFilter: boolean | null = null;
  minPriceFilter: number | null = null;
  maxPriceFilter: number | null = null;
  minDateFilter: string = '';
  maxDateFilter: string = '';
  searchTerm: string = '';

  username: string;

  constructor(private carService: CarService,
              private router: Router) {
    this.username = 'user';
  }

  ngOnInit() {
    this.loadBrands();
    this.loadAllCars();
    this.loadCars(this.currentPage, this.pageSize, 'id', 'asc');
  }


  /**
   * This function gets all brands from the endpoint
   * GET localhost:10150/brand
   */
  loadBrands() {
    this.carService.getBrands().pipe(
      tap((data: Brand[]) => {
        this.brands = data;
      })
    ).subscribe({
      next: () => {},
      error: (error) => {
        console.error('Error fetching brands', error);
      }
    });
  }

  /**
   * This function gets all cars from the endpoint without pagination and filters
   * GET localhost:10150/car
   */
  loadAllCars() {
    this.carService.getAllCars().pipe(
      tap((data: Car[]) => {
        this.allCars = data;
        this.calculateMinMaxPrice();
      })
    ).subscribe({
      next: () => {},
      error: (error) => {
        console.error('Error fetching all cars', error);
      }
    });
  }

  /**
   * This function gets all cars from the endpoint with given filters and pagination data
   * GET localhost:10150/car/byPage
   * @param page          The current page number
   * @param size          The number of items per page
   * @param sortBy        The key to sort the results by  (brand, releaseDateTime etc.)
   * @param sortDir       Sort direction asc - desc
   * @param searchTerm    Search term to filter result
   */
  loadCars(page: number, size: number, sortBy: string, sortDir: string, searchTerm: string = '') {
    const filters = {
      brand: this.brandFilter,
      specification: this.specificationFilter,
      engineLiter: this.engineLiterFilter,
      isNew: this.isNewFilter,
      minPrice: this.minPriceFilter,
      maxPrice: this.maxPriceFilter,
      minDate: this.minDateFilter,
      maxDate: this.maxDateFilter,
    };

    this.carService.getCars(page, size, sortBy, sortDir, searchTerm, filters).pipe(
      tap((response: { content: Car[], totalPages: number, totalElements: number }) => {
        this.cars = response.content;
        this.filteredCars = this.cars;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      })
    ).subscribe({
      next: () => {},
      error: (error) => {
        console.error('Error fetching cars', error);
      }
    });
  }

  /**
   * Calculates and sets the minimum and maximum price of all cars.
   * If there are no cars available, both minPrice and maxPrice are set to 0.
   */
  calculateMinMaxPrice() {
    if (this.allCars.length > 0) {
      this.minPrice = Math.min(...this.allCars.map(car => car.price));
      this.maxPrice = Math.max(...this.allCars.map(car => car.price));
    } else {
      this.minPrice = 0;
      this.maxPrice = 0;
    }
  }

  /**
   * Retrieves the full URL for the given image filename using the car service
   * @param filename      The name of the image file.
   */
  getImageUrl(filename: string): string {
    return this.carService.getImageUrl(filename);
  }

  /**
   * Adds a new car to the corresponding list and updates the display
   * POST localhost:10150/car
   */
  addNewCar() {
    const carData = {
      ...this.newCar,
      brand: this.brands.find(brand => brand.id === this.newCar.brandId)!
    };

    this.carService.addCar(carData, this.selectedFile).subscribe({
      next: (newCar) => {
        this.cars.push(newCar);
        this.filterCars();
        this.resetNewCarForm();
        this.loadCars(this.currentPage, this.pageSize, this.sortByKey || 'id', this.sortDirection);
      },
      error: (error) => {
        console.error('Error adding new car', error);
      }
    });
  }

  /**
   * Updates an existing car and refreshes the car list.
   * PUT localhost:10150/car/{carId}
   */
  updateCar() {
    if (this.editCarIndex !== null) {
      const carId = this.cars[this.editCarIndex].id;
      const carData = {
        brandId: this.newCar.brandId,
        specification: this.newCar.specification,
        engineLiter: this.newCar.engineLiter,
        isNew: this.newCar.isNew,
        price: this.newCar.price,
        releaseDateTime: this.newCar.releaseDateTime
      };

      this.carService.updateCar(carId!, carData, this.selectedFile).subscribe({
        next: (updatedCar) => {
          this.cars[this.editCarIndex!] = updatedCar;
          this.resetNewCarForm();
          this.loadCars(this.currentPage, this.pageSize, this.sortByKey || 'id', this.sortDirection);
        },
        error: (error) => {
          console.error('Error updating car', error);
        }
      });
    }
  }

  /**
   * Deletes a car from the list and updates the display
   * DELETE localhost:10150/car/{carId}
   * @param index     Index of the car to delete
   */
  deleteCar(index: number) {
    const carId = this.cars[index].id;
    this.carService.deleteCar(carId!).subscribe({
      next: () => {
        this.cars.splice(index, 1);
      },
      error: (error) => {
        console.error('Error deleting car', error);
      }
    });
  }

  /**
   * Resets the form after adding or editing car
   */
  resetNewCarForm() {
    this.newCar = {};
    this.selectedFile = null;
    this.showNewCarPanel = false;
    this.showEditCarPanel = false;
    this.editCarIndex = null;
  }

  /**
   * Adds a new brand to the list of brands
   * POST localhost:10150/brand
   */
  addBrand() {
    const newBrand: Brand = { id: 0, name: this.newBrandName };
    this.carService.addBrand(newBrand).subscribe({
      next: (brand) => {
        this.brands.push(brand);
        this.newBrandName = '';
        this.loadBrands();
      },
      error: (error) => {
        console.error('Error adding brand', error);
      }
    });
  }

  /**
   * Updates an existing brand
   * PUT localhost:10150/brand/{brandId}
   * @param brand     Brand to be updated
   */
  updateBrand(brand: Brand) {
    const brandData = { name: brand.name };
    this.carService.updateBrand(brand.id, brandData).subscribe({
      next: () => {
      },
      error: (error) => {
        console.error('Error updating brand', error);
      }
    });
    this.closeBrandsPanel();
  }

  /**
   * Deletes a brand by ID
   * DELETE localhost:10150/brand/{brandId}
   * @param brandId     The ID of the brand to be deleted
   */
  deleteBrand(brandId: number) {
    this.carService.deleteBrand(brandId).subscribe({
      next: () => {
        this.brands = this.brands.filter(brand => brand.id !== brandId);
      },
      error: (error) => {
        console.error('Error deleting brand', error);
      }
    });
  }

  /**
   * Opens the brands panel
   */
  openBrandsPanel() {
    this.showBrandsPanel = true;
  }

  /**
   * Closes the brands panel
   */
  closeBrandsPanel() {
    this.showBrandsPanel = false;
  }

  /**
   * Opens the panel to add a new car.
   */
  openNewCarPanel() {
    this.showNewCarPanel = true;
    this.showEditCarPanel = false;
    this.newCar = {};
    this.selectedFile = null;
  }

  /**
   * Opens the panel to edit an existing car.
   * @param car       The car to be edited
   * @param index     The index of the car in the cars array
   */
  openEditCarPanel(car: Car, index: number) {
    this.showEditCarPanel = true;
    this.showNewCarPanel = false;
    this.newCar = { ...car, brandId: car.brand.id };
    this.editCarIndex = index;
    this.selectedFile = null;
  }

  /**
   * Closes both the new car and edit car panels
   */
  closeCarPanel() {
    this.showNewCarPanel = false;
    this.showEditCarPanel = false;
  }

  /**
   * Opens the filter panel.
   */
  openFilterPanel() {
    this.showFilterPanel = true;
  }

  /**
   * Closes the filter panel.
   */
  closeFilterPanel() {
    this.showFilterPanel = false;
  }

  /**
   * Adds the selected car to the cart and updates the total price
   * @param car       The car to be added to the cart
   */
  addToCart(car: Car) {
    this.cart.push(car);
    this.totalPrice += car.price || 0;
  }

  /**
   * Navigates to the next page of cars if available
   */
  nextPage() {
    if (this.currentPage + 1 < this.totalPages) {
      this.currentPage++;
      this.loadCars(this.currentPage, this.pageSize, this.sortByKey || 'id', this.sortDirection, this.searchTerm);
    }
  }

  /**
   * Navigates to the previous page of cars if available
   */
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadCars(this.currentPage, this.pageSize, this.sortByKey || 'id', this.sortDirection, this.searchTerm);
    }
  }

  /**
   * Filters cars based on the search term and sorts the filtered list.
   */
  filterCars() {
    this.filteredCars = this.cars.filter(car =>
      car.brand.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      car.specification.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
    this.sortCars();
  }

  /**
   * Handles changes in the search term and reloads the cars
   */
  onSearchTermChange() {
    this.loadCars(0, this.pageSize, this.sortByKey || 'id', this.sortDirection, this.searchTerm);
  }

  /**
   * Applies the selected filters and reloads the cars
   */
  onFilterChange() {
    this.closeFilterPanel();
    this.loadCars(0, this.pageSize, this.sortByKey || 'id', this.sortDirection, this.searchTerm);
  }

  /**
   * Resets all filters to their default values and applies the change.
   */
  resetFilters() {
    this.brandFilter = '';
    this.specificationFilter = '';
    this.engineLiterFilter = null;
    this.isNewFilter = null;
    this.minPriceFilter = null;
    this.maxPriceFilter = null;
    this.minDateFilter = '';
    this.maxDateFilter = '';
    this.onFilterChange();
  }

  /**
   * Handles the file selection event for uploading a file
   * @param event       The file selection event
   */
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] || null;
  }

  /**
   * Sorts the filteredCars array based on the specified sortByKey and sortDirection
   */
  sortCars() {
    if (this.sortByKey !== '') {
      this.filteredCars.sort((a, b) => {
        let valueA: any;
        let valueB: any;

        if (this.sortByKey === 'brand') {
          valueA = a.brand.name;
          valueB = b.brand.name;
        } else {
          valueA = a[this.sortByKey as keyof Car];
          valueB = b[this.sortByKey as keyof Car];
        }

        if (valueA === undefined || valueB === undefined) {
          return 0;
        }

        if (typeof valueA === 'string' && typeof valueB === 'string') {
          return this.sortDirection === 'asc' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
        }

        if (typeof valueA === 'number' && typeof valueB === 'number') {
          return this.sortDirection === 'asc' ? valueA - valueB : valueB - valueA;
        }

        if (typeof valueA === 'boolean' && typeof valueB === 'boolean') {
          return this.sortDirection === 'asc' ? (valueA === valueB ? 0 : valueA ? -1 : 1) : (valueA === valueB ? 0 : valueA ? 1 : -1);
        }

        return 0;
      });
    }
  }

  /**
   * Sets the sorting key and direction, then sorts the cars
   * @param key       The key to sort by.
   */
  sortBy(key: keyof Car | 'brand') {
    if (this.sortByKey === key) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortByKey = key;
      this.sortDirection = 'asc';
    }
    this.loadCars(this.currentPage, this.pageSize, this.sortByKey || 'id', this.sortDirection, this.searchTerm);
  }

  /**
   * Returns a color based on the price, from blue to red
   * @param price       The price of the car
   * @returns           A color string in rgb format
   */
  getPriceColor(price: number): string {
    const normalizedPrice = (price - this.minPrice) / (this.maxPrice - this.minPrice);

    // Convert normalized value to a color (blue to red gradient)
    const red = Math.floor(255 * normalizedPrice);
    const blue = Math.floor(255 * (1 - normalizedPrice));
    return `rgb(${red}, 0, ${blue})`;
  }
}

