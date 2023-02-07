import React from 'react'
import { Dish } from '../../../models/Dish'
import { Button, Card, CardActions, CardContent, CardMedia, Typography } from '@mui/material'

interface DishItemProps {
  dish: Dish
  handleOpenModal: (dish: Dish) => void
}

export const DishItem: React.FC<DishItemProps> = ({
  dish,
  handleOpenModal
}) => {
  return (
    <Card variant="outlined">
      <CardMedia
        sx={{ height: 140 }}
        image={dish.imageUrl}
        title={dish.name}
      />
      <CardContent>
        <Typography gutterBottom variant="h6" component="div">
          {dish.name}
        </Typography>
      </CardContent>
      <CardActions>
        <Button
          size="small"
          onClick={() => {
            handleOpenModal(dish)
          }}
        >
          Detail recipe
        </Button>
      </CardActions>
    </Card>
  )
}
