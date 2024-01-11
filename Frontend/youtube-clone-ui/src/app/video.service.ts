import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FileSystemFileEntry} from 'ngx-file-drop'
import {UploadVideoResponse} from "./upload-videos/UploadVideoResponse";
import {VideoDto} from "./video.dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient : HttpClient) { }

  uploadVideo(fileEntry: File){

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);


    this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos", formData);
    return;
  }

  getVideo(videoId: string): Observable<VideoDto>{
    return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" +videoId);
  }
}
