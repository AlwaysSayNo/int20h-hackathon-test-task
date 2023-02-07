export interface EnvironmentConfig {
  APP_ENV: 'dev' | 'prod'
  API_URL: string
}

export const localHostAddress = 'localhost'

const dev: EnvironmentConfig = {
  APP_ENV: 'dev',
  API_URL: `http://ec2-3-120-134-204.eu-central-1.compute.amazonaws.com:8080/hackathon/api/v1/`
}

let config: EnvironmentConfig

switch (process.env.REACT_APP_ENV){
  case 'dev':
    config = dev
    break
  default:
    config = dev
}

export const {
  APP_ENV,
  API_URL
} = config
