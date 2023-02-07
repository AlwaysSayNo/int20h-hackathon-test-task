export type AuthBody = {
  login: string,
  password: string
}

export interface User {
  id: string,
  login: string,
  password: string,
}
