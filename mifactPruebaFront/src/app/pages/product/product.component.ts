import { CurrencyPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { PaginatorModule } from 'primeng/paginator';
import { TableModule } from 'primeng/table';
import { PageEvent } from '../../interfaces/page-event';
import { ProductService } from '../../services/product.service';
import { Product, ProductRequest } from '../../models/product.model';
import { InputTextModule } from 'primeng/inputtext';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ProductEditDialogComponent } from '../../components/dialogs/product-edit-dialog/product-edit-dialog.component';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { AutoComplete, AutoCompleteCompleteEvent } from 'primeng/autocomplete';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    ButtonModule,
    InputTextModule,
    TableModule,
    PaginatorModule,
    CurrencyPipe,
    InputTextModule,
    FloatLabelModule,
    AutoComplete,
    FormsModule
  ],
  providers: [DialogService],
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent implements OnInit {

  private readonly productService = inject(ProductService);
  private readonly dialogService = inject(DialogService);

  ref: DynamicDialogRef | undefined;

  products!: Product[];

  totalRecords: number = 0;
  rows: number = 0;
  first: number = 0;

  name: string = "";

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(event?: PageEvent) {
    this.first = event?.first ?? 0;
    this.rows = event?.rows ?? 10;
    const page = this.first / this.rows;
    const sort = 'id,asc';
    this.productService.getAllPaginado(this.name, page, this.rows, sort).subscribe({
      next: (pageResponse) => {
        this.products = pageResponse.content;
        this.totalRecords = pageResponse.totalElements;
      },
      error: (err) => console.error('Error loading products', err),
    });
  }

  create(): void {
    this.ref = this.dialogService.open(ProductEditDialogComponent, {
      header: 'Formulario de creación de producto',
      modal: true,
      focusOnShow: false,
      data: {
        product: {
          idProduct: null,
          name: '',
          description: '',
          count: 0,
          price: 0
        }
      }
    });

    this.ref.onClose.subscribe((result: any) => {
      if (result) {
        this.productService.create(result as ProductRequest).subscribe({
          next: () => this.loadProducts(),
          error: (err) => console.error('Error creating product', err),
        });
      }
    });
  }

  updateProduct(product: Product): void {
    this.ref = this.dialogService.open(ProductEditDialogComponent, {
      header: 'Formulario de edición de producto',
      modal: true,
      focusOnShow: false,
      data: {
        product: product
      }
    });

    this.ref.onClose.subscribe((result: any) => {
      if (result) {
        this.productService.update(product.idProduct, result as ProductRequest).subscribe({
          next: () => this.loadProducts(),
          error: (err) => console.error('Error updating product', err),
        });
      }
    });
  }

  deleteProduct(product: Product): void {
    this.productService.delete(product.idProduct).subscribe({
      next: () => this.loadProducts(),
      error: (err) => console.error('Error deleting product', err),
    });
  }

  search(event: AutoCompleteCompleteEvent) {
    this.loadProducts();
  }
}
