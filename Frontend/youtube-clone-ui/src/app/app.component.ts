import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgxFileDropModule} from "ngx-file-drop";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {HeaderComponent} from "./header/header.component";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {BrowserModule} from "@angular/platform-browser";
import {VgCoreModule} from "@videogular/ngx-videogular/core";
import {VgOverlayPlayModule} from "@videogular/ngx-videogular/overlay-play";
import {VgBufferingModule} from "@videogular/ngx-videogular/buffering";
import {VgControlsModule} from "@videogular/ngx-videogular/controls";
import {MatSnackBar} from "@angular/material/snack-bar";
import {VideoDto} from "./video.dto";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet,
    FormsModule, HttpClientModule,
    NgxFileDropModule, HeaderComponent,
    FlexLayoutModule, MatFormFieldModule,
    MatSelectModule, MatOptionModule,
    ReactiveFormsModule,BrowserModule,
    VgCoreModule, VgControlsModule,
    VgOverlayPlayModule, VgBufferingModule],
  templateUrl: './app.component.html',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'youtube-clone-ui';

  saveVideo(){

  }
}
