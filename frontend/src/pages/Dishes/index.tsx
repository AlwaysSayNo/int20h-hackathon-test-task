import React, { useState } from 'react'
import { Dish, SortType } from '../../models/Dish'
import { useGetDishesQuery } from '../../store/services/DishesService'
import { Box, Divider, Grid, Typography } from '@mui/material'
import { DishesGrid } from './components/DishesGrid'
import { Loader } from '../../components/Loader'
import { PaginationPanel } from './components/PaginationPanel'
import { DishDetailModal } from './components/DishDetailModal'


export const DishesPage: React.FC = () => {

  const [page, setPage] = useState<number>(1)
  const [selectedDish, setSelectedDish] = useState<Dish | null>(null)
  const [isModalActive, setIsModalActive] = useState<boolean>(false)

  const { data: dishesData }  = useGetDishesQuery({ sort: SortType.Asc, page })

  const handleNextPage = () => setPage((currPage) => currPage + 1)

  const handlePrevPage = () => setPage((currPage) => currPage > 1 ? currPage - 1 : 0)

  const handleCloseModal = () => {
    setIsModalActive(false)
    setSelectedDish(null)
  }

  const handleOpenModal = (dish: Dish) => {
    setIsModalActive(true)
    setSelectedDish(dish)
  }

  return (
    <Grid py={2}>
      <Box mb={2}>
        <Typography variant="h4" align="center">
          Dishes
        </Typography>
      </Box>
      <Divider />
      <Box my={2} display="flex" justifyContent="center">
        {dishesData && <DishesGrid dishes={dishesData} handleOpenModal={handleOpenModal} />}
      </Box>
      <Divider />
      <PaginationPanel
        onNextPage={handleNextPage}
        onPrevPage={handlePrevPage}
        page={page}
        isDisabledNext={false}
        isDisabledPrev={page === 1}
      />

      {selectedDish && isModalActive && (
          <DishDetailModal
            dish={selectedDish}
            isOpen={isModalActive}
            handleClose={handleCloseModal}
          />
        )
      }
    </Grid>
  )
}
