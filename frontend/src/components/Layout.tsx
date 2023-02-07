import { Outlet } from 'react-router-dom'
import { Grid } from '@mui/material'
import { Header } from './Header'

export const Layout = () => {
  return (
    <>
      <Header />
      <Grid m={2}>
        <Outlet />
      </Grid>
    </>
  )
}
