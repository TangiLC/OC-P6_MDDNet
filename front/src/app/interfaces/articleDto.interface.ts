export interface Article {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
  authorUsername: string;
  authorPicture: string;
  themeIds: number[];
  comments: {
    id: number;
    content: string;
    createdAt: string;
    authorUsername: string;
    authorPicture: string;
    articleId: number;
  }[];
}
