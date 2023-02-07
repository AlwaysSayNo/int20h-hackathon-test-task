import React from 'react'
import { createTheme, CssBaseline, ThemeProvider, useMediaQuery } from '@mui/material'
import { css, Global } from '@emotion/react'

type ThemeProps = {
    children: React.ReactNode
}
export const Theme: React.FC<ThemeProps> = ({ children }) => {
  const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');

  const theme = createTheme({
    palette: {
      mode: prefersDarkMode ? 'dark' : 'light'
    }
  })
    return (
        <ThemeProvider theme={theme}>
            <Global
                styles={css`
                  html,
                  body,
                  #root {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                    height: 100%;
                  }
                `}
            />
            <CssBaseline enableColorScheme />
            {children}
        </ThemeProvider>
    )
}
