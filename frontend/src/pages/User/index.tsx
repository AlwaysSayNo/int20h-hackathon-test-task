import React, { useEffect, useState } from 'react'
import { AppBar, Box, Divider, Grid, Tab, Tabs, Typography } from '@mui/material'
import { useAppSelector } from '../../hooks/redux'
import { useNavigate } from 'react-router-dom'
import { User } from '../../models/User'
import { Category } from '../../models/Product'
import { DishItem } from '../Dishes/components/DishItem'
const MOCK_CATEGORIES: Category[] = [
  {
    id: 'id',
    name: 'SEASONING'
  },
  {
    id: 'id2',
    name: 'VEGETABLE'
  },
  {
    id: 'id32',
    name: 'FISH'
  },
  {
    id: 'id23',
    name: 'Fruit'
  },
  {
    id: 'id1',
    name: 'Meat'
  },
  {
    id: 'i312d',
    name: 'Rice'
  }
]

export const UserPage: React.FC<Record<string, never>> = () => {
  const user = useAppSelector((state) => state.auth.user as User)

  const [category, setCategory] = useState<number>(0)

  const navigate = useNavigate()

  useEffect(() => {
    if(!user){
      navigate('/')
    }
  }, [user])

  const handleChangeCategory = (index: number) => {
    setCategory(index)
  }

  return (
    <Grid>
      <Box mb={2}>
        <Typography variant='h5'>
          Hello, {user.login}
        </Typography>
      </Box>
      <Divider />
      <Box mt={2}>
        <Box>
          <AppBar position="static">
            <Tabs
              value={category}
              onChange={(event, index) => handleChangeCategory(index)}
              variant="scrollable"
              scrollButtons="auto"
              aria-label="scrollable auto tabs example"
            >
              {MOCK_CATEGORIES.map((category) => (
                <Tab label={category.name} key={category.id}/>
              ))}
            </Tabs>
          </AppBar>
          <Box>
            <Grid container columns={{ xs: 2, sm: 4, md: 8 }}>

            </Grid>
          </Box>
        </Box>
      </Box>
    </Grid>
  )
}
