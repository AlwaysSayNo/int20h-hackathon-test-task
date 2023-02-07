import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { RootState } from '../../store'
import { AuthBody, User } from '../../../models/User'
import axios from 'axios'
import { API_URL } from '../../../environment'

export interface AuthState {
  user: User | null,
  token: string | null,
  isAuthenticated: boolean
}

const initialState = {
  user: null,
  token: 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmNkZSIsImlhdCI6MTY3NTc5NTU5OSwiZXhwIjoxNzA3MjY0MDAwfQ.J279HPhfYbQokkeP6SSHvQOtdaJkoSbOf21rMbUWy7oLQ-FgFdtCoJQBiZ0ALSAN',
  isAuthenticated: true,
} as AuthState

export type AuthRegisterResponse = {
  id: number,
  login: string
}

export const login = createAsyncThunk(
  '/login',
  async (authBody: AuthBody, thunkAPI) => {
    const response = await axios.post<string>(API_URL + 'login', authBody)
    console.log(response, 'redux login response')
    localStorage.setItem('token', response.data)
    if(response.data) {
      localStorage.setItem('user', JSON.stringify(authBody))
    }
    return { user: authBody, token: response.data, isAuthenticated: true }
  }
)

export const register = createAsyncThunk(
  '/registration',
  async (authBody: AuthBody, thunkAPI) => {
    const response = await axios.post<AuthRegisterResponse>(API_URL + 'registration', authBody)
    let token = '';

    if(response.data.login){
      const loginResponse = await axios.post<string>(API_URL + 'login', authBody )
      localStorage.setItem('token', loginResponse.data)
      localStorage.setItem('user', JSON.stringify(authBody))

      token = loginResponse.data
    }

    return {
      user: {
        ...authBody,
        id: response.data.id
      },
      token,
      isAuthenticated: token.length > 0
    }
  }
)

export const getInitialState = () => ({
  user: JSON.parse(localStorage.getItem('user') as string),
  token: localStorage.getItem('token'),
  isAuthenticated: Boolean(localStorage.getItem('token'))
})

const slice = createSlice({
  name: 'auth',
  initialState: getInitialState,
  reducers: {
    logout: (state) => {
      state.user = null
      state.token = null
      state.isAuthenticated = false
      localStorage.removeItem('user');
      localStorage.removeItem('token');
    }
  },
  extraReducers: (builder) => {
    builder.addCase(login.fulfilled, (state, action) => {
      state.user = action.payload.user
      state.token = action.payload.token
      state.isAuthenticated = true
    })
    builder.addCase(login.rejected, (state, action) => {
      state.isAuthenticated = false
    })
    builder.addCase(register.fulfilled, (state, action) => {
      state.user = action.payload.user
      state.token = action.payload.token
      state.isAuthenticated = true
    })
    builder.addCase(register.rejected, (state, action) => {
      state.isAuthenticated = false
    })
  }
})

export const { logout } = slice.actions
export default slice.reducer

export const selectIsAuthenticated = (state: RootState) =>
  state.auth.isAuthenticated

