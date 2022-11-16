import { Box, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis } from 'recharts';
import { handleRefreshToken } from '../helper/TokenHelper';
import { GetRequestBasicToken, PostRequestBasicToken } from '../types/RequestTypes';
import { TaskListReport, TaskType } from '../types/TaskTypes';

export const ReportPage = () => {
  const [cookies, setCookie] = useCookies();
  const [taskList, setTaskList] = useState<TaskType[]>([]);
  const [taskListReport, setTaskListReport] = useState<TaskListReport[]>([]);
  const [taskListReportExample, setTaskListReportExample] = useState([
    {
      dateCreated: new Date(2022, 11, 13).toLocaleDateString(),
      count: 5,
    },
    {
      dateCreated: new Date(2022, 11, 14).toLocaleDateString(),
      count: 10,
    },
    {
      dateCreated: new Date(2022, 11, 15).toLocaleDateString(),
      count: 2,
    },
    {
      dateCreated: new Date(2022, 11, 16).toLocaleDateString(),
      count: 19,
    },
    {
      dateCreated: new Date(2022, 11, 19).toLocaleDateString(),
      count: 13,
    },
    {
      dateCreated: new Date(2022, 11, 21).toLocaleDateString(),
      count: 13,
    },
    {
      dateCreated: new Date(2022, 11, 21).toLocaleDateString(),
      count: 13,
    },
  ]);

  useEffect(() => {
    getAllTasksRequest();
  }, []);

  console.log(new Date(1668427774424));

  const getAllTasksRequest = async () => {
    const requestBody = {
      beforeDate: Date.now(),
    };

    const requestOptions: PostRequestBasicToken = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
      body: JSON.stringify(requestBody),
    };

    const response = await fetch(`/user/task/get/recent/week/${cookies.userId}`, requestOptions);

    if (response.status === 200) {
      const data = await response.json();
      let taskListReportGeneratedMap = new Map<string, number>();
      for (let task in data) {
        let date: string = new Date(data[task].dateCreated).toLocaleDateString();
        if (taskListReportGeneratedMap.has(date)) {
          let updatedCount = Number(taskListReportGeneratedMap.get(date)) + 1;
          taskListReportGeneratedMap.set(date, updatedCount);
        } else {
          taskListReportGeneratedMap.set(date, 1);
        }
      }
      // Convert "taskListReportGenerated" into a list of objects
      let taskListReportGeneratedList: TaskListReport[] = [];
      for (let [date, count] of taskListReportGeneratedMap) {
        let obj = {
          dateCreated: date,
          count: count,
        };
        taskListReportGeneratedList.push(obj);
      }

      setTaskList([...data]);
      setTaskListReport(taskListReportGeneratedList);
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to get tasks');
      }
    }
  };

  return (
    <Box
      sx={{
        marginTop: '80px',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Typography sx={{ marginBottom: '40px' }} variant='h2'>
        Report
      </Typography>
      <LineChart width={1000} height={400} data={taskListReport}>
        <XAxis dataKey='dateCreated' />
        <YAxis dataKey='count' />
        <Tooltip />
        <Line type='linear' dataKey='count' stroke='#82ca9d' />
      </LineChart>
    </Box>
  );
};
