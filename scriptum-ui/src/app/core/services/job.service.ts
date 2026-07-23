import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { JobResponse, JobStartRequest } from '../models/job.model';

@Injectable({ providedIn: 'root' })
export class JobService {

  constructor(private http: HttpClient) {}

  startJob(request: JobStartRequest): Observable<JobResponse> {
    return this.http.post<JobResponse>(`${environment.apiUrl}/jobs`, request);
  }

  getJob(id: string): Observable<JobResponse> {
    return this.http.get<JobResponse>(`${environment.apiUrl}/jobs/${id}`);
  }
}
