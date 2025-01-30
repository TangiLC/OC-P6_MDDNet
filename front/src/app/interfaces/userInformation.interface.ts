export interface UserInformation {
  id: number;
  username: string;
  email: string;
  picture?: string;
  isAdmin: boolean;
  themesSet: number[];
  commentsSet: number[];
}
