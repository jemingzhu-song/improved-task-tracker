import { Box, Button, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import uuid from 'react-uuid';
import { TaskList } from '../components/TaskList';
import { handleRefreshToken } from '../helper/TokenHelper';
import {
  DeleteRequestEmptyBodyToken,
  GetRequestBasicToken,
  PostRequestBasicToken,
  PutRequestBasicToken,
} from '../types/RequestTypes';
import { TaskType, TaskTypeNoId, TaskTypeNullable } from '../types/TaskTypes';

export const HomePageLoggedIn = () => {
  const [cookies, setCookie] = useCookies();
  const [taskList, setTaskList] = useState<TaskType[]>([]);

  useEffect(() => {
    getAllTasksRequest();
  }, []);

  const getAllTasksRequest = async () => {
    const requestOptions: GetRequestBasicToken = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
    };

    const response = await fetch(`/user/task/get/all/${cookies.userId}`, requestOptions);

    if (response.status === 200) {
      const data = await response.json();

      setTaskList([...data]);
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to get tasks');
      }
    }
  };

  const addTaskRequest = async () => {
    let currentTaskList = taskList;

    const newTask: TaskTypeNoId = {
      status: 'INCOMPLETE',
      taskDescription: 'To Do',
    };

    const requestOptions: PostRequestBasicToken = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
      body: JSON.stringify(newTask),
    };

    const response = await fetch(`/user/task/create/${cookies.userId}`, requestOptions);

    if (response.status === 200) {
      const data = await response.json();
      console.log(data);
      currentTaskList.push(data);
      setTaskList([...currentTaskList]);
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to save new task');
      }
    }
  };

  const editTaskRequest = async (editedTask: TaskTypeNullable) => {
    const requestOptions: PutRequestBasicToken = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
      body: JSON.stringify(editedTask),
    };

    const response = await fetch(`/user/task/edit/${cookies.userId}`, requestOptions);

    if (response.status === 200) {
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to edit task');
      }
    }
  };

  const deleteTaskRequest = async (taskId: string) => {
    const requestOptions: DeleteRequestEmptyBodyToken = {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
    };

    const response = await fetch(`/user/task/delete/${cookies.userId}/${taskId}`, requestOptions);

    if (response.status === 200) {
      let currentTaskList = taskList.filter((task) => task.taskId !== taskId);
      setTaskList([...currentTaskList]);
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to delete task');
      }
    }
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
      <TaskList
        taskList_prop={taskList}
        deleteTask_prop={deleteTaskRequest}
        editTask_prop={editTaskRequest}
      />
      <Button sx={{ marginTop: '20px', textTransform: 'none' }} color='primary' onClick={addTaskRequest}>
        <Typography variant='body1'>Add</Typography>
      </Button>
    </Box>
  );
};
