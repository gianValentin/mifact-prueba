import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse, Product, ProductRequest } from '../models/product.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly http = inject(HttpClient);
  private readonly path = `${environment.apiUrl}/product`;

  getAllPaginado(q: string, page: number, size: number, sort?: string): Observable<PageResponse<Product>> {
    let params = new HttpParams().set('q', q).set('page', page).set('size', size);
    if (sort) params = params.set('sort', sort);
    return this.http.get<PageResponse<Product>>(`${this.path}/page`, { params });
  }

  create(data: ProductRequest): Observable<Product> {
    return this.http.post<Product>(this.path, data);
  }

  update(id: number, data: ProductRequest): Observable<Product> {
    return this.http.put<Product>(`${this.path}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.path}/${id}`);
  }
}
