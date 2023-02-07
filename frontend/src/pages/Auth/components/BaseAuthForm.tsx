import { Avatar, Box, Button, FormControlLabel, Grid, TextField, Typography } from '@mui/material'
import React, { SyntheticEvent, useState } from 'react'
import { TabsEnum } from '../index'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import { useAppDispatch } from '../../../hooks/redux'
interface BaseAuthFormProps {
  form: TabsEnum
  changeTab: (event: React.SyntheticEvent, tab: TabsEnum) => void
}
import {login as loginApi, register as registerApi} from '../../../store/reducers/auth'

export const BaseAuthForm: React.FC<BaseAuthFormProps> = ({
  form,
  changeTab
}) => {

  const [login, setLogin] = useState<string>('')
  const [password, setPassword] = useState<string>('')
  const dispatch = useAppDispatch()

  const handleSubmit = (event: SyntheticEvent) => {
    event.preventDefault()
    if(form === 'Login'){
      console.log({ login, password })
      dispatch(loginApi({ login, password }))
    }
    if(form === 'Register'){
      dispatch(registerApi({ login, password }))
    }
  }

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      mt={4}
      pb={4}
      px={2}
    >
      <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
        <LockOutlinedIcon />
      </Avatar>
      <Typography component="h1" variant="h5">
        {form}
      </Typography>
      <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
        <TextField
          margin="normal"
          required
          fullWidth
          id="login"
          label="Login"
          name="login"
          onChange={(event) => setLogin(event.target.value)}
          autoFocus
        />
        <TextField
          margin="normal"
          required
          fullWidth
          name="password"
          onChange={(event) => setPassword(event.target.value)}
          label="Password"
          type="password"
          id="password"
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          {form}
        </Button>
        {form === TabsEnum.Login && (
          <Grid container>
              <Grid item>
                <Typography
                  variant="subtitle2"
                  onClick={(event) => {
                    changeTab(event, TabsEnum.Register)
                  }}
                  sx={{
                    cursor: 'pointer',
                    textDecoration: 'underline'
                  }}
                >
                  {'Don\'t have an account? Register'}
                </Typography>
              </Grid>
          </Grid>
        )}
      </Box>
    </Box>
  )
}
