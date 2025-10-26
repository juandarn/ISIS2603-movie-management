/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PeliculaCrearComponent } from './pelicula-crear.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PeliculaService } from '../pelicula.service';
import { ReactiveFormsModule } from '@angular/forms';
import { Toast, ToastrModule } from 'ngx-toastr';

describe('PeliculaCrearComponent', () => {
  let component: PeliculaCrearComponent;
  let fixture: ComponentFixture<PeliculaCrearComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule, RouterTestingModule, ReactiveFormsModule, ToastrModule.forRoot()],
      declarations: [ PeliculaCrearComponent ],
      providers: [PeliculaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeliculaCrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
