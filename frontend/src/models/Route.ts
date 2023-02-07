import React from 'react'

export interface Route {
  path: string,
  name: string,
  isIndex: boolean,
  isVisible: boolean,
  component: React.ReactNode
}

