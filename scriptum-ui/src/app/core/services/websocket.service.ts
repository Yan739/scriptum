import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProgressMessage } from '../models/job.model';

@Injectable({ providedIn: 'root' })
export class WebSocketService {

  private client: Client | null = null;

  connect(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.client = new Client({
        webSocketFactory: () => new SockJS(environment.wsUrl),
        reconnectDelay: 5000,
        onConnect: () => resolve(),
        onStompError: (frame) => reject(frame)
      });
      this.client.activate();
    });
  }

  subscribeToJob(jobId: string): Observable<ProgressMessage> {
    return new Observable(observer => {
      if (!this.client) {
        observer.error('WebSocket non connecte');
        return;
      }

      const subscription = this.client.subscribe(`/topic/job/${jobId}`, (message: IMessage) => {
        const progress: ProgressMessage = JSON.parse(message.body);
        observer.next(progress);
      });

      return () => subscription.unsubscribe();
    });
  }

  disconnect(): void {
    this.client?.deactivate();
  }
}
