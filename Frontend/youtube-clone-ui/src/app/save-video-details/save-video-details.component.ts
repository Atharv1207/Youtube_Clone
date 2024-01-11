import { Component } from '@angular/core';
import {FlexModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {FormControl, FormGroup} from "@angular/forms";
import{ReactiveFormsModule as NgModule} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-save-video-details',
  standalone: true,
  imports: [
    FlexModule,
    MatFormFieldModule,
    MatSelectModule
  ],
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css'
})
export class SaveVideoDetailsComponent {

  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('')
  videoStatus: FormControl = new FormControl('')
  saveVideoDetailsForm: FormGroup;

  constructor(private matSnakckBar: MatSnackBar) {
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description,
      vidoeStatus: this.videoStatus
    })

  }

}
