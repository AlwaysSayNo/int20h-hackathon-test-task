import React from 'react'
import { Dish } from '../../../models/Dish'
import {
  AppBar,
  Box,
  Dialog,
  Divider,
  Grid,
  IconButton,
  Slide,
  Toolbar,
  Typography, useMediaQuery,
} from '@mui/material'
import CloseIcon from '@mui/icons-material/Close'
import { TransitionProps } from '@mui/material/transitions'
import Image from 'mui-image'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import { Product } from '../../../models/Product'
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import { Autoplay } from 'swiper'
import { useGetProductsForDishQuery } from '../../../store/services/ProductsService'

const MAX_SCORE = 5

interface DishDetailModal {
  dish: Dish
  isOpen:boolean
  handleClose: () => void
}

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>,
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export const DishDetailModal: React.FC<DishDetailModal> = ({
  dish,
  isOpen,
  handleClose
}) => {

  const isMobile = useMediaQuery('(max-width:750px)');

  const { data: dishInfoData } = useGetProductsForDishQuery(dish.id)

  return (
    <Dialog
      fullScreen
      open={isOpen}
      onClose={handleClose}
      TransitionComponent={Transition}
    >
      <AppBar sx={{ position: 'relative' }}>
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            onClick={handleClose}
            aria-label="close"
          >
            <CloseIcon />
          </IconButton>
          <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
            {dish.name}
          </Typography>
        </Toolbar>
      </AppBar>
      <Grid
        p={2}
        container
        bgcolor="background.default"
        justifyContent="flex-start"
        alignItems="center"
      >
        <Box mr={1}>
          <Typography variant="h6">
              Difficulty:
          </Typography>
        </Box>
        <Box display="flex">
          {[...new Array(Math.round(3))].map((_, index) => (
            <StarIcon key={index}/>
          ))}
          {[...new Array(Math.round(MAX_SCORE - 3.2))].map((_, index) => (
            <StarBorderIcon key={index}/>
          ))}
        </Box>
      </Grid>
      <Grid container bgcolor="background.default" p={2} justifyContent="center" flexWrap="wrap" alignItems="center">
        <Box
          width={300}
          borderRadius="2px"
          overflow="hidden"
          mr={2}
          mb={2}
        >
          <Image
            src={dish.imageUrl}
            height="100%"
            width="100%"
            showLoading
            duration={0}
          />
        </Box>
        <Box width={500}>
            <Box mb={1}>
              <Typography variant="h4" align="center" fontWeight="bold">
                How to cook
              </Typography>
            </Box>
            <Divider />
            <Box mt={1}>
              <Typography variant="body2" align="left">
                {dish.recipe}
              </Typography>
            </Box>
        </Box>
      </Grid>
      <Grid height={500} py={1} px={2}>
        <Box my={1}>
          <Typography variant="h5" fontWeight="bold">
            Products to use:
          </Typography>
        </Box>
        <Divider sx={{
          marginBottom: 2
        }}/>
        <Swiper
          slidesPerView={isMobile ? 2 : 5}
          spaceBetween={10}
          modules={[Autoplay]}
          autoplay={{
            delay: 2500,
            disableOnInteraction: false,
          }}
        >
          {dishInfoData?.products && dishInfoData.products.map((product) => (
            <SwiperSlide key={product.id}>
              <Box p={2} bgcolor="background.default" borderRadius={2}>
                <Box
                  height={150}
                  mb={1}
                >
                  <Image
                    src={product.imageUrl || ''}
                    showLoading
                  />
                </Box>
                <Divider/>
                <Box>
                  <Typography variant="subtitle2">
                    {product.category}
                  </Typography>
                </Box>
                <Box>
                  <Typography variant="body2">
                    {product.name}
                  </Typography>
                </Box>
              </Box>
            </SwiperSlide>
          ))}
        </Swiper>
      </Grid>
    </Dialog>
  )
}
