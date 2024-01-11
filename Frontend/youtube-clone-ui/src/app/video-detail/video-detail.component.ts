import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../video.service";
import {VideoPlayerComponent} from "../video-player/video-player.component";
import {FlexModule} from "@angular/flex-layout";

@Component({
  selector: 'app-video-detail',
  standalone: true,
  imports: [
    VideoPlayerComponent,
    FlexModule
  ],
  templateUrl: './video-detail.component.html',
  styleUrl: './video-detail.component.css'
})
export class VideoDetailComponent {

  videoId!: string
  videoUrl!: string;
  private videoAvailable = false;
  private videoTitle!: string;
  private videoDescription!: string;
  videoTags: Array<string> = [];
  constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService) {
    // @ts-ignore
    this.videoId = this.activatedRoute.snapshot.params.videoId;
    this.videoService.getVideo(this.videoId).subscribe(data=>{
    this.videoAvailable = true;
    this.videoTitle = data.title;
    this.videoDescription = data.description;
    this.videoTags = data.tags;
    })
  }
}
