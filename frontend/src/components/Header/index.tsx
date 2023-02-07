import * as React from 'react';
import { PRIVATE_ROUTES, PUBLIC_ROUTES } from '../../constants/routes'
import { Link as RouterLink, useNavigate } from 'react-router-dom'
import { StyledRouterLink } from './StyledRouterLink'
import { useAppDispatch, useAppSelector } from '../../hooks/redux'
import { Menu as MenuIcon, Login as LoginIcon } from '@mui/icons-material'
import { logout, selectIsAuthenticated } from '../../store/reducers/auth'
import {
  AppBar,
  Avatar,
  Box,
  Button,
  Container,
  IconButton,
  Menu,
  MenuItem,
  Toolbar,
  Tooltip,
  Typography,
} from '@mui/material'

function LocalDiningIcon(props: { sx: { mr: number; display: { md: string; xs: string } } }) {
  return null
}

export const Header: React.FC<Record<string, never>> = () => {
  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);

  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const isUserAuth = useAppSelector(selectIsAuthenticated)
  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleLogout = () => dispatch(logout())

  const navigateToHome = () => {
    navigate('/')
  }

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <LocalDiningIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="span"
            onClick={navigateToHome}
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              cursor: 'pointer',
              textDecoration: 'none',
            }}
          >
            Food
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              <Box display="flex" flexDirection="column" >
                {PUBLIC_ROUTES
                  .filter((route) => route.isVisible)
                  .map((route) => (
                  <Button
                    key={route.name}
                    component={RouterLink}
                    to={route.path}
                    color="inherit"
                    sx={{ borderRadius: 0 }}
                  >
                    {route.name}
                  </Button>
                ))}
              </Box>
            </Menu>
          </Box>
          <LocalDiningIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="span"
            onClick={navigateToHome}
            sx={{
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              cursor: 'pointer',
              textDecoration: 'none',
            }}
          >
            Food
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {PUBLIC_ROUTES
              .filter((route) => route.isVisible)
              .map((route) => (
                <StyledRouterLink to={route.path} color="inherit" key={route.name}>
                  {route.name}
                </StyledRouterLink>
            ))}
          </Box>
          {!isUserAuth && (
            <Button
              aria-label="login"
              color="inherit"
              to="/auth"
              component={RouterLink}
            >
              <LoginIcon />
            </Button>

          )}
          {isUserAuth && (
              <Box sx={{ flexGrow: 0 }}>
              <Tooltip title='Open settings'>
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <Avatar alt='Remy Sharp' src='/static/images/avatar/2.jpg' />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: '45px' }}
                id='menu-appbar'
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                {PRIVATE_ROUTES
                  .filter((route) => route.isVisible)
                  .map((route) => (
                    <Button
                      key={route.name}
                      component={RouterLink}
                      to={route.path}
                      color="inherit"
                      sx={{ borderRadius: 0 }}
                    >
                      {route.name}
                    </Button>
                  ))}
                <MenuItem onClick={handleLogout}>
                  <Typography textAlign='center'>Logout</Typography>
                </MenuItem>
              </Menu>
            </Box>
          )}
        </Toolbar>
      </Container>
    </AppBar>
  );
}
