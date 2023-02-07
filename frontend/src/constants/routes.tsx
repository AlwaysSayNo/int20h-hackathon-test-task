import { Route } from '../models/Route'
import { HomePage } from '../pages/Home'
import { DishesPage } from '../pages/Dishes'
import { AuthPage } from '../pages/Auth'
import { UserPage } from '../pages/User'

export const PUBLIC_ROUTES: Route[] = [
  {
    path: '/',
    name: 'Home',
    isIndex: true,
    isVisible: true,
    component: <HomePage/>
  },
  {
    path: '/dishes',
    name: 'Dishes',
    isIndex: false,
    isVisible: true,
    component: <DishesPage/>
  },
  {
    path: '/auth',
    name: 'Authorization',
    isIndex: false,
    isVisible: false,
    component: <AuthPage/>
  }
]

export const PRIVATE_ROUTES: Route[] = [
  {
    path: '/user',
    name: 'Profile',
    isIndex: true,
    isVisible: true,
    component: <UserPage/>
  }
]
