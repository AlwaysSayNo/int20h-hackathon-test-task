import React, { useEffect, useState } from 'react'
import { Box, Grid, Paper, Tab, Tabs, useTheme } from '@mui/material'
import { BaseAuthForm } from './components/BaseAuthForm'
import { useAppSelector } from '../../hooks/redux'
import { selectIsAuthenticated } from '../../store/reducers/auth'
import { useNavigate } from 'react-router-dom'

export enum TabsEnum {
  Login = 'Login',
  Register = 'Register'
}

export const AuthPage: React.FC<Record<string, never>> = () => {

  const [currentTab, setCurrentTab] = useState<TabsEnum>(TabsEnum.Login)

  const isUserAuth = useAppSelector(selectIsAuthenticated)
  const navigate = useNavigate()

  useEffect(() => {
    if(isUserAuth){
      navigate('/')
    }
  }, [isUserAuth])

  const handleChange = (event: React.SyntheticEvent, tab: TabsEnum) => {
    setCurrentTab(tab);
  };

  return (
    <Grid>
      <Paper>
        <Tabs
          value={currentTab}
          onChange={handleChange}
          indicatorColor="secondary"
          textColor="inherit"
          variant="fullWidth"
          aria-label="full width tabs example"
        >
          <Tab label={TabsEnum.Login} value={TabsEnum.Login} />
          <Tab label={TabsEnum.Register} value={TabsEnum.Register} />
        </Tabs>
        <Paper>
          {currentTab === TabsEnum.Login && (
            <BaseAuthForm form={TabsEnum.Login} changeTab={handleChange}/>
          )}
          {currentTab === TabsEnum.Register && (
            <BaseAuthForm form={TabsEnum.Register} changeTab={handleChange}/>
          )}
        </Paper>
      </Paper>
    </Grid>
  )
}

