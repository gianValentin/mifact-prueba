import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'product',
        loadComponent: () => import('./pages/product/product.component').then((m) => m.ProductComponent),
    },
    { path: '', redirectTo: '/product', pathMatch: 'full' },
    { path: '**', redirectTo: '/product' },
];
