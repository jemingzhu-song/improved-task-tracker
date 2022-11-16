import { Box } from '@mui/material';
import { TaskListProps, TaskType } from '../types/TaskTypes';
import { TaskBox } from './TaskBox';

export const TaskList = ({ taskList_prop, deleteTask_prop, editTask_prop }: TaskListProps) => {
  return (
    <Box>
      {taskList_prop.map((task) => (
        <TaskBox
          key={task.taskId + 10}
          taskId_prop={task.taskId}
          status_prop={task.status}
          taskDescription_prop={task.taskDescription}
          deleteTask_prop={deleteTask_prop}
          editTask_prop={editTask_prop}
        />
      ))}
    </Box>
  );
};
