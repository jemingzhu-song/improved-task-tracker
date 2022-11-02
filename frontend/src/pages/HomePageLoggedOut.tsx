import { Box, Button, Typography } from '@mui/material';
import React, { useState } from 'react';
import uuid from 'react-uuid';
import { TaskBox } from '../components/TaskBox';
import { TaskType } from '../types/TaskTypes';

export const HomePageLoggedOut = () => {
  const [taskList, setTaskList] = useState<TaskType[]>([]);

  const addTask = () => {
    let currentTaskList = taskList;
    currentTaskList.push({
      taskId: uuid(),
      status: 'incomplete',
      taskDescription: 'To Do',
    });
    setTaskList([...currentTaskList]);
  };

  const deleteTask = (taskId: string) => {
    let currentTaskList = taskList.filter((task) => task.taskId !== taskId);
    setTaskList([...currentTaskList]);
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }}>
      {taskList.map((task) => {
        return (
          <TaskBox
            key={task.taskId}
            taskId_prop={task.taskId}
            taskDescription_prop={task.taskDescription}
            status_prop={task.status}
            deleteTask_prop={deleteTask}
          />
        );
      })}
      <Button sx={{ marginTop: '20px', textTransform: 'none' }} onClick={() => addTask()}>
        <Typography variant='body1'>Add</Typography>
      </Button>
    </Box>
  );
};
