export enum JobStatus {
  PENDING = 'PENDING',
  EXTRACTING_AUDIO = 'EXTRACTING_AUDIO',
  TRANSCRIBING = 'TRANSCRIBING',
  TRANSLATING = 'TRANSLATING',
  DONE = 'DONE',
  FAILED = 'FAILED'
}

export interface JobResponse {
  id: string;
  mediaFileId: string;
  status: JobStatus;
  progress: number;
  sourceLanguage: string | null;
  targetLanguage: string | null;
  rawText: string | null;
  translatedText: string | null;
  errorMessage: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface JobStartRequest {
  mediaFileId: string;
  targetLanguage?: string;
}

export interface ProgressMessage {
  jobId: string;
  status: JobStatus;
  progress: number;
}
