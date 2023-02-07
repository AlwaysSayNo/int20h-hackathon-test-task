import { Category, Product, ProductRequestParams } from '../../models/Product'
import { api } from '../api'
import { DishesResponse } from './DishesService'
import { Dish } from '../../models/Dish'

type ProductsResponse = Product[]
type CategoriesResponse = Category[]

type GetProductsForDishResponse = {
  dish: Dish,
  products: Product[]
}
export const productsApi = api.injectEndpoints({
  endpoints: (build) => ({
    getProductsByCategory: build.query<ProductsResponse, ProductRequestParams>({
      query: (params) => ({ url: `/product-by-category?page${params.page}&id=${params.categoryId}`}),
      providesTags: (result = []) => [
        ...result.map(({ id }) => ({ type: 'Products', id } as const)),
        { type: 'Products' as const, id: 'LIST' },
      ],
    }),
    getAllCategories: build.query<CategoriesResponse, void>({
      query: () => ({ url: '/product/categories'}),
      providesTags: (result = []) => [
        ...result.map(({ id }) => ({ type: 'Products', id } as const)),
        { type: 'Products' as const, id: 'LIST' },
      ],
    }),
    getProductsForDish: build.query<GetProductsForDishResponse, number>({
      query: (id) => ({ url: `/dish/info?id=${id}`}),
      providesTags: (result = { products: [], dish: {} as Dish }) => [
        ...result.products.map(({ id }) => ({ type: 'Products', id } as const)),
        { type: 'Products' as const, id: 'LIST' },
      ],
    }),
    getUserProducts: build.query<ProductsResponse, void>({
      query: () => ({ url: '/product' }),
      providesTags: (result = []) => [
        ...result.map(({ id }) => ({ type: 'Products', id } as const)),
        { type: 'Products' as const, id: 'LIST' },
      ],
    })
  })
})

export const {
  useGetProductsForDishQuery,
} = productsApi
