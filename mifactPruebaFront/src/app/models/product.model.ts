export interface Product {
    idProduct: number;
    name: string;
    description: string;
    count: number;
    price: number;
}

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
}

export interface ProductRequest {
    idProduct: number;
    name: string;
    description: string;
    count: number;
    price: number;
}