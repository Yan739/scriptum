export interface MediaFileResponse {
  id: string;
  originalFilename: string;
  mimeType: string;
  sizeBytes: number;
  durationSeconds: number | null;
  uploadedAt: string;
}
