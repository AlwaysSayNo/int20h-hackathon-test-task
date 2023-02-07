export interface Product {
  id: number,
  name: string,
  category: string,
  imageUrl: string | null
}

export interface Category {
  id: string,
  name: string
}

export type ProductRequestParams = {
  page: number
  categoryId: string
}
