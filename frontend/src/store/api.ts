import { RootState } from './store'
import { createApi, fetchBaseQuery, retry } from '@reduxjs/toolkit/query/react'
import { API_URL } from '../environment'

const baseQuery = fetchBaseQuery({
  baseUrl: API_URL,
  prepareHeaders: (headers, { getState }) => {
    const token = (getState() as RootState).auth.token
    if (token) {
      headers.set('Authorization', `Bearer ${token}`)
      headers.set('Access-Control-Allow-Origin', '*')
    }
    return headers
  },
})

const baseQueryWithRetry = retry(baseQuery, { maxRetries: 6 })

export const api = createApi({
  reducerPath: 'baseApi',
  baseQuery: baseQueryWithRetry,
  tagTypes: ['Dishes', 'Products'],
  endpoints: () =>  ({})
})
