import React from 'react'
import { Box, Grid, styled, Typography } from '@mui/material'
import { useAppSelector } from '../../hooks/redux'
import { selectIsAuthenticated } from '../../store/reducers/auth'
import Button from '@mui/material/Button'
import { Link as RouterLink } from 'react-router-dom'

const WelcomeText = styled(Typography)`
  text-transform: uppercase;
  font-size: ${({ theme }) => theme.typography.h4};
  font-family: 'monospace', sans-serif;
  font-weight: 700;
  letter-spacing: .2rem;
  text-align: center;
`

export const HomePage: React.FC<Record<string, never>> = () => {

  const currentUser = useAppSelector(({auth}) => auth.user)

  return (
    <Grid
      container
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      mt={5}
    >
      <Box mb={2}>
        <WelcomeText>
          Welcome to Food
        </WelcomeText>
      </Box>
      <Grid>
        <Box mb={5}>
          <Typography variant="body1" fontWeight="bold">
            Hello, {currentUser ? currentUser.login + ' ,' : ''} you can visit Dishes page {currentUser ? '' : 'or Login/Register'}
          </Typography>
        </Box>
        <Grid container justifyContent="space-around">
          <Button
            size="large"
            variant="contained"
            component={RouterLink}
            to='/dishes'
            color="primary"
          >
            Dish page
          </Button>
          {!currentUser && (
                <Button
                  size="large"
                  variant='contained'
                  component={RouterLink}
                  to='/auth'
                  color='primary'
              >
                Login/Register
              </Button>
            )
          }
        </Grid>
      </Grid>
    </Grid>
  )
}
