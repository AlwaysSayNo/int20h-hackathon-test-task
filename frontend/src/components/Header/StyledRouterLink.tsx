import React from 'react'
import { Link as RouterLink, LinkProps } from 'react-router-dom'
import { styled } from '@mui/material'

export const StyledRouterLink = styled(RouterLink)`
  color: inherit;
  text-decoration: none;
  &:not(:last-child){
    margin-right: ${({ theme }) => theme.spacing(1)};
  }
`
