import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../environments/environment";

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

@Injectable({
  providedIn: 'root'
})
export class CarService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  /**
   * Fetches the list of all brands from the backend.
   * @returns     Fetched brands data.
   */
  getBrands(): Observable<any> {
    return this.http.get(`${this.baseUrl}/brand`);
  }

  /**
   * Fetches a paginated list of cars from the backend with sorting, searching, and filtering
   * @param page          Current page number to fetch
   * @param size          Number of items per page
   * @param sortBy        The field to sort the results by.
   * @param sortDir       The direction of sorting (asc - desc)
   * @param searchTerm    The term to search within the car specifications or brands.
   * @param filters       An object containing various filter criteria such as brand, specification, engine liter, etc.
   * @returns             Fetched data including content (list of cars), totalPages, and totalElements.
   */
  getCars(page: number, size: number, sortBy: string, sortDir: string, searchTerm: string, filters: any): Observable<{
    content: Car[],
    totalPages: number,
    totalElements: number
  }> {
    let params = new HttpParams()
      .set('pageNo', page.toString())
      .set('pageSize', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir)
      .set('searchTerm', searchTerm);

    if (filters.brand) params = params.set('brand', filters.brand);
    if (filters.specification) params = params.set('specification', filters.specification);
    if (filters.engineLiter) params = params.set('engineLiter', filters.engineLiter);
    if (filters.isNew) params = params.set('isNew', filters.isNew);
    if (filters.minPrice) params = params.set('minPrice', filters.minPrice);
    if (filters.maxPrice) params = params.set('maxPrice', filters.maxPrice);
    if (filters.minDate) params = params.set('minDate', filters.minDate);
    if (filters.maxDate) params = params.set('maxDate', filters.maxDate);

    return this.http.get<{
      content: Car[],
      totalPages: number,
      totalElements: number
    }>(`${this.baseUrl}/car/byPage`, {params});
  }

  /**
   * Fetches the complete list of cars from the backend.
   * This method retrieves all cars without pagination, filtering, or sorting.
   *
   * @returns {Observable<Car[]>} An observable containing the list of all cars.
   */
  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.baseUrl}/car`);
  }

  /**
   * Adds a new car to the backend
   * @param car     The car data to be added
   * @param file    The image file associated with the car, if any
   */
  addCar(car: Partial<Car>, file: File | null): Observable<Car> {
    const formData: FormData = new FormData();
    formData.append('car', new Blob([JSON.stringify(car)], {type: 'application/json'}));
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.post<Car>(`${this.baseUrl}/car`, formData);
  }

  /**
   * Updates an existing car in the backend.
   * @param carId       The ID of the car to be updated.
   * @param car         The updated car data.
   * @param file        The new image file associated with the car, if any.
   */
  updateCar(carId: number, car: Partial<Car>, file: File | null): Observable<Car> {
    const formData: FormData = new FormData();
    formData.append('car', new Blob([JSON.stringify(car)], {type: 'application/json'}));
    if (file) {
      formData.append('file', file, file.name);
    }
    return this.http.put<Car>(`${this.baseUrl}/car/${carId}`, formData);
  }

  /**
   * Deletes a car from the backend
   * @param carId         The ID of the car to be deleted
   */
  deleteCar(carId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/car/${carId}`);
  }

  /**
   * Adds a new brand to the backend
   * @param brand       The brand data to be added
   */
  addBrand(brand: Brand): Observable<Brand> {
    return this.http.post<Brand>(`${this.baseUrl}/brand`, brand);
  }

  /**
   * Updates an existing brand in the backend.
   * @param brandId       The ID of the brand to be updated.
   * @param brand         The updated brand data.
   */
  updateBrand(brandId: number, brand: Partial<Brand>): Observable<Brand> {
    return this.http.put<Brand>(`${this.baseUrl}/brand/${brandId}`, brand);
  }

  /**
   * Deletes a brand from the backend.
   * @param brandId       The ID of the brand to be deleted.
   */
  deleteBrand(brandId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/brand/${brandId}`);
  }

  /**
   * Generates the full URL to download the image for the given image filename.
   * @param filename      The name of the image file.
   */
  getImageUrl(filename: string): string {
    return `${this.baseUrl}/uploads/${filename}`;
  }

}
