import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogService, DynamicDialogComponent, DynamicDialogRef } from 'primeng/dynamicdialog';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumber } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { ProductRequest } from '../../../models/product.model';

@Component({
  selector: 'app-product-edit-dialog',
  standalone: true,
  imports: [InputTextModule, InputNumber, FloatLabelModule, ButtonModule, FormsModule],
  templateUrl: './product-edit-dialog.component.html',
  styleUrl: './product-edit-dialog.component.scss'
})
export class ProductEditDialogComponent implements OnInit, OnDestroy {

  instance: DynamicDialogComponent | undefined;

  idProduct: number = null as any;
  name: string = "";
  description: string = "";
  count: number = 0;
  price: number = 0;

  constructor(public ref: DynamicDialogRef, private dialogService: DialogService) {
    this.instance = this.dialogService.getInstance(this.ref);
  }

  ngOnInit() {
    if (this.instance && this.instance.data) {
      this.idProduct = this.instance.data.product['idProduct'];
      this.name = this.instance.data.product['name'];
      this.description = this.instance.data.product['description'];
      this.count = this.instance.data.product['count'];
      this.price = this.instance.data.product['price'];
    }
  }

  edit() {
    const request: ProductRequest = {
      idProduct: this.idProduct,
      name: this.name,
      description: this.description,
      count: this.count,
      price: this.price
    };
    this.ref.close(request);
  }

  close() {
    this.ref.close();
  }

  ngOnDestroy() {
    if (this.ref) {
      this.ref.close();
    }
  }

}
