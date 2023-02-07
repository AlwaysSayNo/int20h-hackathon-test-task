import React from 'react'
import { Box, CircularProgress } from '@mui/material'

export const Loader: React.FC<Record<string, never>> = () => {
  return (
    <Box display="flex" my={2}>
      <CircularProgress />
    </Box>
  )
}
