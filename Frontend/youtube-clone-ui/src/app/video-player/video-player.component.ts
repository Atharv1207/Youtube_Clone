import { Component } from '@angular/core';
import {VgCoreModule} from "@videogular/ngx-videogular/core";
import {VgOverlayPlayModule} from "@videogular/ngx-videogular/overlay-play";
import {VgBufferingModule} from "@videogular/ngx-videogular/buffering";
import {VgControlsModule} from "@videogular/ngx-videogular/controls";

@Component({
  selector: 'app-video-player',
  standalone: true,
  imports: [
    VgCoreModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    VgControlsModule
  ],
  templateUrl: './video-player.component.html',
  styleUrl: './video-player.component.css'
})
export class VideoPlayerComponent {

}
