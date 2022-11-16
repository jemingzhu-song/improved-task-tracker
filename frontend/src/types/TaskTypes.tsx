export type TaskType = {
  taskId: string;
  status: string;
  taskDescription: string;
};

export type TaskTypeNoId = {
  status: string;
  taskDescription: string;
};

export type TaskTypeNullable = {
  taskId: string;
  status: string | null;
  taskDescription: string | null;
};

export type TaskBoxProps = {
  taskId_prop: string;
  status_prop: string;
  taskDescription_prop: string;
  deleteTask_prop: (taskId: string) => void;
  editTask_prop: ((editedTask: TaskTypeNullable) => void) | null;
};

export type TaskListProps = {
  taskList_prop: TaskType[];
  deleteTask_prop: (taskId: string) => void;
  editTask_prop: (editedTask: TaskTypeNullable) => void;
};

export type TaskListReport = {
  dateCreated: string;
  count: number;
};
