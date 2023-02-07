import { api } from '../api'
import { Dish, DishRequestParams } from '../../models/Dish'

export type DishesResponse = Dish[]

export const dishesApi = api.injectEndpoints({
  endpoints: (build) => ({
    getDishes: build.query<DishesResponse, DishRequestParams>({
      query: (params) => ({ url: `/dish?page=${params.page}&sort=${params.sort}` }),
      providesTags: (result = []) => [
        ...result.map(({ id }) => ({ type: 'Dishes', id } as const)),
        { type: 'Dishes' as const, id: 'LIST' },
      ],
    }),
    getDishesByProducts: build.query<DishesResponse, void>({
      query: () => ({ url: '/dish/available'}),
      providesTags: (result = []) => [
        ...result.map(({ id }) => ({ type: 'Dishes', id } as const)),
        { type: 'Dishes' as const, id: 'LIST' },
      ],
    })
  })
})


export const {
  useGetDishesQuery
} = dishesApi
