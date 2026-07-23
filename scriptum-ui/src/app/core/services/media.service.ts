import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { MediaFileResponse } from '../models/media-file.model';

@Injectable({ providedIn: 'root' })
export class MediaService {

  constructor(private http: HttpClient) {}

  upload(file: File): Observable<MediaFileResponse> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<MediaFileResponse>(`${environment.apiUrl}/media/upload`, formData);
  }
}
