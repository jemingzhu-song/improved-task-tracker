import { Box, Button, Card, Checkbox, ClickAwayListener, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { TaskBoxProps } from '../types/TaskTypes';

export const TaskBox = ({
  taskId_prop,
  status_prop,
  taskDescription_prop,
  deleteTask_prop,
  editTask_prop,
}: TaskBoxProps) => {
  const taskId: string = taskId_prop;
  const [status, setStatus] = useState<string>(status_prop);
  const [taskDescription, setTaskDescription] = useState<string>(taskDescription_prop);
  const [isEditing, setIsEditing] = useState<boolean>(false);

  const handleClickAway = () => {
    setIsEditing(!isEditing);
    if (editTask_prop !== null) {
      editTask_prop({
        taskId: taskId,
        status: status,
        taskDescription: taskDescription,
      });
    }
  };

  const handleTextFieldChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTaskDescription(event.target.value);
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newStatus = event.target.checked ? 'COMPLETE' : 'INCOMPLETE';
    setStatus(newStatus);
    if (editTask_prop !== null) {
      editTask_prop({
        taskId: taskId,
        status: newStatus,
        taskDescription: taskDescription,
      });
    }
  };

  return (
    <Card
      sx={{
        marginTop: '20px',
        padding: '10px',
        minHeight: '40px',
        minWidth: '450px',
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
      }}
    >
      <Box>
        <Checkbox
          color='success'
          checked={status === 'INCOMPLETE' ? false : true}
          onChange={handleCheckboxChange}
        ></Checkbox>
      </Box>
      <Box sx={{ minWidth: '350px' }}>
        {isEditing ? (
          <ClickAwayListener onClickAway={handleClickAway}>
            <TextField
              sx={{ minWidth: '350px' }}
              variant='standard'
              value={taskDescription}
              onChange={handleTextFieldChange}
            ></TextField>
          </ClickAwayListener>
        ) : (
          <Typography
            onClick={() => {
              setIsEditing(!isEditing);
            }}
            variant='body1'
          >
            {taskDescription}
          </Typography>
        )}
      </Box>
      <Box>
        <Button
          color='warning'
          onClick={() => {
            deleteTask_prop(taskId);
          }}
        >
          Delete
        </Button>
      </Box>
    </Card>
  );
};
