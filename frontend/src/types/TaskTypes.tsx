export type TaskType = {
  taskId: string;
  status: string;
  taskDescription: string;
};

export type TaskBoxProps = {
  taskId_prop: string;
  status_prop: string;
  taskDescription_prop: string;
  deleteTask_prop: (taskId: string) => void;
};
