import React from 'react'
import { Dish } from '../../../models/Dish'
import { Grid } from '@mui/material'
import { DishItem } from './DishItem'

interface DishesGridProps {
  dishes: Dish[]
  handleOpenModal: (dish: Dish) => void
}
export const DishesGrid: React.FC<DishesGridProps> = ({
  dishes,
  handleOpenModal
}) => {
  return (
    <Grid container columns={{ xs: 2, sm: 4, md: 8 }}>
      {dishes.map((dish) => (
        <Grid xs={2} sm={2} md={2} padding={1} key={dish.id}>
          <DishItem dish={dish} handleOpenModal={handleOpenModal}/>
        </Grid>
      ))}
    </Grid>
  )
}
