import { RequestMethod } from '../types/RequestTypes';

export const failedRequestLogger = async (
  requestPath: string,
  requestMethod: RequestMethod,
  response: Response
) => {
  console.log(
    `${requestPath} ${requestMethod} request failed with status: ${response.status} and body: ${response.body}`
  );
};
