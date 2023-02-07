export interface Dish {
  id: number,
  name: string,
  recipe: string,
  difficulty: number,
  votesAmount: number,
  imageUrl: string
}

export enum SortType {
  Asc = 'asc',
  Desc = 'desc'
}

export type DishRequestParams = {
  page: number
  sort: SortType
}
