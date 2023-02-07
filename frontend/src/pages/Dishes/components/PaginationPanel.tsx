import React from 'react'
import { Box, Grid, IconButton, Typography } from '@mui/material'
import NavigateNextIcon from '@mui/icons-material/NavigateNext'
import NavigatePrevIcon from '@mui/icons-material/NavigateBefore'


interface PaginationPanelProps {
  onPrevPage: () => void
  onNextPage: () => void
  isDisabledNext: boolean
  isDisabledPrev: boolean
  page: number
}

export const PaginationPanel: React.FC<PaginationPanelProps> = ({
  onPrevPage,
  onNextPage,
  isDisabledNext,
  isDisabledPrev,
  page
}) => {
  return (
    <Grid
      container
      justifyContent="space-around"
      mt={2}
      alignItems="center"
    >
      <Box>
        <IconButton
          aria-label="prev"
          disabled={isDisabledPrev}
          onClick={onPrevPage}
          color="secondary"
        >
          <NavigatePrevIcon />
        </IconButton>
      </Box>
      <Box>
        <Typography variant="subtitle1" fontWeight="bold">
          {page}
        </Typography>
      </Box>
      <Box>
        <IconButton
          aria-label="next"
          disabled={isDisabledNext}
          onClick={onNextPage}
          color="secondary"
        >
          <NavigateNextIcon />
        </IconButton>
      </Box>
    </Grid>
  )
}
