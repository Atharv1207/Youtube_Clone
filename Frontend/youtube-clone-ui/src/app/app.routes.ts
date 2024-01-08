import { Routes } from '@angular/router';
import {UploadVideosComponent} from "./upload-videos/upload-videos.component";

export const routes: Routes = [
  {
    path: 'upload-videos', component: UploadVideosComponent
  }
];

// export const routes: Routes = [
//   {
//     path: '',
//     redirectTo: '/upload-videos',
//     pathMatch: 'full' // Specify the match type
//   },
//   {
//     path: 'upload-videos',
//     component: UploadVideosComponent // Component for the 'upload-videos' route
//   },
//   // Other routes...
// ];
