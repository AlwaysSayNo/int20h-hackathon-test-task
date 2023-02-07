import React from 'react';
import { Theme } from './theme'
import { Route, Routes } from 'react-router-dom'
import { Layout } from './components/Layout'
import { PRIVATE_ROUTES, PUBLIC_ROUTES } from './constants/routes'
import { useAppSelector } from './hooks/redux'
import { selectIsAuthenticated } from './store/reducers/auth'

function App() {

  const isUserAuth = useAppSelector(selectIsAuthenticated)

  return (
    <Theme>
      <Routes>
        <Route path="/" element={<Layout/>}>
          {PUBLIC_ROUTES.map((route) => (
            <Route
              index={route.isIndex}
              path={route.path}
              element={route.component}
              key={route.name}
            />
          ))}
          {isUserAuth && PRIVATE_ROUTES.map((route) => (
            <Route
              index={route.isIndex}
              path={route.path}
              element={route.component}
              key={route.name}
            />
          ))}
        </Route>
      </Routes>
    </Theme>
  );
}

export default App;
